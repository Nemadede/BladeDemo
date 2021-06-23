package com.bladeDemo;

import com.blade.Blade;
import com.blade.security.web.cors.CorsConfiger;
import com.bladeDemo.commons.Connection;
import com.bladeDemo.utils.BlockingExecutor;
import com.bladeDemo.utils.configs.Configs;
import com.bladeDemo.utils.configs.GlobalConfigs;
import com.typesafe.config.Config;

import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Main {
    public static void main(String[] args) {
        GlobalConfigs globalConfigs = GlobalConfigs.getInstance();
        Config config = new Configs.Builder().withResource("dev.conf").build();

//        Anima.open(ConnectionPools.getTransactional());
        try {
            Connection.setUp();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setGlobalAppConfigs(globalConfigs,config);

        startScheduler();

        Blade.of()
                .enableCors(Boolean.TRUE, getCorsConfiger())
                .start(Main.class);
    }

    public static CorsConfiger getCorsConfiger(){
        return CorsConfiger.builder()
                .allowedMethods(Stream.of("GET","OPTIONS","POST","PUT","DELETE","HEAD").collect(Collectors.toList()))
                .allowedHeaders(Stream.of("Origin","X-Requested-With","Content-Type", "Accept", "Connection", "User-Agent", "Cookie",
                        "Cache-Control").collect(Collectors.toList()))
                .maxAge(1300L)
                .allowCredentials(Boolean.TRUE)
                .build();
    }

    public static void startScheduler(){
//        Schedule scheduler = null;
//        try {
//            scheduler = Schedule.getInstance();
//            scheduler.getMyScheduler().start();
//        } catch (SchedulerException e) {
//            e.printStackTrace();
//        }

    }


    public static void setGlobalAppConfigs(GlobalConfigs globalAppConfigs, Config config){
        String appSecretKey = config.getString("app.secret_key");
        globalAppConfigs.setKey(appSecretKey);
        int numberOfThread = (Runtime.getRuntime().availableProcessors())*2;

        int pull = (int) Math.ceil(numberOfThread * 0.5);
        int write = (int) Math.ceil(numberOfThread * 0.3);
        int user = (int) Math.ceil(numberOfThread * 0.2);

        globalAppConfigs.setPullingExecutor(3, 3);
        globalAppConfigs.setWritingExecutor(1, 2);
        globalAppConfigs.setUserTaskExecutor(1, 2);
        globalAppConfigs.setThreadPoolsExecutor(new BlockingExecutor(2, 40));

    }
}
