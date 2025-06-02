package com.caiocaminha.javamultithreading.threads;

import java.awt.font.NumericShaper;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ThreadHackerPoliceExample {
    private static final int MAX_PASSWORD= 9999;

    public static void main(String[] args) {
        var random = new Random();
        Vault vault = new Vault(random.nextInt(MAX_PASSWORD));

        List<Thread> threads = new ArrayList<>();
        threads.add(new AscendingHackerThread(vault));
        threads.add(new DescendingHackerThread(vault));
        threads.add(new PoliceThread());

        for (Thread thread : threads) {
            thread.start();
        }
    }

    private static class Vault {
        private int password;
        private static String name;


        public Vault(int password) {
            this.password = password;
        }

        public boolean isCorrectPassword(int guess) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {}
            return this.password == guess;
        }
    }

    private static abstract class HackerThread extends Thread {
        protected Vault vault;

        public HackerThread(Vault vault) {
            this.vault = vault;
            this.setName(this.getClass().getSimpleName()); //using reflection
            this.setPriority(Thread.MAX_PRIORITY);
        }

        @Override
        public void start() {
            System.out.format("Starting Thread %s", this.getName());
            super.start();
        }
    }

    private static class AscendingHackerThread extends HackerThread {
        public AscendingHackerThread(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            for (int i = 0; i <= MAX_PASSWORD; i++) {
                if(vault.isCorrectPassword(i)) {
                    System.out.format("%s Gessed the correct password %s", this.getName(), i);
                    System.exit(0);
                }
            }
        }
    }

    private static class DescendingHackerThread extends HackerThread {
        public DescendingHackerThread(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            for (int i = MAX_PASSWORD; i >= 0; i--) {
                if(vault.isCorrectPassword(i)) {
                    System.out.format("%s Gessed the correct password %s", this.getName(), i);
                    System.exit(0);
                }
            }
        }
    }

    private static class PoliceThread extends Thread {
        @Override
        public void run() {
            for(int i = 10; i >= 0; i-- ) {
                try {
                    Thread.sleep(1000);
                }catch(InterruptedException e) {
                }
                System.out.println(i);
            }
            System.out.println("Game over");
            System.exit(0);
        }
    }
}
