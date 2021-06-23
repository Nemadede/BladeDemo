package com.bladeDemo.connectors.quickbooks;

import com.bladeDemo.commons.models.quickbooks.QBBusiness;
import com.bladeDemo.connectors.utils.DataFetchState;
import com.bladeDemo.utils.configs.CustomThreadPoolExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PullService {

    private volatile QBBusiness qbBusiness;

    private DataFetchState dataFetchState;
    CustomThreadPoolExecutor pullExecutor;
    CustomThreadPoolExecutor writerExecutor;


    public PullService(QBBusiness qbBusiness , DataFetchState dataFetchState, CustomThreadPoolExecutor pullExecutor,
                       CustomThreadPoolExecutor writerExecutor) {
        this.qbBusiness = qbBusiness;

        this.dataFetchState = dataFetchState;

        this.pullExecutor = pullExecutor;
        this.writerExecutor = writerExecutor;

    }

    public void pull() {

       List<String> modelNames = new ArrayList(Arrays.asList("Account", "Bill", "BillPayment", "Customer", "Invoice", "Payment",
               "PaymentMethod", "RefundReceipt", "SalesReceipt", "Vendor", "VendorCredit"));

//         List<String> modelNames = new ArrayList(Arrays.asList("Account", "Bill", "BillPayment"));

        dataFetchState = dataFetchState.updateTotalCount(modelNames.size()+1);

        for(String name: modelNames){
            pullExecutor.execute(new Query(qbBusiness,name, writerExecutor,
                    dataFetchState));
        }
    }
}
