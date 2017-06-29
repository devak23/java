package com.ak.learning.concurrency.producerconsumer.threadpool;

import java.util.concurrent.TimeUnit;

public class FileSearchTask implements Runnable {
    @Override
    public void run() {
        System.out.println("Searching a file for a particular word...");
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Done with File Search");
    }
}
