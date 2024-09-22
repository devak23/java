package com.ak.learning.concurrency.virtualthreads;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.StructuredTaskScope;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static com.ak.learning.concurrency.virtualthreads.InvokeAllMain.fetchTester;

public class StructuredTaskScopeWithStreamsMain {
    private static Logger LOGGER = Logger.getLogger(StructuredTaskScopeWithStreamsMain.class.getName());

    public static void main(String[] args) {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tT] [%4$-7s] %5$s %n");

        TestingTeam team = buildTestingTeam();
        LOGGER.info(STR."Team = \{team}");
    }

    private static TestingTeam buildTestingTeam() {
        try (StructuredTaskScope.ShutdownOnSuccess<String> scope = new StructuredTaskScope.ShutdownOnSuccess<>()) {
            List<StructuredTaskScope.Subtask<String>> subtasks = Stream.of(1, 2, 3)
                    .<Callable<String>>map(id -> () -> fetchTester(id))
                    .map(scope::fork)
                    .toList();

            scope.join();

            List<Throwable> failed = subtasks
                    .stream()
                    .filter(f -> f.state() == StructuredTaskScope.Subtask.State.FAILED)
                    .map(StructuredTaskScope.Subtask::exception)
                    .toList();

            LOGGER.info("failed: " + failed);


            List<String> succeeded = subtasks
                    .stream()
                    .filter(f -> f.state() == StructuredTaskScope.Subtask.State.SUCCESS)
                    .map(StructuredTaskScope.Subtask::get)
                    .toList();

            return new TestingTeam(succeeded.toArray(new String[1]));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
