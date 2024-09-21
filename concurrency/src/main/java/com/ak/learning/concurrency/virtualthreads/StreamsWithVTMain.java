package com.ak.learning.concurrency.virtualthreads;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class StreamsWithVTMain {
    private static final Logger LOGGER = Logger.getLogger(StreamsWithVTMain.class.getName());

    public static void main(String[] args) {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%4$s] %5$s %n");

        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Future<String>> futures = IntStream.range(0, 10)
                    .mapToObj(i -> executorService.submit(() -> Thread.currentThread() + "(" + i + ")"))
                    .toList();

            futures.forEach(StreamsWithVTMain::accept);


            // For instance, the following stream pipeline returns a List of results (it filters all Future instances
            // that haven’t completed successfully)
            List<String> results = process1(executorService);
            LOGGER.info("results = " + results);

            // ofcourse the same thing can be written as
            List<String> results2 = process2(executorService);
            LOGGER.info("results2 = " + results2);

            // On the other hand, you may need to collect all the Future that has been completed exceptionally. This can
            // be achieved via exceptionNow(), as follows
            List<Throwable> exceptions = process3(executorService);
            LOGGER.info("exceptions = " + exceptions);
        }
    }

    private static List<Throwable> process3(ExecutorService executorService) {
        try {
            return executorService.invokeAll(
                    List.of(() -> "pass01", () -> "pass02".substring(50), () -> "pass03"))
                    .stream()
                    .filter(f -> f.state() == Future.State.FAILED)
                    .<Throwable>mapMulti( (f,c) -> c.accept(f.exceptionNow())).toList();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<String> process2(ExecutorService executorService) {
        try {
            return executorService.invokeAll(
                    List.of(() -> "pass01", () -> "pass02", () -> "pass03", () -> "pass04" ))
                    .stream()
                    .filter(f -> f.state() == Future.State.SUCCESS)
                    .map(f -> f.resultNow().toString())
                    .toList();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    private static List<String> process1(ExecutorService executorService) {
        try {
            return executorService.invokeAll(
                    List.of(() -> "pass01", () -> "pass02", () -> "pass03", () -> "pass04" ))
                    .stream()
                    .filter(f -> f.state() == Future.State.SUCCESS)
                    .<String>mapMulti((future, consumer) -> consumer.accept((String) future.resultNow()))
                    .toList();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void accept(Future<String> f) {
        try {
            LOGGER.info(f.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}