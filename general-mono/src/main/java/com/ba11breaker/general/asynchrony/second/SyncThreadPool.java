package com.ba11breaker.general.asynchrony.second;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SyncThreadPool extends SyncExample {
    private final static int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
    private final static ThreadPoolExecutor POOL_EXECUTOR = new ThreadPoolExecutor(
        AVAILABLE_PROCESSORS, AVAILABLE_PROCESSORS*2, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(5), new ThreadPoolExecutor.CallerRunsPolicy()
    );         

    public static void main(String[] args)  {
        long start = System.currentTimeMillis();

        POOL_EXECUTOR.execute(() -> {
            try {
                doSomethingA();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        try {
            doSomethingB();
            System.out.println(System.currentTimeMillis()-start);
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }  
}
