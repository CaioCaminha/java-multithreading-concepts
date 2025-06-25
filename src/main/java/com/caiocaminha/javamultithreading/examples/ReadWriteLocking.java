package com.caiocaminha.javamultithreading.examples;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.*;
import java.util.concurrent.locks.*;

public class ReadWriteLocking {

    private static ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    private static Lock readLock = rwLock.readLock();

    private static Lock writeLock = rwLock.writeLock();

    public static void main(String[] args) {
        AtomicInteger x = new AtomicInteger(0);

        x.incrementAndGet();
        if(readLock.tryLock()) {
            try {

            } finally {
                readLock.unlock();
            }
        }
    }

}



class ProductReviewsService {
    private final HashMap<Integer, List<String>> productIdToReviews;

    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private Lock readLock = rwLock.readLock();
    private Lock writeLock = rwLock.writeLock();



    /********* DO NOT MODIFY THIS SECTION **************/

    public ProductReviewsService() {
        this.productIdToReviews = new HashMap<>();
    }

    /**
     * Adds a product ID if not present
     */
    public void addProduct(int productId) {
        Lock lock = getLockForAddProduct();

        lock.lock();

        try {
            if (!productIdToReviews.containsKey(productId)) {
                productIdToReviews.put(productId, new ArrayList<>());
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * Removes a product by ID if present
     */
    public void removeProduct(int productId) {
        Lock lock = getLockForRemoveProduct();

        lock.lock();

        try {
            if (productIdToReviews.containsKey(productId)) {
                productIdToReviews.remove(productId);
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * Adds a new review to a product
     * @param productId - existing or new product ID
     * @param review - text containing the product review
     */
    public void addProductReview(int productId, String review) {
        Lock lock = getLockForAddProductReview();

        lock.lock();

        try {
            if (!productIdToReviews.containsKey(productId)) {
                productIdToReviews.put(productId, new ArrayList<>());
            }
            productIdToReviews.get(productId).add(review);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Returns all the reviews for a given product
     */
    public List<String> getAllProductReviews(int productId) {
        Lock lock = getLockForGetAllProductReviews();

        lock.lock();

        try {
            if (productIdToReviews.containsKey(productId)) {
                return Collections.unmodifiableList(productIdToReviews.get(productId));
            }
        } finally {
            lock.unlock();
        }

        return Collections.emptyList();
    }

    /**
     * Returns the latest review for a product by product ID
     */
    public Optional<String> getLatestReview(int productId) {
        Lock lock = getLockForGetLatestReview();

        lock.lock();

        try {

            if (productIdToReviews.containsKey(productId) && !productIdToReviews.get(productId).isEmpty()) {
                List<String> reviews = productIdToReviews.get(productId);
                return Optional.of(reviews.get(reviews.size() - 1));
            }
        } finally {
            lock.unlock();
        }

        return Optional.empty();
    }

    /**
     * Returns all the product IDs that contain reviews
     */
    public Set<Integer> getAllProductIdsWithReviews() {
        Lock lock = getLockForGetAllProductIdsWithReviews();

        lock.lock();

        try {
            Set<Integer> productsWithReviews = new HashSet<>();
            for (Map.Entry<Integer, List<String>> productEntry : productIdToReviews.entrySet()) {
                if (!productEntry.getValue().isEmpty()) {
                    productsWithReviews.add(productEntry.getKey());
                }
            }
            return productsWithReviews;
        } finally {
            lock.unlock();
        }
    }

    /********* END OF UNMODIFIABLE SECTION **************/


    Lock getLockForAddProduct() {
        return writeLock;
    }

    Lock getLockForRemoveProduct() {
        return writeLock;
    }

    Lock getLockForAddProductReview() {
        return writeLock;
    }

    Lock getLockForGetAllProductReviews() {
        return readLock;
    }

    Lock getLockForGetLatestReview() {
        return readLock;
    }

    Lock getLockForGetAllProductIdsWithReviews() {
        return readLock;
    }


    public static class Barrier {
        private final int numberOfWorkers;
        private final Semaphore semaphore;
        private int counter = 0;
        private final Lock lock = new ReentrantLock();

        public Barrier(int numberOfWorkers) {
            this.numberOfWorkers = numberOfWorkers;
            this.semaphore = new Semaphore(0);
        }

        public void waitForOthers() throws InterruptedException {
            lock.lock();
            boolean isLastWorker = false;
            try {
                counter++;

                if (counter == numberOfWorkers) {
                    isLastWorker = true;
                }
            } finally {
                lock.unlock();
            }

            if (isLastWorker) {
                semaphore.release(numberOfWorkers - 1);
            } else {
                semaphore.acquire();
            }
        }
    }

    public static class CoordinatedWorkRunner implements Runnable {
        private final Barrier barrier;

        public CoordinatedWorkRunner(Barrier barrier) {
            this.barrier = barrier;
        }

        @Override
        public void run() {
            try {
                task();
            } catch (InterruptedException e) {
            }
        }

        private void task() throws InterruptedException {
            // Performing Part 1
            System.out.println(Thread.currentThread().getName()
                    + " part 1 of the work is finished");

            barrier.waitForOthers();

            // Performing Part2
            System.out.println(Thread.currentThread().getName()
                    + " part 2 of the work is finished");
        }
    }

    public static void main(String [] args) throws InterruptedException {
        int numberOfThreads = 200; //or any number you'd like

        List<Thread> threads = new ArrayList<>();

        Barrier barrier = new Barrier(numberOfThreads);
        for (int i = 0; i < numberOfThreads; i++) {
            threads.add(new Thread(new CoordinatedWorkRunner(barrier)));
        }

        for(Thread thread: threads) {
            thread.start();
        }
    }
}


