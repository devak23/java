package com.ak.learning.concurrency.producerconsumer.stockprocessor;

import java.util.Queue;
import java.util.concurrent.TimeUnit;

public class AppleStockProcessor implements Runnable {
    private final Queue<Stock> queue;


    public AppleStockProcessor(Queue<Stock> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            Stock stock = queue.peek();
            if (stock != null && stock.getCode().equals("APPL")) {
                System.out.println("[" + Thread.currentThread().getName() + "] processing Apple stock " + stock.getPrice());
                queue.remove();
            }
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
