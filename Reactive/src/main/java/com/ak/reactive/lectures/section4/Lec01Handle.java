package com.ak.reactive.lectures.section4;

import com.ak.reactive.utils.Util;
import reactor.core.publisher.Flux;

// A handle is an operator (decorator) that we can use with our flux.
public class Lec01Handle {

    public static void main(String[] args) {
        // A handle is more like = filter + map

        System.out.println("********** Handle functioning as map ***********");
        // In this case the handle is behaving like a map printing all the numbers
        Flux.range(1, 20).handle(
                (number, ssink) -> ssink.next(number)
        ).subscribe(Util.getSubscriber());

        System.out.println("********** Handle functioning as filter ***********");
        // In this case we can treat it like a filter printing only even numbers
        Flux.range(1, 20).handle(
                (number, ssink) -> {
                    if (number % 2 == 0) {
                        ssink.next(number);
                    }
                }
        ).subscribe(Util.getSubscriber());


        // It can be used to even break the operation like so.
        System.out.println("********** Handle functioning as restrictor ************");
        Flux.range(1, 20)
                .handle(
                        (number, ssink) -> {
                            if (number == 8) {
                                ssink.complete();
                            } else {
                                ssink.next(number);
                            }
                        }
                )
                .subscribe(Util.getSubscriber());

        // Using the same example we did before. We will emit countries till Canada
        System.out.println("********** Handle functioning as restrictor (2nd example) ************");
        Flux.generate(synchronousSink -> synchronousSink.next(Util.faker().country().name()))
                .map(item -> item.toString())
                .handle(
                        (country, ssink) -> {
                            if (country.equalsIgnoreCase("Canada")) {
                                ssink.complete();
                            } else {
                                ssink.next(country);
                            }
                        }
                ).subscribe(Util.getSubscriber());
    }
}
