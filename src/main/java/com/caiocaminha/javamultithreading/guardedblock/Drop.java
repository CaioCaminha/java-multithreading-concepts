package com.caiocaminha.javamultithreading.guardedblock;

public class Drop {
    private String message;

    /**
     * True if the consumer should wait for the producer to send the message
     * False if the producer should wait for the consumer to retrieve the message
     */
    private boolean empty = true;

    public synchronized String getMessage() {
        while(empty) {
            try {
                wait();
            }catch (InterruptedException e) {}
        }
        empty = true;

        notifyAll();
        return message;
    }

    public synchronized void putMessage(String message) {
        while(!empty) {
            try{
                wait();
            } catch (InterruptedException e) {}
        }
        empty = false;
        this.message = message;
        notifyAll();
    }
}
