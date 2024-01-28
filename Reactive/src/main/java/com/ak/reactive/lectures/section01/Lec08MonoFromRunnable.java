package com.ak.reactive.lectures.section01;

import com.ak.reactive.utils.Util;
import reactor.core.publisher.Mono;

/**
 * A Runnable interface doesn't accept any parameter and doesn't return any parameter. Can that be used in any way
 * in this publisher business? Yes. There are many situations (long-running tasks) where you might need a notification of
 * a task completed. A Mono from Runnable becomes useful in those situations
 */
public class Lec08MonoFromRunnable {

    // Let's first create a long running task
    private static Runnable runTimeConsumingOperation() {
        return () -> {
            System.out.println("Time consuming operation started...");
            Util.sleep(5);
            System.out.println("Time consuming operation ended.");
        };
    }

    // Now let's create a Mono that can use this data source and turn into a stream.
    public static void main(String[] args) {
        System.out.println("******** Begin *********");
        Mono.fromRunnable(runTimeConsumingOperation())
                .subscribe(
                        Util.onNext(),
                        Util.onError(),
                        () -> System.out.println("Time consuming operation has completed. Now sending emails...")
                );
        System.out.println("******** End *********");
    }
}
