package com.caiocaminha.javamultithreading.basic;

import com.caiocaminha.javamultithreading.SynchronizedCounter;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ThreadCreatingTesting {
    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        Thread thread = new Thread(() -> {
            throw new RuntimeException("Intentional Exception thrown");
        });

        thread.setName("CreatedThread");

        /**
         * I'm allowed to pass a lambda to setUncaughtExceptionHandler becase it's expecting a UncaughtExceptionHandler
         * This interface is a Functional interface, meaning that only has one abstract method
         * void uncaughtException(Thread t, Throwable e); This way JVM already knows that this lambda is an implementation
         * of this FunctionalInterface. Without needing to speficy a Function type for example.
         */
        thread.setUncaughtExceptionHandler((Thread t, Throwable e) -> {
            System.out.format("Critical error detected in thread: %s exception is: %s", t.getName(), e.getMessage());
        });

        thread.start();

    }
}
