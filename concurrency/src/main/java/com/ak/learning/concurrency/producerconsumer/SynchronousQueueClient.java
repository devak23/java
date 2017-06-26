package com.ak.learning.concurrency.producerconsumer;

import com.ak.learning.concurrency.producerconsumer.priorityqueue.Person;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class SynchronousQueueClient {
    public static void main(String[] args) {
        /*
          If you see the output carefully then you would have noticed that order of events are reversed.
          Seems [CONSUMER] thread is consuming data even before [PRODUCER] thread has produced it. This happens because
          by default SynchronousQueue doesn't guarantee any order, but it has a fairness policy, which if set to true
          allows access to threads in FIFO order. You can enable this fairness policy by passing true to overloaded
          constructor of SynchronousQueue
          (http://javarevisited.blogspot.com/2014/06/synchronousqueue-example-in-java.html#ixzz4l50OwNJA)
         */
        final SynchronousQueue<Person> queue = new SynchronousQueue<>(true);
        Thread producer = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Person p = new Person();
                    p.setAge((int) Math.ceil(Math.random() * 20));
                    try {
                        queue.put(p);
                        System.out.println(Thread.currentThread().getName() + ": Published person. age = " + p.getAge());
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        producer.setName("[PRODUCER]");
        producer.start();

        Thread consumer = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Person p = queue.take();
                        System.out.println(Thread.currentThread().getName() + ": Received person. age = " + p.getAge());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        consumer.setName("[CONSUMER]");
        consumer.start();
    }
}
