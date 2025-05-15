package com.caiocaminha.javamultithreading;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Implementing atomicity on a counter object using ReetrantLock.
 * Reetrant Locking is allowing a thread to acquire the lock for the same object more than once.
 * Preventing the Thread to suspend itself.
 */
public class SynchronizedCounter {
    private int counter = 0;
    private final Lock lock = new ReentrantLock();

    public int getCount() {
        lock.lock();
        try {
            return counter;
        } finally {
            lock.unlock();
        }
    }

    public void increment() {
        lock.lock();
        try {
            counter++;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Uses tryLock to check if it's possible to acquire the lock
     */
    public void decrement() throws InterruptedException {
        if(lock.tryLock(100, TimeUnit.SECONDS)) {
            try {
                counter--;
            } finally {
                lock.unlock();
            }
        } else {
            System.out.println("Failed to acquire lock");
        }
    }
}


/**
 * Implementing atomicity on a counter object using AtomicInteger which already provides
 * atomic operations.
 */
class AtomicCounter {
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    public int getCount() {
        return atomicInteger.get();
    }

    public int incrementAndGet() {
        return atomicInteger.incrementAndGet();
    }
}

class GenericExample <T> {
    public void print(T object) {
        System.out.println(object);
    }

    public <R> Set<R> groupToSet(T object) {
        return Set.of((R) object);
    }
}