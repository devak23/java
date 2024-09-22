package com.ak.learning.concurrency.virtualthreads;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.StructuredTaskScope;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static com.ak.learning.concurrency.virtualthreads.InvokeAllMain.fetchTester;

public class StructuredTaskWithStreamMain {
    private static final Logger LOGGER = Logger.getLogger(StructuredTaskWithStreamMain.class.getName());

    public static void main(String[] args) {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tT] [%4$-7s] %5$s %n");

        TestingTeam team = buildTestingTeam();
        LOGGER.info(STR."Testing team: \{team}");
    }

    private static TestingTeam buildTestingTeam() {
        try(StructuredTaskScope<String> scope = new StructuredTaskScope<>()) {
            List<StructuredTaskScope.Subtask<String>> subtasks = Stream.of(1, 2, 3)
                    .<Callable<String>>map(id -> () -> fetchTester(id))
                    .map(scope::fork)
                    .toList();

            scope.join();

            subtasks.stream()
                    .filter(f -> f.state() == StructuredTaskScope.Subtask.State.FAILED)
                    .map()

            List<String> results = subtasks.stream()
                    .filter(f -> f.state() == StructuredTaskScope.Subtask.State.SUCCESS)
                    .map(StructuredTaskScope.Subtask::get)
                    .toList();

            return new TestingTeam(results.toArray(new String[1]));

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
