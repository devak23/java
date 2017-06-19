package com.ak.learning.concurrency.locks;

public class ReadWriteLock {
    private int DEFAULT_READERS = 10;
    private int DEFAULT_WRITERS = 10;
    private int DEFAULT_LIMIT = 10;

    private int readers;
    private int writers;
    private final Object lock = new Object();
    private int limit = 0;

    public ReadWriteLock() {
        this.readers = DEFAULT_READERS;
        this.writers = DEFAULT_WRITERS;
    }

    public ReadWriteLock(int readers, int writers, int limit) {
        this.readers = readers;
        this.writers = writers;
        this.limit = limit;
        this._validateAndLoadDefaults();
    }

    private void _validateAndLoadDefaults() {
        if (this.readers <= 0) {
            this.readers = DEFAULT_READERS;
        }
        if (this.writers  <= 0) {
            this.writers = DEFAULT_WRITERS;
        }
        if (this.limit <= 0) {
            this.limit = DEFAULT_LIMIT;
        }
    }

    public void readLock() {
        synchronized (lock) {
            while (this.readers > limit || this.writers != 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException("Cannot place a readLock on the resource");
                }
            }
            ++this.readers;
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
            while (this.writers  > 0 || this.writers > limit) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException("Cannot acquire lock");
                }
            }
            ++ this.writers;
            System.out.println(this.writers + " slots have been occupied");
        }
    }

    public void writeUnlock() {
        synchronized (lock) {
            if (this.writers  > 0) {
                -- this.writers;
                System.out.println(this.writers+ " slots are available");
                notifyAll();
            }
        }
    }
}
