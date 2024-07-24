package com.modernjava.virtualthreads;


import com.modernjava.util.CommonUtil;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static com.modernjava.util.LoggerUtil.log;

public class MaxVirtualThreads {

    static AtomicInteger atomicInteger = new AtomicInteger();

    public static void doSomeWork(int index) {
        log("started doSomeWork : " + index);
        CommonUtil.sleep(5000); // blocking task
        log("finished doSomeWork : " + index);
    }

    public static void main(String[] args) {

        int MAX_THREADS =1000_000; //10000, 100_000, 1000_000
        log("Total # cores = " + CommonUtil.noOfCores());

        IntStream.rangeClosed(1, MAX_THREADS)
                .forEach((i) -> {
                    var threads = Thread.ofVirtual().start(() -> MaxVirtualThreads.doSomeWork(i));
                    atomicInteger.incrementAndGet();
                    log("No of threads : " + atomicInteger.get());
                });


        CommonUtil.sleep(10000);

        log("Program Completed!");
    }
}
