package com.ak.learning.concurrency.producerconsumer.stockprocessor;

import java.math.BigDecimal;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

public class StockProducer implements Runnable {
    private Queue<Stock> queue;

    public StockProducer(Queue<Stock> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            int number = (int) Math.round(Math.random() * 20);
            Double value = Math.random() * 100;
            BigDecimal price = new BigDecimal(value).setScale(2,BigDecimal.ROUND_HALF_UP);
            if (number % 2 == 0) {
                queue.offer(new Stock("APPL", price));
            } else {
                queue.offer(new Stock("GOOG", price));
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
