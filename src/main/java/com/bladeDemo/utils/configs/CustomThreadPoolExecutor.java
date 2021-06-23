package com.bladeDemo.utils.configs;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CustomThreadPoolExecutor extends ThreadPoolExecutor {

    public CustomThreadPoolExecutor(int i, int i1, long l, TimeUnit timeUnit, BlockingQueue<Runnable> blockingQueue) {
        super(i, i1, l, timeUnit, blockingQueue);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r){
        super.beforeExecute(t,r);
//        System.out.println("Perform beforeExecute() logic");
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t){
        super.afterExecute(r,t);
        if(t != null){
            System.out.println("Perform exception handler logic");
        }
//        System.out.println("Perform afterExecute() logic");
    }
}
