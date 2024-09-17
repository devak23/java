package com.ak.learning.concurrency;

import java.util.concurrent.TimeUnit;

public final class Utils {
    private Utils() {}

    public static void sleep(int timeout) {
        try {
            TimeUnit.SECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
