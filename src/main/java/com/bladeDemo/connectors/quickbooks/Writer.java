package com.bladeDemo.connectors.quickbooks;

import com.bladeDemo.connectors.utils.QBService;
import com.bladeDemo.connectors.utils.DataFetchState;

import java.util.List;

public class Writer implements Runnable{
    private List list;
    private DataFetchState dataFetchState;


    public Writer(List list, DataFetchState dataFetchState){
        this.list = list;
        this.dataFetchState = dataFetchState;
    }

    @Override
    public void run() {
        System.err.println("--------------------------------------------------------------> About to Write");

//        QBService.batchSaveObject(list);

        dataFetchState.setCount();
    }
}
