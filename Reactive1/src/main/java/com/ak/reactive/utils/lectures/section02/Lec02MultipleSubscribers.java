package com.ak.reactive.utils.lectures.section02;

import reactor.core.publisher.Flux;

public class Lec02MultipleSubscribers {
    public static void main(String[] args) {
        Flux<Integer> integers = Flux.just(1, 2, 3 ,4 ,5, 6);

        integers.subscribe(i -> System.out.println("Sub1: " + i));

        // you could technically have another subscriber listening to the same thing like so...
        integers.subscribe(i -> System.out.println("Sub2: " + i));

        // Let's say someone is interested in even numbers alone and somebody is interested in odd numbers
        Flux<Integer> evenNumbersFlux = integers.filter(i -> i% 2 == 0);
        Flux <Integer> oddNumbesFlux = integers.filter(i -> i % 2 == 1);

        // and now the subscribers of 2 different publishers will receive diffent data
        evenNumbersFlux.subscribe(i -> System.out.println("Even Flux: " + i));
        oddNumbesFlux.subscribe( i -> System.out.println("Odd Flux: " + i));
    }
}
