package com.ak.learning.concurrency.producerconsumer.threadpool;

import java.util.concurrent.TimeUnit;

public class BigDataComputeTask implements Runnable {
    @Override
    public void run() {
        System.out.println("Running a compute on Hadoop cluster...");
        try {
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Done with BigData computing task.");
    }
}
