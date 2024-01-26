package com.ak.reactive.utils.lectures.section01;

import com.ak.reactive.utils.Util;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

/**
 * You can consume the publisher's data from a CompletableFuture as well. A CompletableFuture allows us to write
 * asynchronous non-blocking code. Many such async codes can be combined under a single CompletableFuture. This allows
 * CPU efficiency taking advantage of multi-core features of the processor.
 */
public class Lec07MonoFromFuture {

    // Let's define a completable future as follows:
    private static CompletableFuture<String> getName() {
        return CompletableFuture.supplyAsync(() -> Util.faker().name().fullName());
    }

    // We can now build a Publisher that relies on an asynchronous data producer
    public static void main(String[] args) {
        Mono.fromFuture(getName()).subscribe(Util.onNext());

        // needless to say since the data is being generated in a different thread, we need to pause the main thread
        // to allow the execution of the other thread. Comment the line below and chances are that you wont see any
        // output.
        Util.sleep(1);
    }
}
