package com.caiocaminha.javamultithreading.guardedblock;

import java.util.Random;

public class Consumer implements Runnable {
    private Drop drop;

    public Consumer(Drop drop) {
        this.drop = drop;
    }

    @Override
    public void run() {
        var random = new Random();
        /**
         * drop.getMessage() is the initialization section
         * !message.equals(DONE) - is the condition section: Runs while the message is not "DONE"
         * last drop.getMessage - is the update section
         */
        for(String message = drop.getMessage(); !message.equals("DONE"); message = drop.getMessage()) {
            System.out.format("Message Received: %s%n", message);
            try{
                Thread.sleep(random.nextInt(5000));
            } catch (InterruptedException e) {}
        }
    }
}
