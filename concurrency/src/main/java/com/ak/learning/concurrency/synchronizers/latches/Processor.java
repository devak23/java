package com.ak.learning.concurrency.synchronizers.latches;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Processor implements Runnable {
    private final CountDownLatch latch;

    public Processor(CountDownLatch mylatch) {
        latch = mylatch;
    }

    @Override
    public void run() {
        System.out.println("Started...");
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        latch.countDown();
    }
}
