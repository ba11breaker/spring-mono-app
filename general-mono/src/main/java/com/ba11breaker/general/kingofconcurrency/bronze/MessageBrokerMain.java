package com.ba11breaker.general.kingofconcurrency.bronze;

import java.util.LinkedList;

public class MessageBrokerMain {
    private static final LinkedList<String> messageQueue = new LinkedList<String>();

    static class MessageProducer implements Runnable {
        public void run() {
            try {
                createMessage();
            } catch (InterruptedException e) {
                System.out.println("The producer is brokern");
            }
        }

        public static void main(String[] args) {
            Thread producer = new Thread(new MessageProducer());
            Thread consumer = new Thread(new MessageConsumer());

            producer.start();
            consumer.start();
        }   
    
        public void createMessage() throws InterruptedException {
            for (int i = 0 ;; i++) {
                synchronized(messageQueue) {
                    if (messageQueue.size() == 0) {
                        messageQueue.add("message " + i);
                        System.out.println(messageQueue.getLast());
                        messageQueue.notify();
                    }
                }
                Thread.sleep(1000);
            }
        }
    }

    static class MessageConsumer implements Runnable {

        public void run() {
            try {
                consumeMessage();
            } catch (InterruptedException e) {
                System.out.println("The consumer is broken");
            }
        }
        
        public void consumeMessage() throws InterruptedException {
            while(true) {
                synchronized(messageQueue) {
                    if (messageQueue.size() == 0) {
                        messageQueue.wait();                    
                    }
                    String message = messageQueue.getLast();
                    messageQueue.remove(message);
                    System.out.println("consume message: " + message);
                }
            }
        }
    }
}
