package com.caiocaminha.javamultithreading.examples;

public class DoubleCheckedLockingExample {

    public class BrokenSingleton {
        private static BrokenSingleton instance;

        public static BrokenSingleton getInstance() {
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
                        instance = new BrokenSingleton();
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
                        instance = new CorrectSingleton();
                    }
                }
            }
            return instance;
        }
    }

}
