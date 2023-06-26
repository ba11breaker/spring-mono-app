package com.ba11breaker.general.asynchrony.third;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class TestCompletableFutureSet {
    private final static int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
    private final static ThreadPoolExecutor POOL_EXECUTOR = new ThreadPoolExecutor(
        AVAILABLE_PROCESSORS, AVAILABLE_PROCESSORS*2, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(5), new ThreadPoolExecutor.CallerRunsPolicy()
    );

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        long start = System.currentTimeMillis();
        // runCompletableFuture();
        // runAsync();
        // supplAsync();
        // thenRun();
        // thenAccept();
        // thenApply();
        whenComplete();
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

    public static void runAsync() throws InterruptedException, ExecutionException, TimeoutException {
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

    public static void supplAsync() throws InterruptedException, ExecutionException {
        CompletableFuture<?> future = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "hello, FutureCompletable";
            }
        });
        System.out.println(future.get());
    }

    public static void thenRun() throws InterruptedException, ExecutionException {
        CompletableFuture<String> oneFuture = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "hello";
            }
        });
        CompletableFuture<?> twoFuture = oneFuture.thenRun(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
                System.out.println("---after oneFuture over doSomething---");
            }
        });
        System.out.println(twoFuture.get());
    }

    public static void thenAccept() throws InterruptedException, ExecutionException {
        CompletableFuture<String> oneFuture = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "hello";
            }
        });
        CompletableFuture<?> twoFuture = oneFuture.thenAccept(new Consumer<String>() {
            @Override
            public void accept(String t) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
                System.out.println("---after oneFuture over doSomething---" + t);
            }
        });
        System.out.println(twoFuture.get());
    }

    public static void thenApply() throws InterruptedException, ExecutionException {
        CompletableFuture<String> oneFuture = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "hello";
            }
        });
        CompletableFuture<String> twoFuture = oneFuture.thenApply((String t) -> {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return t + " world";
        });
        System.out.println(twoFuture.get());
    }

    public static void whenComplete() throws InterruptedException, ExecutionException {
        long start = System.currentTimeMillis();
        CompletableFuture<String> future= CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "hello, world";
            }
        });
        future.whenComplete((String t, Throwable u) -> {
            if (u == null) {
                System.out.println(t);
            } else {
                System.out.println(u.getLocalizedMessage());
            }
        });
        System.out.println(System.currentTimeMillis()-start);
        Thread.currentThread().join();
    }
}
