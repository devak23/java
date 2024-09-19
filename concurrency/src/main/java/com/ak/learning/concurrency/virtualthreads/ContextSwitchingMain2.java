package com.ak.learning.concurrency.virtualthreads;

import java.util.logging.Logger;

import static com.ak.learning.concurrency.Utils.sleep;

public class ContextSwitchingMain2 {
    private static final Logger LOGGER = Logger.getLogger(ContextSwitchingMain2.class.getName());

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "[%1$tT] [%4$-7s] %5$s %n");
        System.setProperty("jdk.virtualThreadScheduler.maxPoolSize", "1");
        System.setProperty("jdk.virtualThreadScheduler.maxPoolSize", "1");
        System.setProperty("jdk.virtualThreadScheduler.maxPoolSize", "1");

        Runnable slowTask = () -> {
            LOGGER.info(() -> Thread.currentThread() + " | working on something");
            LOGGER.info(() -> Thread.currentThread() + " | break time (blocking)");
            sleep(5);
            LOGGER.info(() -> Thread.currentThread() + " | work completed");
        };

        Runnable fastTask = () -> {
            LOGGER.info(() -> Thread.currentThread() + " | working on something");
            LOGGER.info(() -> Thread.currentThread() + " | break time (blocking)");
            sleep(1);
            LOGGER.info(() -> Thread.currentThread() + " | work completed");
        };

        Thread st = Thread.ofVirtual().name("slow-", 0).start(slowTask);
        Thread ft = Thread.ofVirtual().name("fast-", 0).start(fastTask);

        st.join();
        ft.join();
    }
}


// OUTPUT:
// [08:36:23] [INFO   ] VirtualThread[#22,slow-0]/runnable@ForkJoinPool-1-worker-1 | working on something
// [08:36:23] [INFO   ] VirtualThread[#22,slow-0]/runnable@ForkJoinPool-1-worker-1 | break time (blocking)
// [08:36:23] [INFO   ] VirtualThread[#24,fast-0]/runnable@ForkJoinPool-1-worker-1 | working on something
// [08:36:23] [INFO   ] VirtualThread[#24,fast-0]/runnable@ForkJoinPool-1-worker-1 | break time (blocking)
// [08:36:24] [INFO   ] VirtualThread[#24,fast-0]/runnable@ForkJoinPool-1-worker-1 | work completed
// [08:36:28] [INFO   ] VirtualThread[#22,slow-0]/runnable@ForkJoinPool-1-worker-1 | work completed

// we can see that the execution starts the slow task. The fast task cannot be executed, since worker-1 (the only
// available worker) is busy executing the slow task:
// [08:36:23] [INFO   ] VirtualThread[#22,slow-0]/runnable@ForkJoinPool-1-worker-1 | working on something

// Worker-1 executes the slow task until this task hits the sleeping operation. Since this is a blocking operation, the
// corresponding virtual thread (#22) is unmounted from worker-1:
// [08:36:23] [INFO   ] VirtualThread[#22,slow-0]/runnable@ForkJoinPool-1-worker-1 | break time (blocking)

// The JVM takes advantage of the fact that worker-1 is available and pushes for the execution of the fast task:
// [08:36:23] [INFO   ] VirtualThread[#24,fast-0]/runnable@ForkJoinPool-1-worker-1 | working on something

// The fast task also hits a sleeping operation, and its virtual thread (#24) is unmounted:
// [08:36:23] [INFO   ] VirtualThread[#24,fast-0]/runnable@ForkJoinPool-1-worker-1 | break time (blocking)

// However, the fast task sleeps for only 1 second, so its blocking operation is over before the slow task blocking
// operation, which is still sleeping. So the JVM can schedule the fast task for execution again, and worker-1 is ready
// to accept it:
// [08:36:24] [INFO   ] VirtualThread[#24,fast-0]/runnable@ForkJoinPool-1-worker-1 | work completed

// At this moment, the fast task is done, and worker-1 is free. But the slow task is still sleeping. After
// these 5 seconds, the JVM schedules the slow task for execution, and worker-1 is there to take it.
// [08:36:28] [INFO   ] VirtualThread[#22,slow-0]/runnable@ForkJoinPool-1-worker-1 | work completed

// Done!