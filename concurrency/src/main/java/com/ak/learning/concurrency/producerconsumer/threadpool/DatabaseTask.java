package com.ak.learning.concurrency.producerconsumer.threadpool;


import java.util.concurrent.TimeUnit;

public class DatabaseTask implements Runnable {
    @Override
    public void run() {
        System.out.println("Running a stored proc...");
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Done running the Stored procedure.");
    }
}
