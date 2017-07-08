package com.ak.learning.concurrency.callablesandfutures.calculatingpi;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TestClient {

    public static void main(String[] args) {
        double pi = 0.0;
        ExecutorService pool = Executors.newCachedThreadPool();
        final long startTime = System.currentTimeMillis();
        final Future<Double> future = pool.submit(new PiCalculator());
        try {
            pi = future.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        final long endTime = System.currentTimeMillis();
        System.out.printf("Calculated the value of PI (%10.9f) in %d milliseconds: ", pi, (endTime - startTime));
        pool.shutdown();
    }
}
