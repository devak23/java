package com.ak.learning.concurrency.virtualthreads;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.*;
import java.util.logging.Logger;

public class InvokeAllMain {
    private static final Logger LOGGER = Logger.getLogger(InvokeAllMain.class.getName());

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "[%1$tT] [%4$-7s] %5$s %n");

        // Using invokeAll() with virtual threads via newVirtualThreadPerTaskExecutor() (or with
        // newThreadPerTaskExecutor()) is straightforward. For instance, here we have a simple example of
        // executing three Callable instances:
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Future<String>> futures = executor.invokeAll(
                    List.of(() -> "pass01", () -> "pass02", () -> "pass03")
            );

            futures.forEach(f -> LOGGER.info(() -> "state: " + f.state()));
        }

        // [20:42:15] [INFO   ] state: SUCCESS
        // [20:42:15] [INFO   ] state: SUCCESS
        // [20:42:15] [INFO   ] state: SUCCESS

        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            String result = executor.invokeAny(
                    List.of(() -> "pass01", () -> "pass02", () -> "pass03")
            );
            LOGGER.info("result: " + result);
        }

        // [20:46:57] [INFO   ] result: pass01

        TestingTeam testingTeam = buildTestingTeamWithInvokeAll();
        LOGGER.info("testingTeam: " + testingTeam);

        TestingTeam testingTeam1 = buildTestingTeamWithInvokeAny();
        LOGGER.info("testingTeam: " + testingTeam);
    }

    public static TestingTeam buildTestingTeamWithInvokeAll() throws InterruptedException {
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Future<String>> futures = executor.invokeAll(
                List.of(() -> fetchTester(1), () -> fetchTester(2), () -> fetchTester(3))
            );

            futures.forEach(f -> LOGGER.info(() -> "state: " + f.state()));
            return new TestingTeam(
                    futures.get(0).resultNow()
                    , futures.get(1).resultNow()
                    , futures.get(2).resultNow()
            );
        }
    }

    public static TestingTeam buildTestingTeamWithInvokeAny() throws InterruptedException, ExecutionException {
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            String result = executor.invokeAny(
                List.of(() -> fetchTester(1), () -> fetchTester(2), () -> fetchTester(3))
            );

            return new TestingTeam(result);
        }
    }

    public static String fetchTester(int id) throws InterruptedException, IOException {
        HttpClient httpClient = HttpClient.newHttpClient();

        // intentionally add a delay of 1-5 seconds
        LOGGER.info("sleeping arbitrarily: " + Thread.currentThread());
        TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextLong(5));

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://reqres.in/api/users/" + id))
                .build();

        HttpResponse<String> responseGet = httpClient.send(request,HttpResponse.BodyHandlers.ofString());

        if (responseGet.statusCode() == 200) {
            return responseGet.body();
        }

        throw new UserNotFoundException("Code: " + responseGet.statusCode());
    }
}
