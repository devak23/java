package com.ak.reactive.lectures.section03;

import com.ak.reactive.utils.Util;
import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicInteger;

public class Lec06FluxGenerateAssignment {

    public static void main(String[] args) {
        // We are required to emit countries till 'Canada' using Flux.generate()
        Flux.generate(synchronousSink -> {
            String country = Util.faker().country().name();
            synchronousSink.next(country);
            if (country.equalsIgnoreCase("Canada")) {
                synchronousSink.complete();
            }
        }).subscribe(Util.getSubscriber());

        System.out.println("********** SynchronousSink with 10 elements ***********");
        // What if we want to emit max 10 records till Canada shows up or not and also cancel if the subscriber sends
        // a cancel signal? One way to do is to use AtomicInteger
        AtomicInteger counter = new AtomicInteger(0);
        Flux.generate(synchronousSink -> {
            String country = Util.faker().country().name();
            synchronousSink.next(country);
            int count = counter.incrementAndGet();
            if (country.equalsIgnoreCase("Canada") || count >= 10) {
                synchronousSink.complete();
            }
        }).subscribe(Util.getSubscriber());

        // However as you can see the atomicInteger has to be declared outside the Flux.generate() method which means
        // anyone else with access to it can simply call the incrementAndGet() thereby failing our business logic.
        // We shall take a look at it in the next example
    }
}
