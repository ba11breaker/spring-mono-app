package com.ba11breaker.general.kingofconcurrency.bronze;

public class SynchronizedMain {
    public static void main(String[] args) {
        final Master master = new Master();

        Thread neZhaAttachThread = new Thread() {
            public void run() {
                while (master.isAlive()) {
                    try {
                        int remainBlood = master.decreaseBlood();
                        System.out.println("Nezha beat Master to " + remainBlood);
                        if (remainBlood == 0) {
                            System.out.println("Nezha beated Master!");
                        }
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Thread lanLingWangThread = new Thread() {
            public void run() {
                while (master.isAlive()) {
                    try {
                        int remainBlood = master.decreaseBlood();
                        System.out.println("Lanlingwang beat Master to " + remainBlood);
                        if (remainBlood == 0) {
                            System.out.println("Lanlingwang beated Master!");
                        }
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        lanLingWangThread.start();
        neZhaAttachThread.start();
    }
}

class Master {

    private int blood = 100;

    public synchronized int decreaseBlood() throws Exception {
        if (blood <= 0) {
            throw new Exception("Master got beated!");
        }
        blood = blood - 5;
        return blood;
    }


    public boolean isAlive() {
        return blood > 0;
    }
}