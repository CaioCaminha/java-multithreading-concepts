package com.caiocaminha.javamultithreading.waitNotifyAllExamples;

public class ProducerConsumerExample {
    public static void main(String[] args) {
        var drop = new Drop();
        (new Thread(new Producer(drop))).start();
        (new Thread(new Consumer(drop))).start();
    }
}
