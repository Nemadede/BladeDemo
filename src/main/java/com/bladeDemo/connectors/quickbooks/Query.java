package com.bladeDemo.connectors.quickbooks;

import com.bladeDemo.connectors.utils.QBService;
import com.bladeDemo.commons.models.quickbooks.QBBusiness;
import com.bladeDemo.commons.models.quickbooks.QBPullTracker;
import com.bladeDemo.connectors.params.PullTrackerParams;
import com.bladeDemo.connectors.utils.DataFetchState;
import com.bladeDemo.connectors.utils.QuickBooksOAuth;
import com.bladeDemo.utils.DateUtils;
import com.bladeDemo.utils.JSON;
import com.bladeDemo.utils.configs.CustomThreadPoolExecutor;
import com.bladeDemo.connectors.utils.AuthErrorException;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Query implements Runnable {

    private final String modelName;
    private volatile QBBusiness qbBusiness;
    private List arraylist;
    private final CustomThreadPoolExecutor executor;
    private DataFetchState dataFetchState;
    private LocalDateTime currentDate;
    private LocalDateTime previousDate;
    QBPullTracker qbPullTracker;



    public Query(QBBusiness qbBusiness, String modelName, CustomThreadPoolExecutor executor,
                 DataFetchState dataFetchState)  {
        this.qbBusiness = qbBusiness;
        this.modelName = modelName;
        arraylist = new ArrayList();
        this.executor = executor;
        this.dataFetchState = dataFetchState;

    }


    @Override
    public void run() {

            System.err.println("---------------------------------------------------------------> " +
                    "Proccessing Model " + modelName);

            setTrackerDates();

            int numberOfRecords = recordCounter();

            //get total loop count

            int loopCount = numberOfRecords % 1000 == 0
                    ? (int) numberOfRecords /1000
                    : (int) Math.ceil(numberOfRecords/1000F);

            int startPosition = 0;

            dataFetchState = dataFetchState.updateTotalCount(loopCount).setCount();

            for (int i = 0; i<loopCount ; i++) {

                String sql = "select * from " + modelName +
                                " where Metadata.CreateTime > '" + previousDate +
                                "' AND Metadata.CreateTime < '" + currentDate +
                                "' AND Metadata.LastUpdatedTime > '" + previousDate +
                                "' AND Metadata.LastUpdatedTime < '" + currentDate +
                                "' startPosition " + startPosition +
                                " maxResults 1000";

//                String sql = "select * from " + modelName;

                String res = null;


                try {
                    res = QuickBooksOAuth.getData(qbBusiness.getRealmId(), qbBusiness.getToken(),sql);

                    processResponse(res);

                } catch (AuthErrorException e) {

                    res = refreshBlock(sql);
                    processResponse(res);

                }

                startPosition = startPosition + 1000;



            }
            System.err.println("------------------------------------------------------------>" +
                " Done Processing " + modelName);

        // Todo Update LastPullDateLimit when Pulling is done;

        QBService.UpdateLastPullTime(qbPullTracker, DateUtils.getCurrentUTCDate());

    }

    public int recordCounter() {

        String sql = "SELECT COUNT(*) FROM "+ modelName +
                " where Metadata.CreateTime > '"+ previousDate +
                "' AND Metadata.CreateTime < '" + currentDate +
                "' AND Metadata.LastUpdatedTime > '"+ previousDate +
                "' AND Metadata.LastUpdatedTime < '" + currentDate +"'";

//        String sql = "SELECT COUNT(*) FROM "+ modelName;

        String res = null;


        try {
            res = QuickBooksOAuth.getData(qbBusiness.getRealmId(), qbBusiness.getToken(),sql);

        } catch (AuthErrorException e) {

            res = refreshBlock(sql);

        }


        return getCount(res);

    }

    private String refreshBlock(String sql) {
        try {
            synchronized (qbBusiness){
                Map<String, String> tokens = QuickBooksOAuth.refreshBearerToken(qbBusiness.getRefreshToken());

                QBService.updateQBBusinessTokens(qbBusiness.getTableId(), tokens.get("token"), tokens.get("refresh-token"));

                return QuickBooksOAuth.getData(qbBusiness.getRealmId(), tokens.get("token"),sql);
            }

        }
        catch (Exception oAuthException) {
            oAuthException.printStackTrace();
        }
        return null;
    }


    private int getCount(String s){
        Map<String, Object> res = JSON.parserToMap(s);
        int counts = 0;

        if(res.get("QueryResponse") != null){

            Map<String, Object> totalCount = (Map<String, Object>) res.get("QueryResponse");
            counts = (int) Math.ceil((Double) totalCount.get("totalCount"));

        }

        return counts;
    }



    private void setTrackerDates(){
        qbPullTracker = QBService.getTrackerByUserNTabName(qbBusiness.getUser(), modelName);

        if(qbPullTracker == null){
            previousDate = DateUtils.getSixMonthsAgo(qbBusiness.getConnectionDate());
            currentDate = qbBusiness.getConnectionDate();

            PullTrackerParams pullTrackerParams = new PullTrackerParams(qbBusiness.getUser(), modelName,  currentDate);
            qbPullTracker = QBService.createTrackerItem(pullTrackerParams);

            return;
        }

        previousDate = qbPullTracker.getLastPullDateLimit();
        currentDate = DateUtils.getCurrentUTCDate();



    }


    private void processResponse(String s){

        Map<String, Object> res = JSON.parserToMap(s);



        if (res.get("QueryResponse") != null){

            Map<String, Object> queryResponse = (Map<String, Object>) res.get("QueryResponse");
            try {
                arraylist.addAll(new QBService().SwitchObject(modelName, JSON.json(queryResponse.get(modelName)), qbBusiness.getUser().getId()));

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            executor.execute(new Writer(arraylist, dataFetchState));


            arraylist = null;

        }
    }

}
