package com.ak.learning.concurrency.producerconsumer.boundedbuffer;

import java.lang.reflect.Array;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GenericQueue<T> implements Queue<T> {
    // we need a lock for synchronizing operations
    private Lock lock = new ReentrantLock();
    // ... and two condition variables that denote if the readers
    // can take the items from the queue and the writers can
    // put the items in the queue
    private Condition notFull = lock.newCondition(); // notFull => so writers can write
    private Condition notEmpty = lock.newCondition(); // notEmpty => so readers can read
    // finally we need a backing data structure for the queue
    private LinkedList<T> items;
    private final int MAX_CAPACITY;
    private int index;

    public GenericQueue(Class<T> clazz, int size) {
        // Notice the way an array of generic type getting created
        items = new LinkedList<>();
        MAX_CAPACITY = size;
    }
    @Override
    public boolean put(T object) {
        // we are going to enclose the lock.lock() within the try
        // so even if some spurious signal happens after lock.lock()
        // and an exception is raised, we will be assured that the
        // lock will be released.
        try {
            lock.lock();
            // ask the producer thread to wait if
            while (items.size() >= MAX_CAPACITY) {
                notEmpty.await(); // this will block all the producers
            }
            items.add(object);
            // signal the blocked threads who are wishing to read from
            // the queue to begin reading once the lock is relinquished
            notFull.signalAll();
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            return false;
        } finally {
            lock.unlock();
        }
        return true;
    }

    @Override
    public T take() {
        T object = null;
        // once again open the try and lock the lock
        try {
            lock.lock(); //critical section begins
            // ask the consumer threads to wait if the queue is empty
            while (items.size() == 0) {
                notEmpty.await(); // this is a blocking call
            }
            object = items.remove(0); // always take from the front of the queue
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
        return object;
    }

    @Override
    public int count() {
        return items.size();
    }
}
