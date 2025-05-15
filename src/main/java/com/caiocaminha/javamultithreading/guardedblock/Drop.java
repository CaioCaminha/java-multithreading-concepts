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
                /**
                 * The Object.wait method will suspend the thread until a notification is sent
                 * But the notification that causes the Interruption on wait not necessarily is the one we are waiting for
                 * Because of that we have a try catch, if we receive the InterruptionException but the condition still the same
                 * we block the thread again.
                 */
                wait();
            }catch (InterruptedException e) {}
        }
        empty = true;

        /**
         * This will notify the ProducerThread that the condition has changed and the messages are empty again
         */
        notifyAll();
        return message;
    }

    public synchronized void putMessage(String message) {
        while(!empty) {
            try{
                /**
                 * The Object.wait method will suspend the thread until a notification is sent
                 * But the notification that causes the Interruption on wait not necessarily is the one we are waiting for
                 * Because of that we have a try catch, if we receive the InterruptionException but the condition still the same
                 * we block the thread again.
                 */
                wait();
            } catch (InterruptedException e) {}
        }
        empty = false;
        this.message = message;

        /**
         * This will notify the ConsumerThread that the condition has changed and the messages are not empty anymore
         */
        notifyAll();
    }
}
