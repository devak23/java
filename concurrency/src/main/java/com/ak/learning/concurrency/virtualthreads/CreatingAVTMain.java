package com.ak.learning.concurrency.virtualthreads;

import com.ak.learning.concurrency.core.SimpleThreadFactory;

import java.util.concurrent.ThreadFactory;
import java.util.logging.Logger;

public class CreatingAVTMain {
    // for some reason logger with @Slf4j didn't work here.
    private static final Logger LOGGER = Logger.getLogger(CreatingAVTMain.class.getName());

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "[%1$tT] [%4$-7s] %5$s %n");

        LOGGER.info("Creating AVT threads");

        Runnable printMe = () -> LOGGER.info(Thread.currentThread().toString());

        Thread vThread = Thread.startVirtualThread(printMe);
        vThread.setName("my_VT");

        Thread vThread2 = Thread.ofVirtual().start(printMe);
        vThread2.setName("my_VT2");

        Thread.ofVirtual().name("my_VT3").start(printMe);

        Thread.Builder builder = Thread.ofVirtual().name("my_VT4");
        Thread vThread4 = builder.start(printMe);

        // VTB-1
        Thread.Builder builder2 = Thread.ofVirtual().name("VTB-",1);
        Thread vThreadBuilder1 = builder2.start(printMe);
        vThreadBuilder1.join();
        LOGGER.info(() -> vThreadBuilder1.getName() + " terminated");

        // VTB-2
        Thread vThreadBuilder2 = builder2.start(printMe);
        vThreadBuilder2.join();
        LOGGER.info(() -> vThreadBuilder2.getName() + " terminated");


        // creating an unstarted virtual thread
        Thread.Builder builder3 = Thread.ofVirtual();
        Thread vThread3 = builder3.unstarted(printMe);
        // This time, the thread is not scheduled for execution. It will be scheduled for execution only after we
        //explicitly call the start() method
        vThread3.start();
        boolean isAliveThread3 = vThread3.isAlive();
        LOGGER.info("isAliveThread3: " + isAliveThread3);

        ThreadFactory factory = Thread.ofVirtual().factory();
        factory.newThread(printMe).start();

        ThreadFactory tfVirtual = Thread.ofVirtual().name("vt-",0).factory();
        tfVirtual.newThread(printMe).start();

        SimpleThreadFactory stf = new SimpleThreadFactory();
        stf.newThread(printMe).start();
        LOGGER.info("Ending main");
    }
}
