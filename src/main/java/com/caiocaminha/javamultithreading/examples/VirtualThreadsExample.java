package com.caiocaminha.javamultithreading.examples;

public class VirtualThreadsExample {

    public static void main(String[] args) {
        Runnable runnable = () -> System.out.println("Hello World!");

        Thread platformThread = Thread.ofPlatform().unstarted(runnable);

        Thread virtualThread = Thread.ofVirtual().unstarted(runnable);
    }
}
