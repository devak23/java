package threads;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

import static java.lang.Math.sqrt;
import static java.util.stream.IntStream.range;
import static java.util.stream.IntStream.rangeClosed;

/**
 * Imagine that I have a server application and I would like to use parallel streams. But the application is large and
 * multi-threaded so I want to compartmentalize it. I do not want a slow running task in one module of the application
 * blocking tasks from another module.
 * If I can not use different thread pools for different modules, it means I can not safely use parallel streams in
 * most real world situations.
 * Try the following example. There are some CPU intensive tasks executed in separate threads. The tasks leverage
 * parallel streams. The first task is broken, so each step takes 1 second (simulated by thread sleep). The issue is
 * that other threads get stuck and wait for the broken task to finish. This is contrived example, but imagine a
 * servlet app and someone submitting a long running task to the shared fork join pool.
 */

public class ParallelMain {

    public static void main(String[] args) throws InterruptedException {
        //brokenWay();
        correctWay();
    }

    @SuppressWarnings("unchecked")
    private static void correctWay() {
        // The trick is based on ForkJoinTask.fork which specifies: "Arranges to asynchronously execute this task in the
        // pool the current task is running in, if applicable, or using the ForkJoinPool.commonPool() if not in
        // ForkJoinPool()"
        final int parallelism = Runtime.getRuntime().availableProcessors();
        ForkJoinPool forkJoinPool = null;
        try {
            forkJoinPool = new ForkJoinPool(parallelism);
            final List<Integer> primes = forkJoinPool.submit(() ->
                // Parallel task here. For example:
                IntStream.range(1, 1_000_000)
                        .parallel()
                        .filter(ParallelMain::isPrime)
                        .boxed()
                        .toList()
            ).get();
            System.out.println("Primes: " + primes);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if (forkJoinPool != null) {
                forkJoinPool.shutdown();
            }
        }
    }

    private static void brokenWay() throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(() -> runTask(1000)); // broken task
        executorService.execute(() -> runTask(0));
        executorService.execute(() -> runTask(0));
        executorService.execute(() -> runTask(0));
        executorService.execute(() -> runTask(0));
        executorService.execute(() -> runTask(0));

        executorService.shutdown();
        executorService.awaitTermination(60, TimeUnit.SECONDS);
    }

    private static void runTask(int delay) {
        range(1, 1_000)
                .parallel()
                .filter(ParallelMain::isPrime)
                .peek(i -> sleep(delay))
                .max()
                .ifPresent(max -> System.out.println(Thread.currentThread() + " " + max));
    }

    private static boolean isPrime(long n) {
        return n > 1 && rangeClosed(2, (int)sqrt(n)).noneMatch(divisor -> n % divisor == 0);
    }

    private static void sleep(int delay) {
        try {
            TimeUnit.SECONDS.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
