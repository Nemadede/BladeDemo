package com.bladeDemo.connectors.quickbooks;

import com.bladeDemo.commons.models.quickbooks.QBBusiness;
import com.bladeDemo.connectors.quickbooks.PullService;
import com.bladeDemo.connectors.utils.DataFetchState;
import com.bladeDemo.connectors.utils.SemaphoreControls;
import com.bladeDemo.utils.configs.GlobalConfigs;

import java.util.concurrent.Semaphore;

public class UserTask implements Runnable {
    private volatile QBBusiness qbBusiness;
    private Semaphore semaphore = SemaphoreControls.getInstance().getSemaphore();
    private DataFetchState dataFetchState;
    private GlobalConfigs globalConfigs = GlobalConfigs.getInstance();



    public UserTask(QBBusiness qbBusiness){
        this.qbBusiness = qbBusiness;
        dataFetchState = new DataFetchState();

    }

    @Override
    public void run() {
        try {
            semaphore.acquire();
            System.err.println("Processing Business "+ qbBusiness.getTableId());

            dataFetchState = dataFetchState.setBusiness(qbBusiness).setCount();


            PullService pullService = new PullService(qbBusiness, dataFetchState, globalConfigs.getPullingExecutor(),
                    globalConfigs.getWritingExecutor());
            pullService.pull();


        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
