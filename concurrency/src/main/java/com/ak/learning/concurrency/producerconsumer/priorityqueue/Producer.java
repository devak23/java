package com.ak.learning.concurrency.producerconsumer.priorityqueue;

import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Producer implements Runnable {
    private Queue<Person> queue;
    private AtomicBoolean stopFlag;

    public Producer(Queue<Person> queue) {
        this.queue = queue;
        this.stopFlag = new AtomicBoolean(false);
    }

    public void run() {
        while (!stopFlag.get()) {
            int age = (int) Math.ceil(Math.random() * 20);
            this.queue.offer(new Person().setAge(age));
            System.out.println("Enqueuing person of age = " + age);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
    }

    public void stopProduction() {
        this.stopFlag.set(true);
    }
}
