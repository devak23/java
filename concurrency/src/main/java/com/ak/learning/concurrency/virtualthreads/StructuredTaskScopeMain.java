package com.ak.learning.concurrency.virtualthreads;

import java.util.concurrent.StructuredTaskScope;
import java.util.logging.Logger;

import static com.ak.learning.concurrency.virtualthreads.InvokeAllMain.fetchTester;

public class StructuredTaskScopeMain {
    private static final Logger LOGGER = Logger.getLogger(StructuredTaskScopeMain.class.getName());

    public static void main(String[] args) {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tT] [%4$-7s] %5$s %n");

        TestingTeam team = buildTestingTeam();
        LOGGER.info(STR."Team: \{team}");
    }

    private static TestingTeam buildTestingTeam() {
        try (StructuredTaskScope scope = new StructuredTaskScope<String>()) {
            StructuredTaskScope.Subtask<String> subtask = scope.fork(() -> fetchTester(1));

            LOGGER.info(() -> "Waiting for " + subtask + " to finish");
            scope.join();

            return new TestingTeam(subtask.get());

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
