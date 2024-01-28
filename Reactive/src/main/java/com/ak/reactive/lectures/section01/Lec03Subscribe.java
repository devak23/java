package com.ak.reactive.lectures.section01;

import com.ak.reactive.utils.Util;
import reactor.core.publisher.Mono;

/**
 * More on subscribe method...
 */
public class Lec03Subscribe {

    public static void main(String[] args) {

        // Let's create a publisher of String data
        Mono<String> name = Mono.just("Abhay");

        // To consume this, we can use the subscribe method which accepts 3 callbacks
        name.subscribe(
                item -> System.out.println(item),
                err -> System.out.println(err),
                () -> System.out.println("Completed consumption")
        );

        // Let's create another publisher that will throw an exception
        Mono<Integer> exceptMono = Mono.just("India")
                .map(String::length)
                .map(length -> length / 0);

        // So we create a publisher who takes a string, finds its length and divides it by a zero, thereby simulating
        // an exception

        exceptMono.subscribe(
                item -> System.out.println("Received: " + item),
                err -> System.out.println("ERROR: " + err.getMessage()),
                () -> System.out.println("Done")
        );

        // So you can see that the error function is triggered.

        // Let's move these implementations to a util class. and rewrite the above code.
        System.out.println("*********** After moving the implementation util *************");
        name.subscribe(Util.onNext(), Util.onError(), Util.onComplete());

        System.out.println("************* ExceptMono with Util *****************");
        exceptMono.subscribe(Util.onNext(), Util.onError(), Util.onComplete());
    }
}
