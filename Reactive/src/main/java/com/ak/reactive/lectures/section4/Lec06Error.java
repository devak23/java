package com.ak.reactive.lectures.section4;

import com.ak.reactive.utils.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Lec06Error {

    public static void main(String[] args) {

        System.out.println("********** onErrorReturn **************");
        Flux.range(1, 10)
                .log()
                .map(i -> 10 / (5 - i))
                .onErrorReturn(-1)
                .subscribe(Util.getSubscriber());

        System.out.println("********** onErrorResume **************");
        Flux.range(1, 10)
                .log()
                .map(i -> 10 / (5 - i))
                .onErrorResume(e -> fallback())
                .subscribe(Util.getSubscriber());

        System.out.println("********** onErrorResume **************");
        Flux.range(1, 10)
                .log()
                .map(i -> 10 / (5 - i))
                .onErrorContinue((e, obj) -> {
                    System.out.println("Continuing..Error caused on: " + obj);
                })
                .subscribe(Util.getSubscriber());

    }


    private static Mono<Integer> fallback() {
        return Mono.fromSupplier(() -> Util.faker().random().nextInt(100, 200));
    }
}
