package com.ak.reactive.lectures.section4;

import com.ak.reactive.utils.Util;
import reactor.core.publisher.Flux;

public class Lec09SwitchIfEmpty {

    public static void main(String[] args) {

            getOrderNumbes()
                    .filter( i -> i > 10)
                    .switchIfEmpty(fallback())
                    .subscribe(Util.getSubscriber());
    }

    // this could be a redis cache
    private static Flux<Integer> getOrderNumbes() {
        System.out.println("From getOrderNumbers");
        return Flux.range(1, 10);
    }

    // the actual call to db
    private static Flux<Integer> fallback() {
        System.out.println("From fallback");
        return Flux.range(20, 5);
    }
}
