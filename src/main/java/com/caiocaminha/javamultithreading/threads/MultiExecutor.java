package com.caiocaminha.javamultithreading.threads;

import java.util.ArrayList;
import java.util.List;

public class MultiExecutor {

    List<Runnable> tasks;

    /*
     * @param tasks to executed concurrently
     */
    public MultiExecutor(List<Runnable> tasks) {
        this.tasks = tasks;
    }

    /**
     * Starts and executes all the tasks concurrently
     */
    public void executeAll() {
        for (Runnable task : tasks) {
            new Thread(task).start();
        }
    }
}
