package com.ak.learning.concurrency.virtualthreads;

// The goal of this problem is to highlight how virtual threads interact with synchronous code. For this, we use the
// built-in java.util.concurrent.SynchronousQueue. This is a built-in blocking queue that allows only one thread to
// operate at a time. More precisely, a thread that wants to insert an element in this queue is blocked until another
// thread attempts to remove an element from it, and vice versa. Basically, a thread cannot insert an element unless
// another thread attempts to remove an element.

import java.util.concurrent.SynchronousQueue;
import java.util.logging.Logger;

import static com.ak.learning.concurrency.Utils.sleep;


public class SynchronizationMain {
    private static final Logger LOGGER = Logger.getLogger(SynchronizationMain.class.getName());

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tT] [%4$-7s] %5$s %n");


        SynchronousQueue<Integer> queue = new SynchronousQueue<>();

        Runnable taskr = getRunnable(queue);

        LOGGER.info("In main: creating a producer virtual thread before running the task");
        Thread producerVT = Thread.ofVirtual().start(taskr);
        LOGGER.info("In main: Producer: " + producerVT.toString() + " created.");

        // So the virtual thread (producerVT) waits for 5 seconds before attempting to insert an element into the queue.
        // However, it will not successfully insert an element until another thread attempts to remove an element from
        // this queue
        LOGGER.info(() -> "In main: Consumer: " + Thread.currentThread() + " can't take from the queue yet. This thread is blocked on queue.take()...");
        int maxInt = queue.take();

        LOGGER.info(() -> "In main: Consumer: " + Thread.currentThread() + " took from queue: " + maxInt);
        LOGGER.info("In main: End of task!");
    }

    private static Runnable getRunnable(SynchronousQueue<Integer> queue) {
        return () -> {
            LOGGER.info(() -> "Producer: " + Thread.currentThread() + " sleeps for 5 seconds");
            sleep(5);
            LOGGER.info(() -> "Producer: " + Thread.currentThread() + " woke up. Adding the MAGIC_NUMBER");
            queue.add(Integer.MAX_VALUE);
            LOGGER.info(() -> "Producer: " + Thread.currentThread() + " added " + Integer.MAX_VALUE + " to queue");
        };
    }

    // OUTPUT:
    // [21:56:58] [INFO   ] In main: creating a producer virtual thread before running the task
    // [21:56:59] [INFO   ] Producer: VirtualThread[#22]/runnable@ForkJoinPool-1-worker-1 sleeps for 5 seconds
    // [21:56:59] [INFO   ] In main: Producer: VirtualThread[#22]/runnable created.
    // [21:56:59] [INFO   ] In main: Consumer: Thread[#1,main,5,main] can't take from the queue yet. This thread is blocked on queue.take()...
    // [21:57:04] [INFO   ] Producer: VirtualThread[#22]/runnable@ForkJoinPool-1-worker-4 woke up. Adding the MAGIC_NUMBER
    // [21:57:04] [INFO   ] Producer: VirtualThread[#22]/runnable@ForkJoinPool-1-worker-4 added 42 to queue
    // [21:57:04] [INFO   ] In main: Consumer: Thread[#1,main,5,main] took from queue: 42
    // [21:57:04] [INFO   ] In main: End of task!
}
