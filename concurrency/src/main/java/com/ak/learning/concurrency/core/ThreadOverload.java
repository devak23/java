package com.ak.learning.concurrency.core;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

// Check how many threads are allowed by your machine. 10308 on my linux.
public class ThreadOverload {
    public static void main(String[] args) {
        AtomicInteger number = new AtomicInteger();
        for (;;) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(number.incrementAndGet());
                    try {
                        TimeUnit.SECONDS.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }).start();
        }
    }
}
