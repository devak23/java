package com.ak.learning.concurrency.producerconsumer;

import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

public class TestClient {

    public static void main(String[] args) throws InterruptedException {
        final Queue<Person> queue = new PriorityBlockingQueue<>(5);
        Producer producer = new Producer(queue);
        Consumer consumer1 = new Consumer(queue);
        Consumer consumer2 = new Consumer(queue);

        Thread worker1 = new Thread(producer);
        Thread worker2 = new Thread(consumer1);
        Thread worker3 = new Thread(consumer2);

        worker1.start();
        worker2.start();
        worker3.start();

        // make the main thread sleep for 60 seconds
        TimeUnit.SECONDS.sleep(60);
        producer.stopProduction();
        consumer1.stopConsumption();
        consumer2.stopConsumption();
        System.out.println("Done!");
    }
}
