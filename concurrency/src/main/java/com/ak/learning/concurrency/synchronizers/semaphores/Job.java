package com.ak.learning.concurrency.synchronizers.semaphores;

/**
 * The Job class represents the actual task that will get executed on the
 * print queue
 * @param <T>
 */
public class Job<T> implements Runnable {
    private PrintQueue<T> queue;
    private T document;

    public Job(PrintQueue<T> queue, T document) {
        this.queue = queue;
        this.document = document;
    }

    @Override
    public void run() {
        System.out.printf("%s: Going to print Job\n", Thread.currentThread().getName());
        queue.printJob(document);
        System.out.printf("%s: The document has been printed\n", Thread.currentThread().getName());
    }
}
