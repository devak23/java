package com.ak.learning.concurrency.producerconsumer.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadPool {
    private Lock executeLock;
    private Condition queueNotEmptyCondition; // this is accessed by the readers
    private Condition queueNotFullCondition; // this is accessed by writers
    private AtomicBoolean started = new AtomicBoolean(false);
    private Queue<? super Runnable> requestQueue;
    private List<Worker> workers;
    private int MAX_REQUESTS = 0;
    private int MAX_WORKERS = 0;

    private ThreadPool(int nThreads, int nRequests) {
        MAX_REQUESTS = nRequests;
        MAX_WORKERS = nThreads;
        requestQueue = new ArrayBlockingQueue<>(nRequests);
        executeLock = new ReentrantLock();
        queueNotEmptyCondition = executeLock.newCondition();
        queueNotFullCondition = executeLock.newCondition();
        workers = new ArrayList<>(nThreads);
        initializeWorkerThreads();
    }

    private void initializeWorkerThreads() {
        for (int i = 0; i < MAX_WORKERS; i++) {
            workers.add(new Worker(requestQueue, this));
        }
        for (Worker worker : workers) {
            worker.start();
        }
    }


    public void startPool() {
        started.getAndSet(true);
    }

    public void stopPool() {
        started.getAndSet(false);
        for (Worker worker : workers) {
            worker.stopThread();
        }
        workers.clear();
        requestQueue = null;
    }

    public void submit(Runnable task) {
        if (!started.get()) {
            throw new IllegalArgumentException("Pool has been stopped");
        }

        try {
            executeLock.lock();
            // while the queue is full, tell the writers to wait
            while (requestQueue.size() >= MAX_REQUESTS) {
                try {
                    queueNotFullCondition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // else add the task
            requestQueue.offer(task);
            // notify the others
            queueNotEmptyCondition.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executeLock.unlock();
        }
    }

    public static ThreadPool createFixedThreadPool(int nThreads, int nRequests) {
        return new ThreadPool(nThreads, nRequests);
    }

    public boolean isRunning() {
        return started.get();
    }
}
