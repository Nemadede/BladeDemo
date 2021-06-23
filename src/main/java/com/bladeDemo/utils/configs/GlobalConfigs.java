package com.bladeDemo.utils.configs;

import com.bladeDemo.utils.BlockingExecutor;

import java.util.concurrent.*;

public class GlobalConfigs {

    private static volatile GlobalConfigs instance;

    private String key;
    private BlockingExecutor threadPoolsExecutor;
    private ExecutorService poolsExecutorService;

    private CustomThreadPoolExecutor pullingExecutor;
    private CustomThreadPoolExecutor writingExecutor;
    private CustomThreadPoolExecutor userTaskExecutor;

    private final static BlockingQueue<Runnable> WBlockingQueue = new ArrayBlockingQueue<Runnable>(50);
    private final static BlockingQueue<Runnable> PBlockingQueue = new ArrayBlockingQueue<Runnable>(50);
    private final static BlockingQueue<Runnable> UBlockingQueue = new ArrayBlockingQueue<Runnable>(50);

    private long keepAliveTime = 1000;

    private GlobalConfigs(){}

    public static GlobalConfigs getInstance(){
        if (instance == null){
            synchronized (GlobalConfigs.class){
                if(instance == null){
                    instance = new GlobalConfigs();
                }
            }
        }
        return instance;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }



    public BlockingExecutor getThreadPoolsExecutor(){
        return threadPoolsExecutor;
    }

    public void setThreadPoolsExecutor(BlockingExecutor threadPoolsExecutor){
        this.threadPoolsExecutor = threadPoolsExecutor;
    }

    public ExecutorService getPoolsExecutorService(){
        return poolsExecutorService;
    }

    public void setPoolsExecutorService() {
        poolsExecutorService = Executors.newFixedThreadPool(2);
    }

    // Pulling Executor Controllers
    public CustomThreadPoolExecutor getPullingExecutor(){
        this.pullingExecutor.prestartAllCoreThreads();
        return this.pullingExecutor;
    }

    public void setPullingExecutor(int corePoolSize, int maximumPoolSize){

        final CustomThreadPoolExecutor executor = new CustomThreadPoolExecutor(corePoolSize, maximumPoolSize,
                keepAliveTime, TimeUnit.MILLISECONDS, PBlockingQueue);

        executor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
                System.err.println("Failed adding "+ runnable + ":: retrying.......");

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                executor.execute(runnable);
            }
        });

        this.pullingExecutor = executor;

    }

    // Writer Executor Controls
    public CustomThreadPoolExecutor getWritingExecutor(){
        this.writingExecutor.prestartAllCoreThreads();
        return this.writingExecutor;
    }

    public void setWritingExecutor(int corePoolSize, int maximumPoolSize){

        final CustomThreadPoolExecutor executor = new CustomThreadPoolExecutor(corePoolSize, maximumPoolSize,
                keepAliveTime, TimeUnit.MILLISECONDS, WBlockingQueue);

        executor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
                System.err.println("Failed adding "+ runnable + ":: retrying.......");

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                executor.execute(runnable);
            }
        });

        this.writingExecutor = executor;

    }

    // UserTask Executor Controls
    public CustomThreadPoolExecutor getUserTaskExecutor(){
        this.userTaskExecutor.prestartAllCoreThreads();
        return this.userTaskExecutor;
    }

    public void setUserTaskExecutor(int corePoolSize, int maximumPoolSize ){

        final CustomThreadPoolExecutor executor = new CustomThreadPoolExecutor(corePoolSize, maximumPoolSize,
                keepAliveTime, TimeUnit.MILLISECONDS, UBlockingQueue);

        executor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
                System.err.println("Failed adding "+ runnable + ":: retrying.......");

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                executor.execute(runnable);
            }
        });

        this.userTaskExecutor = executor;

    }



    }

