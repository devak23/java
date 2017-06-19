package com.ak.learning.concurrency.reentrantlocks;

public class TestClient {
    public static void main(String[] args) {
        final BoundedBuffer bb = new BoundedBuffer();

        Thread producer = new Thread(new Runnable() {
            @Override
            public void run() {
                Thread.currentThread().setName("Producer");
                int count = 0;
                while (count  < 20) {
                    try {
                        System.out.println(Thread.currentThread().getName() + ": Putting " + "String " + count);
                        bb.put("String " + count);
                        count++;
                        Thread.sleep((int) (Math.random() * 5 * 100));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Done producing items... exiting");
            }
        });

        Thread consumer = new Thread(new Runnable() {
            @Override
            public void run() {
                Thread.currentThread().setName("Consumer");
                while (!bb.isEmpty()) {
                    try {
                        System.out.println(Thread.currentThread().getName() + ": Retrieving " + bb.take());
                        Thread.sleep((int) (Math.random() * 10 * 100));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Bounded buffer is empty... exiting");
            }
        });

        producer.start();
        consumer.start();
    }
}
