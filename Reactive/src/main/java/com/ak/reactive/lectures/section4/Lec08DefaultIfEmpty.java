package com.ak.reactive.lectures.section4;

import com.ak.reactive.utils.Util;
import reactor.core.publisher.Flux;

public class Lec08DefaultIfEmpty {

    public static void main(String[] args) {

        getNonMatchingOrderNumbers()
                .filter(i -> i > 10)
                .defaultIfEmpty(-100)
                .subscribe(Util.getSubscriber());


        getSomeMatchingOderNumbers()
                .filter(i -> i > 10)
                .defaultIfEmpty(-100)
                .subscribe(Util.getSubscriber());

    }

    private static Flux<Integer> getNonMatchingOrderNumbers() {
        return Flux.range(1, 10);
    }

    private static Flux<Integer> getSomeMatchingOderNumbers() {
        return Flux.range(1, 12);
    }
}
