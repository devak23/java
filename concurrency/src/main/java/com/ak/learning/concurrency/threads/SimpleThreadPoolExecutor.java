package com.ak.learning.concurrency.threads;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Getter
public class SimpleThreadPoolExecutor implements Runnable {
    private final int taskId;

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("Executing task: {}, via thread: {}", taskId, Thread.currentThread().getName());
    }
}
