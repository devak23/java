package com.ak.learning.concurrency.locks;

/**
 * The ReadWriteLock operates on the principle that
 * 1. Multiple readers can read the resource if there are no writers writing to the
 * resource or write requests being placed on the resource
 * 2. A writer can write to a resource only if there are no writers writing to the
 * resource or readers reading the resource
 */
public class ReadWriteLock {
    private int readers;
    private int writers;
    private final Object lock = new Object();
    private int writeRequests;

    public void readLock() {
        synchronized (lock) {
            while (this.writers > 0 || this.writeRequests > 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            this.readers++;
            System.out.println(this.readers + " slots have been occupied");
        }
    }

    public void readUnlock() {
        synchronized (lock) {
            if (this.readers > 0) {
                int remainder = --this.readers;
                System.out.println("Slots remaining = " + remainder);
                notifyAll();
            }
        }
    }

    public void writeLock() {
        synchronized (lock) {
            this.writeRequests++;
            while (this.writers  > 0 || this.readers > 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            this.writers++;
            this.writeRequests--;
            System.out.println(this.writers + " slots have been occupied");
        }
    }

    public void writeUnlock() {
        synchronized (lock) {
            if (this.writers  > 0) {
                this.writers--;
                System.out.println(this.writers+ " slots are available");
                notifyAll();
            }
        }
    }
}
