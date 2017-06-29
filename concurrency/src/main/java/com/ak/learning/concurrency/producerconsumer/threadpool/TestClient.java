package com.ak.learning.concurrency.producerconsumer.threadpool;

public class TestClient {

    private static final int NTHREADS = 10;
    private static final int NREQUESTS = 10;

    public static void main(String[] args) throws InterruptedException {
        ThreadPool tp = ThreadPool.createFixedThreadPool(NTHREADS, NREQUESTS);
        tp.startPool();
        tp.submit(new DatabaseTask());
        tp.submit(new FileSearchTask());
        tp.submit(new BigDataComputeTask());
        tp.submit(new StockPriceUpdateTask());
        //tp.stopPool();
        System.out.println("Done executing Thread pool");
    }
}
