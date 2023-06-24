package com.ba11breaker.general.kingofconcurrency.bronze;

public class ExceptionMain {
    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        Thread neZhaThread = new Thread() {
            public void run() {
                throw new RuntimeException("I'm nezha, I'm attacked!");
            }
        };
        neZhaThread.setName("Nezha");
        neZhaThread.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        neZhaThread.start();
        throw new NullPointerException();
    }

    static class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            System.out.println("Error! Thread Name:" + t.getName() + ", error message: " + e.getMessage());
        }
        
    }
}
