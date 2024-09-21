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
            try {
                List<String> results = executorService.invokeAll(
                        List.of(() -> "pass01", () -> "pass02", () -> "pass03", () -> "pass04" ))
                        .stream()
                        .filter(f -> f.state() == Future.State.SUCCESS)
                        .<String>mapMulti((future, consumer) -> consumer.accept((String) future.resultNow()))
                        .toList();

                LOGGER.info("results = " + results);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
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