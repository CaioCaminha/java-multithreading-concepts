package com.caiocaminha.javamultithreading.virtualthreads;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class LimitConcurrencyWithSemaphore {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(5);
        try(ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            /**
             * Semaphore acts like a Mutex or Lock.
             * Will only allow 5 tasks to run concurrently
             */
            semaphore.acquire();
            executorService.submit(
                    () -> {

                    }
            ).get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
