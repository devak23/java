package com.ak.learning.concurrency.producerconsumer.stockprocessor;

import java.math.BigDecimal;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

public class GoogleStockProcessor implements Runnable {
    private Queue<Stock> queue;
    private BigDecimal divisor = new BigDecimal(2l);

    public GoogleStockProcessor(Queue<Stock> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            Stock stock = queue.peek();
            if (stock != null && stock.getCode().equals("GOOG")) {
                System.out.println("[" + Thread.currentThread().getName() + "] processing Apple Stock " + stock.getPrice());
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
