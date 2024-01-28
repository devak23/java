package com.ak.reactive.lectures.section01;

import reactor.core.publisher.Mono;

/**
 * So what's a Mono? - Mono is an event publisher that publishes either 0 or 1 element just like the stream of data we
 * saw in the previous example. In other words it produces 0 or 1 element. So it's a producer which can stream the data.
 * Later we will come across "Flux" which produces a stream data (List of numbers, strings, objects etc.)
 */

public class Lec02Mono {

    public static void main(String[] args) {
        // So here we have a publisher holding data of integer type. If you know the data beforehand, you can use the
        //"just" method of the mono
        Mono<Integer> integerMono = Mono.just(1);

        // If you print this publisher, again it won't do anything.
        System.out.println(integerMono);

        // In order to get it to print, we need to emit the element from the publisher. We therefore have to
        // "subscribe" to the publisher to get it to do something. The "subscribe" will take the elements one by one
        // just as a stream would do and start processing it.

        integerMono.subscribe(e -> System.out.println("Received: " + e)); // This will print 1 as expected.

        // So what happened? - The consumer code tells the publisher (via subscribe) to publish the data which the
        // consumer receives and prints on the console.
    }
}
