package com.caiocaminha.javamultithreading.basic;

import com.caiocaminha.javamultithreading.SynchronizedCounter;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureExample {

    public static void main(String[] args) {
        //this is often used for concurrent tasks
        var completableFuture = CompletableFuture.runAsync(() -> {
            System.out.println(new SynchronizedCounter().getCount());
        });



        completableFuture.join();
    }

}
