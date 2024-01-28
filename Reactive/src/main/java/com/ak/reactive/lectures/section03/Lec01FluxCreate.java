package com.ak.reactive.lectures.section03;

import com.ak.reactive.utils.Util;
import reactor.core.publisher.Flux;

/**
 * Here is another way to create a flux. Using a FluxSink
 */
public class Lec01FluxCreate {

    public static void main(String[] args) {
        // FluxSink lets us emit elements using multiple threads if required.
        System.out.println("***** Using FluxSink ******");
        Flux.create(fluxSink -> {
            fluxSink.next(1);
            fluxSink.next(2);
            fluxSink.complete();
        }).subscribe(Util.getSubscriber());

        System.out.println("***** Emitting a fixed set of names ******");
        //What if we want to emit a fixed set of data.
        Flux.create(fluxSink -> {
            for (int i=0; i<10; i++) {
                fluxSink.next(Util.faker().name().fullName());
            }
            fluxSink.complete();
        }).subscribe(Util.getSubscriber("NameFlux"));

        System.out.println("***** Emitting Countries till Canada ******");
        // What if we wanted to emit all countries till Canada?
        Flux.create(fluxSink -> {
            String country;
            do {
                country = Util.faker().country().name();
                fluxSink.next(country);
            } while (!country.equalsIgnoreCase("Canada"));
            fluxSink.complete();
        }).subscribe(Util.getSubscriber("CountryFlux"));
    }
}
