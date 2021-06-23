package com.bladeDemo.utils;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

public class Schedule {

    private SchedulerFactory schedulerFactory = new StdSchedulerFactory();
    private Scheduler myScheduler;
    public static Schedule schedule;

    private Schedule(){}

    public static Schedule getInstance() throws SchedulerException {
        if (schedule == null){
            schedule = new Schedule();
            schedule.setScheduler();
        }
        return schedule;
    }

    public void setScheduler() throws SchedulerException {
        this.myScheduler = schedulerFactory.getScheduler();
    }
    public Scheduler getMyScheduler() {
        return myScheduler;
    }

}
