package com.ak.reactive.utils.lectures;

import reactor.core.publisher.Mono;

public class Lec02Mono {

    public static void main(String[] args) {
        // This is a publisher holding data
        Mono<Integer> integerMono = Mono.just(1);

        // If you print this publisher, again it wont do anything.
        System.out.println(integerMono);

        // In order to get it to print, we need to emit the element from the publisher. We therefore have to
        // "subscribe" to the publisher to get it to do something
        integerMono.subscribe(e -> System.out.println("Received: " + e)); // This will print 1 as expected.

        // So what happened? - The consumer code tells the publisher to publish the data which the consumer receives and
        // prints on the console.
    }
}
