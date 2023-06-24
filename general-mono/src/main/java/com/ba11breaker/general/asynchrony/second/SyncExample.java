package com.ba11breaker.general.asynchrony.second;

public class SyncExample {
    public static void doSomethingA() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("--- doSomethingA---");
    }

    public static void doSomethingB() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("--- doSomethingB---");
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        // doSomethingA();
        // doSomethingB();
        Thread thread = new Thread(() -> {
            try {
                doSomethingA();
            } catch(Exception e) {
                e.printStackTrace();
            }
        });
        try {
            thread.start();
            doSomethingB();
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis()-start);
    }
}
