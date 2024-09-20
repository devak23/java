package com.ak.learning.concurrency.virtualthreads;

import java.util.logging.Logger;

import static com.ak.learning.concurrency.Utils.sleep;

public class ContextSwitchingMain3 {
    private static final Logger LOGGER = Logger.getLogger(ContextSwitchingMain3.class.getName());
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "[%1$tT] [%4$-7s] %5$s %n");
        System.setProperty("jdk.virtualThreadScheduler.maxPoolSize", "1");
        System.setProperty("jdk.virtualThreadScheduler.maxPoolSize", "1");
        System.setProperty("jdk.virtualThreadScheduler.maxPoolSize", "1");

        Runnable slowTask = () -> {
            LOGGER.info(() -> Thread.currentThread() + " | working on a problem");
            LOGGER.info(() -> Thread.currentThread() + " | break time (non-blocking)");
            while (dummyTrue()) {} // non-blocking
            LOGGER.info(() -> Thread.currentThread() + " | work done");
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

    static boolean dummyTrue() {
        return true;
    }
}
// OUTPUT:
// [08:36:03] [INFO   ] VirtualThread[#22,slow-0]/runnable@ForkJoinPool-1-worker-1 | working on a problem
// [08:36:03] [INFO   ] VirtualThread[#22,slow-0]/runnable@ForkJoinPool-1-worker-1 | break time (non-blocking)
// (execution hangs)

// The execution hangs on because the infinite loop is not seen as a blocking operation. In other words, the virtual
// thread of the slow task (#22) is never unmounted. Since there is a single worker, the JVM cannot push for the
// execution of the fast task. If we increase the parallelism from 1 to 2, then the fast task will be successfully
// executed by worker-2, while worker-1 (executing the slow task) will simply hang on to a partial execution. We can
// avoid such situations by relying on a timeout join, such as join(Duration duration). This way, after the given
// timeout, the slow task will be automatically interrupted. So pay attention to such scenarios.
