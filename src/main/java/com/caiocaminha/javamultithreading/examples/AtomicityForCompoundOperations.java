package com.caiocaminha.javamultithreading.examples;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AtomicityForCompoundOperations {


    private static class SyncronizedInventory {
        private int counter = 0;

        public synchronized int increment() {
            counter++;
            return counter;
        }

        public synchronized int decrement() {
            counter--;
            return counter;
        }

        public int getCounter() {
            return counter;
        }
    }


    private static class SynchronizedBlockInventory {
        private int counter = 0;
        private final Object lock = new Object();

        public int increment() {
            synchronized (lock) {
                counter++;
            }
            return counter;
        }

        public int decrement() {
            synchronized (lock) {
                counter--;
            }
            return counter;
        }

        public int getCounter() {
            return counter;
        }
    }


    private static class ReetrantLockInventory {
        private int counter = 0;
        private Lock lock = new ReentrantLock(true);

        public int increment() {
            lock.lock();
            try {
                counter++;
            } finally {
                lock.unlock();
            }
            return counter;
        }

        public int decrement() throws InterruptedException {
            if(lock.tryLock(100, TimeUnit.MILLISECONDS)){
                try {
                    counter--;
                } finally {
                    lock.unlock();
                }
            }
            return counter;
        }
    }
}
