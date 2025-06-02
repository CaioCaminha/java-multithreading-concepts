package com.caiocaminha.javamultithreading.forkjoin;

import java.util.concurrent.RecursiveTask;

public class ForkJoinExample extends RecursiveTask<Integer> {
    private int n;

    public ForkJoinExample(int n) {
        this.n = n;
    }


    @Override
    protected Integer compute() {
        if(n <= 1 ) return n;
        var left = new  ForkJoinExample(n - 1);
        var right = new  ForkJoinExample(n + 2);

        left.fork();//Submits the task to the ForkJoinPool to be executed asynchronously by another thread

        right.compute();//Process the task directly on the current thread

        left.join();//Wait for the completion of left and retrieve the result once is done. Meanwhile, the current thread is blocked

        return left.join();
    }
}
