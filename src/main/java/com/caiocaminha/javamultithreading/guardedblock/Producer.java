package com.caiocaminha.javamultithreading.guardedblock;

import java.util.Random;

public class Producer implements Runnable {
    private Drop drop;

    public Producer(Drop drop) {
        this.drop = drop;
    }

    @Override
    public void run() {
        String messages[] = {
                "Mares eat oats",
                "Does eat oats",
                "Little lambs eat ivy",
                "A kid will eat ivy too"
        };
        Random random = new Random();

        for(int i = 0; i < messages.length; i++) {
            System.out.format("Sending Message: %s%n",messages[i]);
            drop.putMessage(messages[i]);
            try {
                Thread.sleep(random.nextInt(5000));
            } catch (InterruptedException e) {}
        }

        drop.putMessage("DONE");
    }
}
