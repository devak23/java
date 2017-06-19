package com.ak.learning.concurrency.boundedbuffer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedBuffer {

    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition(); // used by the writing threads
    private final Condition notEmpty = lock.newCondition(); // used by the reading threads
    private final Object[] items = new Object[100];
    private  int putIdx, takeIdx, count;

    public void put(Object obj) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length) {
                // if there is no room left in the buffer, signal the other "writing threads" who are looking at
                // the "buffer-is-not-full-condition" to wait.
                System.out.println("Buffer is full...........waiting to populate");
                notFull.await();
            }
            items[putIdx] = obj; // put the item at the specified index

            //TODO: I don't understand why this if block is required.
            if (++putIdx == items.length) {
                putIdx = 0; // if the buffer is notEmpty, reset put index
            }
            count++;
            // signal the "reading threads" who are looking at the "buffer-is-not-empty-condition" to read
            // the buffer as they can find the element to read.
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public Object take() throws InterruptedException {
        lock.lock();
        try {
            // if the buffer is empty, ask the threads who are reading the buffer (ie. who are looking
            // at the "buffer-is-not-empty-condition" to wait
            while (count == 0) {
                System.out.println("Buffer is empty...........waiting to retrieve");
                notEmpty.await();
            }
            Object obj = items[takeIdx];
            items[takeIdx] = null;
            --count;
            if (++takeIdx == items.length) {
                takeIdx = 0;
            }
            // signal the threads who want to write into the buffer (ie. who are looking at the
            // "buffer-is-not-full-condition" to proceed.
            notFull.signal();
            return obj;
        } finally {
            lock.unlock();
        }
    }

    public boolean isEmpty() {
        return items.length == 0;
    }
}
