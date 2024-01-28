package com.ak.reactive.lectures.section02;

import com.ak.reactive.utils.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Lec09FluxFromMono {

    // Many a times, we have a business logic that might accept a Flux but you have a Mono at your end. How do we deal
    // with such a situation?
    public static void main(String[] args) {
        System.out.println("******* Converting a Mono into Flux *********");
        Mono<String> monoStrings = Mono.just("Abhay");
        // Flux provides a `from()` method that you can pass any Publisher (Mono in this case)
        Flux<String> fluxStrings = Flux.from(monoStrings);
        process(fluxStrings);


        System.out.println("******* Converting a Flux into Mono *********");
        // Conversely, how will you convert a Flux to a Mono? Let's create a flux first
        Flux<Integer> numbersFlux = Flux.range(1, 10);
        // Flux has a `next()` which you can use to emit just 1 item
        numbersFlux.next().subscribe(Util.onNext(), Util.onError(), Util.onComplete());

        System.out.println("******* Picking out a specific element *********");
        // But how about finding a particular item rather always finding the first/next one? Simple, use the filter method
        numbersFlux.filter( i-> i > 5 && i % 2 == 0).next().subscribe(Util.onNext(), Util.onError(), Util.onComplete());

        // the `next()` essentially makes Flux behave as a collection of Mono's.

    }

    private static void process(Flux<String> fluxStrings) {
        fluxStrings.subscribe(Util.onNext());
    }
}
