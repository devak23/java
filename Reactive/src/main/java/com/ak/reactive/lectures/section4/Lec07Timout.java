package com.ak.reactive.lectures.section4;

import com.ak.reactive.utils.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Lec07Timout {

    public static void main(String[] args) {
        getOrderNumbers()
                .timeout(Duration.ofSeconds(2), fallback())
                .subscribe(Util.getSubscriber());

        Util.sleep(60);
    }

    private static Flux<Integer> getOrderNumbers() {
        return Flux.range(1, 10)
                .delayElements(Duration.ofSeconds(5));
    }

    private static Flux<Integer> fallback() {
        return Flux.range(100, 10)
                .delayElements(Duration.ofMillis(200));
    }
}
