package com.ak.learning.concurrency.producerconsumer;

import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Consumer implements Runnable {
    private Queue<Person> queue;
    private AtomicBoolean stopFlag;

    public Consumer(Queue queue) {
        this.queue = queue;
        this.stopFlag = new AtomicBoolean(false);
    }

    public void run() {
        while (!stopFlag.get()) {
            Person person = queue.poll();
            if (person != null) {
                System.out.println("Thread-" + Thread.currentThread().getId() + ": Processed person of age = " + person.getAge());
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
    }

    public void stopConsumption() {
        this.stopFlag.set(true);
    }
}
