package com.ak.learning.concurrency.core;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.LockSupport;

@Slf4j
public class HowManyClassicalThreadsMain {

    // The Thread.ofPlatform() method was added in JDK 19 to easily distinguish between Java threads
    //(i.e., classical Java threads as we have known them for decades – thin wrappers of OS threads) and
    //the new kids in town, virtual threads.
    public static void main(String[] args) {
        AtomicLong counterOsThreads = new AtomicLong();

        while (true) {
            Thread.ofPlatform().start(() -> {
               long currentOsThreadNr = counterOsThreads.getAndIncrement();
                System.out.println("Thread: " + currentOsThreadNr);
                LockSupport.park();
            });
        }
    }
}

// Output:
// [18.123s][warning][os,thread] Failed to start thread "Unknown thread" - pthread_create failed (EAGAIN) for attributes: stacksize: 1024k, guardsize: 4k, detached.
//[18.123s][warning][os,thread] Failed to start the native thread for java.lang.Thread "Thread-37894"
