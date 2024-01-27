package com.ak.reactive.utils.lectures.section02;

import com.ak.reactive.utils.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Lec09FluxFromMono {

    // Many a times, we have a business logic that might accept a Flux but you have a Mono at your end. How do we deal
    // with such a situation?
    public static void main(String[] args) {
        Mono<String> monoStrings = Mono.just("Abhay");
        Flux<String> fluxStrings = Flux.from(monoStrings);
        process(fluxStrings);
    }

    private static void process(Flux<String> fluxStrings) {
        fluxStrings.subscribe(Util.onNext());
    }
}
