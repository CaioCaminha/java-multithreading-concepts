package com.caiocaminha.javamultithreading.examples;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class AtomicMinMaxMetrics {
    public static class MinMaxMetrics {

        private AtomicInteger x;
        private AtomicLong min;
        private AtomicLong max;


        /**
         * Initializes all member variables
         */
        public MinMaxMetrics() {
            min = new AtomicLong(0);
            max = new AtomicLong(0);
        }

        /**
         * Adds a new sample to our metrics.
         */
        public void addSample(long newSample) {
            min.set(Math.min(min.get(), newSample));
            max.set(Math.max(max.get(), newSample));
        }

        /**
         * Returns the smallest sample we've seen so far.
         */
        public long getMin() {
            return min.get();
        }

        /**
         * Returns the biggest sample we've seen so far.
         */
        public long getMax() {
            return max.get();
        }
    }
}
