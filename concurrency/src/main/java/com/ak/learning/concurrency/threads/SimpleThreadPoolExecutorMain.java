package com.ak.learning.concurrency.threads;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class SimpleThreadPoolExecutorMain {
    private static final Logger LOGGER = Logger.getLogger(SimpleThreadPoolExecutorMain.class.getName());

    // The main() method fires 50 instances of Runnable. Each Runnable sleeps for two seconds and prints a message. The work queue is limited to
    // five instances of Runnable—the core threads to 10, the maximum number of threads to 20, and the idle timeout to one second

    public static void main(String[] args) {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tT] [%4$-7s] %5$s %n");

        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
        final AtomicInteger counter = new AtomicInteger();

        ThreadFactory threadFactory = (Runnable r ) -> {
            LOGGER.info("Creating a cool-thread-" + counter.incrementAndGet());
            return new Thread(r, "Cool-Thread-" + counter.get());
        };

        RejectedExecutionHandler rejectedHandler = (r, executor) -> {
          if (r instanceof SimpleThreadPoolExecutor task) {
              LOGGER.info("Rejecting task: " + task.getTaskId());
          }
        };

        try (ThreadPoolExecutor executor =
                     new ThreadPoolExecutor(10, 20, 1, TimeUnit.SECONDS, workQueue, threadFactory, rejectedHandler)){
            executor.allowCoreThreadTimeOut(true);

            IntStream.rangeClosed(1, 50).forEach(i -> executor.execute(new SimpleThreadPoolExecutor(i)));

            executor.shutdown();
            executor.awaitTermination(Integer.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // The following formula helps us to determine the optimal size of the pool:
    // Number of threads = Number of CPUs * Target CPU utilization * (1 + W/C)
    // where W = Wait Time
    // and C = Compute Time
    // As a rule of thumb, for compute-intensive tasks (usually small tasks), it can be a good idea to benchmark the thread pool
    // with the number of threads equal with to number of processors or number of processors + 1 (to prevent potential pauses).
    // For time-consuming and blocking tasks (for example, I/O), a larger pool is better since threads will not be available for
    // scheduling at a high rate. Also, pay attention to interferences with other pools (for example, database connections pools,
    // and socket connection pools).
}
