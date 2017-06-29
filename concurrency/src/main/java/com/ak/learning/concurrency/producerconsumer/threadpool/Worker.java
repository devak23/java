package com.ak.learning.concurrency.producerconsumer.threadpool;

import java.util.Queue;

public class Worker extends Thread {
    private boolean running;
    private Queue<? super Runnable> taskQueue;
    private ThreadPool pool;

    public Worker(Queue<? super Runnable> queue, ThreadPool pool) {
        this.taskQueue = queue;
        this.pool = pool;
    }

    public void stopThread() {
        running = false;
    }

    @Override
    public void run() {
        while (!running)  { // or if the pool is still running...
            try {
                Runnable task = (Runnable) taskQueue.poll();
                if (task != null) {
                    task.run();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isRunning() {
        return this.running;
    }
}
