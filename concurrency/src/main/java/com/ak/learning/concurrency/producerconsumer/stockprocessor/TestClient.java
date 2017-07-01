package com.ak.learning.concurrency.producerconsumer.stockprocessor;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestClient {
    public static void main(String[] args) {
        Queue<Stock> queue = new ArrayBlockingQueue<Stock>(100);
        ExecutorService pool = Executors.newFixedThreadPool(3);
        StockProducer producer = new StockProducer(queue);
        GoogleStockProcessor googProcessor = new GoogleStockProcessor(queue);
        AppleStockProcessor appProcessor = new AppleStockProcessor(queue);
        pool.submit(producer);
        pool.submit(googProcessor);
        pool.submit(appProcessor);
        pool.shutdown();
    }
}
