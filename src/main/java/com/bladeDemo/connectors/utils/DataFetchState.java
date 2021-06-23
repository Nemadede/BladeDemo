package com.bladeDemo.connectors.utils;

import com.bladeDemo.commons.models.quickbooks.QBBusiness;

import java.util.concurrent.Semaphore;

public class DataFetchState {

    private Integer count = 0;

    private Integer totalCount = 0;

    private Semaphore semaphore = SemaphoreControls.getInstance().getSemaphore();

    private QBBusiness business;


    public DataFetchState setCount(){
        this.count ++;

        System.out.println("================== Counter at -----> " + count);
        System.out.println("================== Total Count at -----> " + totalCount);

        if(this.count.equals(totalCount)){
            System.err.println("-----------------------------> Done Processing Business "+ business.getTableId() );

            semaphore.release();
        }

        return this;
    }


    public DataFetchState updateTotalCount(int value){
        totalCount = totalCount + value;
        return this;
    }

    public DataFetchState setBusiness(QBBusiness business) {
        this.business = business;
        return this;
    }
}
