package com.ak.reactive.lectures.section02.assignment;

import com.ak.reactive.utils.Util;
import reactor.core.publisher.Flux;

/**
 * We are required to generate 10 names using "Flux range api"
 */
public class GenerateNamesUsingFlux {

    // This is a simple exercise where we can use range and connect the "map operator" to define what operation needs
    // to be performed for each index.
    public static void main(String[] args) {
        Flux.range(1,10)
                .map(i -> Util.faker().name().fullName())
                .subscribe(Util.onNext());
    }
}
