package com.ak.learning.concurrency.producerconsumer.threadpool;

import java.util.concurrent.TimeUnit;

public class StockPriceUpdateTask implements Runnable {
    @Override
    public void run() {
        System.out.println("Updating a stock price...");
        try {
            TimeUnit.SECONDS.sleep(7);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Done with updating Stock Price.");
    }
}
