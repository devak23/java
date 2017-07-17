package com.ak.learning.concurrency.synchronizers.semaphores;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * A semaphore is the one that protects the access to one or more shared resources.
 * It is essentially a counter which can be incremented or decremented by acquiring
 * and releasing it. If the counter is greater than 0, it means there are free resources
 * that can be used. IF the counter == 0, the semaphore puts the thread to sleep till
 * the value is non-zero
 *
 * The following PrintQueue implements the control via the semaphore. This is a special
 * case of Binary Semaphore i.e. the Semaphore can take only 2 values 0/1
 * @param <T>
 */
public class PrintQueue<T> {
    // used to control the access to the queue
    private final Semaphore semaphore;

    public PrintQueue() {
        semaphore = new Semaphore(1);
    }

    public void printJob(T document) {
        try {
            // first we need to acquire the semaphore
            semaphore.acquire();
            // simulate the document printing delay
            long duration = (long) (Math.random() * 10);
            System.out.printf("%s: PrintQueue: Printing %s during %d seconds\n", Thread.currentThread().getName(), document, duration);
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            // finally release the semaphore. This will make the value 1 so that the print
            // queue is accesessible to other thrreads
            semaphore.release();
        }
    }
}
