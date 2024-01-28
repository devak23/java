package com.ak.reactive.utils.lectures.section02;

import com.ak.reactive.utils.Util;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.IntStream;

/**
 * Flux can be created from Arrays and from ArrayList.
 */
public class Lec03FluxFromArraysOrList {

    public static void main(String[] args) {
        System.out.println("*********** Flux of Names ******************");
        // Let's create a handy way for arbitrary 10 names from Faker.
        List<String> names = IntStream.range(0, 10).mapToObj(i -> Util.faker().name().fullName()).toList();

        // We can now create a Flux from the data above like so:
        Flux<String> nameFlux = Flux.fromIterable(names);

        // consumption pattern doesn't change.
        nameFlux.subscribe(Util.onNext(), Util.onError(), Util.onComplete());

        System.out.println("*********** Flux of Numbers ******************");
        // We can also use Integer array for our data
        Integer[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        // Once again, a flux of numbers...
        Flux.fromArray(numbers).subscribe(Util.onNext(), Util.onError(), Util.onComplete());
    }
}
