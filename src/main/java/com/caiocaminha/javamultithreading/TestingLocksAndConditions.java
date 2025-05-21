package com.caiocaminha.javamultithreading;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestingLocksAndConditions {
    /**
     * Will use the same wait-notify process that using wait and notify objects from Object Monitor
     * notFull and notEmpty are the conditions
     * while the items is not empty we wait until something is added and a signal is emmited
     * while the items is not full we wait until all elements are added and a signal is emmited
     */
    class BoundedBuffer {
        final Lock lock = new ReentrantLock();
        final Condition notFull = lock.newCondition();
        final Condition notEmpty = lock.newCondition();


        final Object[] items = new Object[100];
        int putptr, takeptr, count;

        public void put(Object x) throws InterruptedException {
            lock.lock();
            try {
                while (count == items.length)
                    notFull.await();
                items[putptr] = x;
                if(++putptr == items.length) putptr = 0;
                ++count;
                notEmpty.signal();
            } finally {
                lock.unlock();
            }
        }

        public Object take() throws InterruptedException {
            lock.lock();
            try {
                while(count == 0)
                    notEmpty.await();
                Object x = items[takeptr];
                if(++takeptr == items.length) takeptr = 0;
                --count;
                notFull.signal();
                return x;
            }finally {
                lock.unlock();
            }
        }
    }


}
