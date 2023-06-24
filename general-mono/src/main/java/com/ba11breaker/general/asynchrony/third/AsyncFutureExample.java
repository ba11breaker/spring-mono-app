package com.ba11breaker.general.asynchrony.third;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class AsyncFutureExample {
    public static String doSomethingA() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("--- doSomethingA---");
        return "TaskAResult";
    }

    public static String doSomethingB() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("--- doSomethingB---");
        return "TaskBResult";
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        
        long start = System.currentTimeMillis();
        FutureTask<String> futureTask = new FutureTask<String>(() -> {
            String result = null;
            try {
                result = doSomethingA();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        });

        Thread thread = new Thread(futureTask, "threadA");
        thread.start();

        String taskBResult = doSomethingB();
        String taskAResult = futureTask.get();

        System.out.println(taskAResult + " " + taskBResult);

        System.out.println(System.currentTimeMillis()-start);
    }
}
