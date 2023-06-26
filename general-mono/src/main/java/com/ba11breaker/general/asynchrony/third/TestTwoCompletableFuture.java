package com.ba11breaker.general.asynchrony.third;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class TestTwoCompletableFuture {
    private final static int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
    private final static ThreadPoolExecutor POOL_EXECUTOR = new ThreadPoolExecutor(
        AVAILABLE_PROCESSORS, AVAILABLE_PROCESSORS*2, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(5), new ThreadPoolExecutor.CallerRunsPolicy()
    );

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        long start = System.currentTimeMillis();
        // thenCompose();
        // thenCombine();
        allOf();
        System.out.println(System.currentTimeMillis()-start);
    }


    public static CompletableFuture<String> doSomethingOne(String encodeCompanyId) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String id = encodeCompanyId;
            return id;
        });
    }

    public static CompletableFuture<String> doSomethingTwo(String companyId) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String id = companyId + ":alibaba";
            return id;
        });
    }


    public static void thenCompose() throws InterruptedException, ExecutionException{
        CompletableFuture<?> result = doSomethingOne("123").thenCompose(id -> doSomethingTwo(id));
        System.out.println(result.get());
    }

    public static void thenCombine() throws InterruptedException, ExecutionException{
        CompletableFuture<?> result = doSomethingOne("123").thenCombine(doSomethingTwo("456"), (one, two) -> {
            return one + " " + two;
        });
        System.out.println(result.get());
    }

    public static void allOf() throws InterruptedException, ExecutionException{
        List<CompletableFuture<String>> futureList = new ArrayList<>();

        futureList.add(doSomethingOne("1"));
        futureList.add(doSomethingOne("2"));
        futureList.add(doSomethingOne("3"));
        futureList.add(doSomethingOne("4"));

        CompletableFuture<?> result = CompletableFuture.allOf(futureList.toArray(
            new CompletableFuture[futureList.size()]
        ));
        System.out.println(result.get());
        System.out.println(futureList.get(0).get());
        System.out.println(futureList.get(1).get());
        System.out.println(futureList.get(2).get());
        System.out.println(futureList.get(3).get());
    }

    public static void anyOf() throws InterruptedException, ExecutionException{
        List<CompletableFuture<String>> futureList = new ArrayList<>();

        futureList.add(doSomethingOne("1"));
        futureList.add(doSomethingOne("2"));
        futureList.add(doSomethingOne("3"));
        futureList.add(doSomethingOne("4"));

        CompletableFuture<?> result = CompletableFuture.anyOf(futureList.toArray(
            new CompletableFuture[futureList.size()]
        ));
        System.out.println(result.get());
    }
}

