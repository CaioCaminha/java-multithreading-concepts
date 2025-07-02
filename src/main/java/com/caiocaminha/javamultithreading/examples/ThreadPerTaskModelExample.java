package com.caiocaminha.javamultithreading.examples;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPerTaskModelExample {

    private static final int NUMBER_OF_TASKS = 10000;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press enter to start");
        scanner.nextLine();
        System.out.printf("Running %d tasks\n", NUMBER_OF_TASKS);

        var start =  System.currentTimeMillis();

        performTasks();
        System.out.printf("Tasks took %dms to complete\n", System.currentTimeMillis() - start);
    }

    private static void performTasks() {
        try (ExecutorService executorService = Executors.newFixedThreadPool(1000)) {
            for(int i = 0; i < NUMBER_OF_TASKS; i++) {
                executorService.submit(ThreadPerTaskModelExample::blockingOperation);
            }
        }
    }

    private static void testing() {
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            Thread newThread =  new Thread(ThreadPerTaskModelExample::blockingOperation);

            newThread.setDaemon(true);

            threads[i] = newThread;
        }

        for (Thread thread : threads) {
            thread.start();
        }
    }


    private static void blockingOperation() {
        System.out.println("Starting blocking operation");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
