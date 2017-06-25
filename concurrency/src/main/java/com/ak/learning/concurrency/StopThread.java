package com.ak.learning.concurrency;

import java.util.concurrent.TimeUnit;

/**
 * This program never stops even when the stopRequested is a boolean which can be set to
 * true in one atomic instruction. The reason is with no synchronization, there is no
 * guarantee, when (if ever) the background thread will see the change made to the
 * stopRequested variable.
 */
public class StopThread {
    private static boolean stopRequested = false;

    public static void main(String[] args) throws InterruptedException {
        Thread backgroundThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (!stopRequested) {
                    i++;
                }
                /* In the absence of synchronization, the compiler may simply optimize the following
                 * code to
                 * if (!stopRequested) {
                 *      while(true) i++
                 * }
                 * This is called hoisting and is what HotSpot VM does. The result is "liveness failure"
                 * Meaning, the program fails to make any progress.
                 */
            }
        });
        backgroundThread.start();
        TimeUnit.SECONDS.sleep(1);
        stopRequested = true;
    }
}
