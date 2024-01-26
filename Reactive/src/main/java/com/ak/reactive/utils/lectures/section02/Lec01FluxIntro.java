package com.ak.reactive.utils.lectures.section02;

import com.ak.reactive.utils.Util;
import reactor.core.publisher.Flux;

/**
 * So Mono creates 0 or 1 element. What if we had more than one element? Enter Flux.
 */
public class Lec01FluxIntro {

    public static void main(String[] args) {
        // This is how we create a "Flux of objects". Again, this is a publisher of data just similar to Java stream
        // that we have seen in the traditional Java development

        Flux<Integer> integers = Flux.just(1, 2 ,3 ,4);

        integers.subscribe(Util.onNext());
    }
}
