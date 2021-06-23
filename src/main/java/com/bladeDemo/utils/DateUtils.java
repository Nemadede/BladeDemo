package com.bladeDemo.utils;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static LocalDateTime getCurrentUTCDate(){
        return LocalDateTime.now(Clock.systemUTC());
    }

    public static Date getExpiryDate(int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }

    public static String dateToString(Date date){
        return "";
    }

    public static LocalDateTime getSixMonthsAgo(LocalDateTime currentDate){
        return currentDate.minus(6, ChronoUnit.MONTHS);
    }

    public static float timing(long start){
        long end = System.currentTimeMillis();
        float time = (end-start)/1000F;
        return time/60F;
    }
}
