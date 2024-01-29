package com.ak.reactive.lectures.section03;

import com.ak.reactive.utils.Util;
import reactor.core.publisher.Flux;

public class Lec07FluxGenerateCounter {

    public static void main(String[] args) {
        // So we use the concept of state (based on the parameters). The first one is a Callable of StateSupplier where
        // we set the value as 1. We then pass another bi-function which accepts 2 params (counter/state and sink)
        // We leverage the counter/state variable, increment it and return it back the subscriber which will then cause
        // more items to be emitted. We will do this for 10 times and then finally exit.
        Flux.generate(
                () -> 1,
                (counter, sink) -> {
                    String country = Util.faker().country().name();
                    sink.next(country);
                    if (counter >= 10 || country.equalsIgnoreCase("Canada")) {
                        sink.complete();
                    }
                    return counter + 1;
                }
        ).subscribe(Util.getSubscriber());

        // If the subscriber cancels it say by taking 4 elements, the following code will still emit and consume 4 elements
        Flux.generate(
                () -> 1,
                (counter, sink) -> {
                    String country = Util.faker().cat().name();
                    sink.next(country);
                    if (counter >= 10 || country.equalsIgnoreCase("Canada")) {
                        sink.complete();
                    }
                    return counter + 1;
                }
        ).take(5).subscribe(Util.getSubscriber());
    }
}
