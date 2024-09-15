package com.ak.learning.concurrency.virtualthreads;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class ExecutorsMain {
    private static final Logger LOGGER = Logger.getLogger(ExecutorsMain.class.getSimpleName());
    public static final int NUMBER_OF_TASKS = 15;
    public static void main(String[] args) {
        Runnable taskr = () -> LOGGER.info("Runnable: " + Thread.currentThread());

        Callable<Boolean> taskc = () -> {
            LOGGER.info("Callable: " + Thread.currentThread());
            return true;
        };

        // submitting 15 tasks
        try(ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < NUMBER_OF_TASKS; i++) {
                executor.submit(taskr);
                executor.submit(taskc);
            }
        }
    }
}
