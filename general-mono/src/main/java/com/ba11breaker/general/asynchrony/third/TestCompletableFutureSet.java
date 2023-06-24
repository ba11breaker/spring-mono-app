package com.ba11breaker.general.asynchrony.third;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class TestCompletableFutureSet {
    private final static int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
    private final static ThreadPoolExecutor POOL_EXECUTOR = new ThreadPoolExecutor(
        AVAILABLE_PROCESSORS, AVAILABLE_PROCESSORS*2, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(5), new ThreadPoolExecutor.CallerRunsPolicy()
    );

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        long start = System.currentTimeMillis();
        // runCompletableFuture();
        runAsync();
        System.out.println(System.currentTimeMillis()-start);
    }

    public static void runCompletableFuture() throws InterruptedException, ExecutionException, TimeoutException  {
        CompletableFuture<String> future = new CompletableFuture<String>();

        POOL_EXECUTOR.execute(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("----" + Thread.currentThread().getName() + "set future result----");
            future.complete("hello, completablefuture");
        });

        System.out.println("---main thread wait future result ---");
        System.out.println(future.get());
        System.out.println("---main thread got future result---");
    }

    public static void  runAsync()  throws InterruptedException, ExecutionException, TimeoutException {
        CompletableFuture<?> futureA = CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("over A");
            }
        });
        CompletableFuture<?> futureB = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("over B");
        });
        System.out.println(futureA.get());
        System.out.println(futureB.get());
    }
}
