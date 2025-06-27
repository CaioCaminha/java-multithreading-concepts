package com.caiocaminha.javamultithreading.waitNotifyAllExamples;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CustomSimpleCountDownLatch {
    public static class SimpleCountDownLatch {
        private AtomicInteger count;

        public SimpleCountDownLatch(int count) {
            this.count = new AtomicInteger(count);
            if (count < 0) {
                throw new IllegalArgumentException("count cannot be negative");
            }
        }

        /**
         * Causes the current thread to wait until the latch has counted down to zero.
         * If the current count is already zero then this method returns immediately.
         */
        public synchronized void await() throws InterruptedException {
            while(count.get() > 0) {
                wait();
            }
            return;
        }

        /**
         *  Decrements the count of the latch, releasing all waiting threads when the count reaches zero.
         *  If the current count already equals zero then nothing happens.
         */
        public void countDown() {
            count.decrementAndGet();
            if(count.get() == 0) {
                notifyAll();
            }
        }

        /**
         * Returns the current count.
         */
        public int getCount() {
            return count.get();
        }
    }

    /**
     * This example it's using the condition variable from ReentrantLock
     * By calling lock.newCondition we can retrieve an instance of Condition
     */
    public static class AlternativeSimpleCountDownLatch {
        private AtomicInteger count;
        private Lock lock = new ReentrantLock();
        private Condition condition = lock.newCondition();

        public AlternativeSimpleCountDownLatch(int count) {
            this.count = new AtomicInteger(count);
            if (count < 0) {
                throw new IllegalArgumentException("count cannot be negative");
            }
        }

        /**
         * Causes the current thread to wait until the latch has counted down to zero.
         * If the current count is already zero then this method returns immediately.
         */
        public void await() throws InterruptedException {
            while(count.get() > 0) {
                if(lock.tryLock()){
                    try {
                        condition.await();
                    } finally {
                        lock.unlock();
                    }
                }
            }
            return;
        }

        /**
         *  Decrements the count of the latch, releasing all waiting threads when the count reaches zero.
         *  If the current count already equals zero then nothing happens.
         */
        public void countDown() {
            count.decrementAndGet();
            if(count.get() == 0) {
                if(lock.tryLock()) {
                    try {
                        notifyAll();
                    } finally {
                        lock.unlock();
                    }
                }
            }
        }

        /**
         * Returns the current count.
         */
        public int getCount() {
            return count.get();
        }
    }


}
