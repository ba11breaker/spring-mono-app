package com.ba11breaker.general.asynchrony.second;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class AsyncThreadPoolExample {
    
    private final static int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
    private final static ThreadPoolExecutor POOL_EXECUTOR = new ThreadPoolExecutor(
        AVAILABLE_PROCESSORS, AVAILABLE_PROCESSORS*2, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(5), new ThreadPoolExecutor.CallerRunsPolicy()
    );

    public static String doSomethingA() {  

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("--- doSomethingA---");
        return "A Task Done";
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException  {
        long start = System.currentTimeMillis();
        Future<?> resultA = POOL_EXECUTOR.submit(() -> doSomethingA());
        Future<?> resultB = POOL_EXECUTOR.submit(() -> doSomethingA());
        System.out.println(resultA.get());
        System.out.println(resultB.get());
        System.out.println(System.currentTimeMillis()-start);
    } 
}
