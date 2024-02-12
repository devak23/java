package com.ak.reactive.lectures.section06;

import com.ak.reactive.utils.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Lec07FluxInterval {

    public static void main(String[] args) {

        Flux.interval(Duration.ofSeconds(1))
                .subscribe(Util.getSubscriber());
    }

    // Flux.interval internally uses Schedulers.parallel()

    // Scheduler Methods and its Purposes
    // ==================================
    // boundedElastic: For Network intensive operations
    // parallel: For CPU intensive tasks
    // single: For a single dedicated thead for one-off tasks
    // immediate: For executing in the current thread.

    // Operators for Scheduling
    // ===========================
    // subscribeOn: for upstream
    // publishOn: for downstream
}
