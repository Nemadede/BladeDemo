package com.bladeDemo.connectors.utils;

import java.util.concurrent.Semaphore;

public class SemaphoreControls {

    private static SemaphoreControls semaphoreControls;
    private static Semaphore semaphore;

    private SemaphoreControls(){}

    public static SemaphoreControls getInstance() {
        if(semaphoreControls == null){
            semaphore = new Semaphore(1);
            semaphoreControls = new SemaphoreControls();
            return semaphoreControls;
        }

        return semaphoreControls;
    }


    public Semaphore getSemaphore() {
        return semaphore;
    }
}
