package com.caiocaminha.javamultithreading.examples;

import java.util.Objects;

public class DoubleCheckedLockingExample {

    public class BrokenSingleton {
        private static BrokenSingleton instance;

        public static BrokenSingleton getInstance() {
            //The Idea is to only go through the process of synchronization if the instance is really null
            // synchronized block has a big overhead + it's blocking since it's acquiring BrokenSingleton's lock
            if( instance == null ) { // first check - UnSynchronized
                synchronized (BrokenSingleton.class) {
                    if(instance == null ) { // Second Check - Synchronized
                        /**
                         * The JVM will translate this new BrokenSingleton() into
                         * 1. Allocate memory
                         * 2. Initialize fields ( execute constructor for BrokenSingleton)
                         * 3. Assign reference of allocated memory to instance
                         * The problem is that the third step may be reordered to come first than the second.
                         *  - Allocate memory -> Assign reference to instance and only after that initialize the
                         *  fields by running the constructor.
                         *
                         *  Another thread can see a non-null but partially constructed object. That's a classic
                         *  race condition and causes undefined behavior.
                         */
                        //instance = new BrokenSingleton();
                    }
                }
            }

            return instance;
        }
    }

    public class CorrectSingleton {
        private static volatile CorrectSingleton instance;

        /**
         * Adding the volatile keyword to instance will prevent the third step
         * of allocating the memory to be reordered.
         * Will force the JVM to keep the program order establishing a synchronization order
         * among other threads
         */


        public static CorrectSingleton getInstance() {
            if( instance == null ) {
                synchronized (CorrectSingleton.class) {
                    if(instance == null ) {
                        //instance = new CorrectSingleton();
                    }
                }
            }
            return instance;
        }
    }

    /**
     * This is the faster approach - JVM treats enum constructors as implicitly thread-safe during initialization.
     * Enum instances are static final fields and their constructor is atomic.
     * Plus doesn't have the memory barriers as the volatile field.
     * And doesn't have the overhead of acquiring and releasing a lock.
     */
    public static class Singleton {

        private enum Holder {
            INSTANCE;

            private final Singleton instance;

            Holder() {
                instance = new Singleton();
            }

            private Singleton getInstance() {
                return this.instance;
            }
        }

        public static Singleton getInstance() {
            return Holder.INSTANCE.getInstance();
        }
    }

}
