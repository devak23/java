package com.ak.learning.concurrency.locks;

/**
 * An implementation of the ReentrantLock using wait() and notify().
 * A ReentrantLock allows locking of a resource more than one times by a single thread.
 */
public class ReentrantLock {
    private final Object monitor = new Object(); // a private monitor
    private Thread lockedBy;
    private int count;

    public void lock() throws InterruptedException {
        // it's always good to synchronize an object rather than synchronize the method
        // because a method locks the the entire object of the class. Thus, the external program
        // could synchronize on the instance of the object where this class is being used and
        // could cause a deadlock on the entire system. This is also called leaking of the lock's
        // monitor
        synchronized (monitor) {
            while (lockedBy != null && Thread.currentThread() != lockedBy) {
                this.wait();
            }
            this.lockedBy = Thread.currentThread();
            count++;
        }
    }

    public void unlock() {
        synchronized (monitor) {
            if (count > 0 && Thread.currentThread() == lockedBy) {
                --count;
            }

            if (count == 0) {
                lockedBy = null;
                this.notify();
            }
        }
    }
}
