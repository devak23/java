package com.ak.reactive.lectures.section03;

import com.ak.reactive.utils.Util;
import reactor.core.publisher.Flux;

public class Lec05FluxGenerate {

    public static void main(String[] args) {
        // With fluxSink, one instance is provided and you take responsibility of looping through the data yourself.
        // Similar to fluxSink here we have the `generate` function which provides us with synchronous fluxSink which
        // takes care of looping through the data elements. Running the program below demonstrates that effect.
        // Uncomment to see it happen.

/*
        Flux.generate(synchronousSink -> {
            synchronousSink.next(Util.faker().company().name());
            Util.sleep(1);
        }).subscribe(Util.getSubscriber());
*/

        System.out.println("********* SynchronousFluxSink with take operator ***********");
        // In order to terminate the loop, we can use the `take` operator
        Flux.generate(synchronousSink -> {
            synchronousSink.next(Util.faker().company().buzzword());
        }).take(10).subscribe(Util.getSubscriber());
        // AS you can see the take() sends the cancellation signal which is handled by the publisher well.

        System.out.println("********* SynchronousFluxSink with complete signal ***********");
        // If you invoke the complete method from within the block, you can end the infinite loop
        Flux.generate(synchronousSink -> {
            synchronousSink.next(Util.faker().cat().name());
            synchronousSink.complete();
        }).take(5).subscribe(Util.getSubscriber());

        System.out.println("********* Two SynchronousFluxSink ***********");
        // Remember though, just like fluxSink, you are allowed only 1 instance of synchronousFluxSink
        Flux.generate(synFluxSink -> {
           synFluxSink.next(Util.faker().hobbit().character());
           synFluxSink.next(Util.faker().hobbit().character());
        }).subscribe(Util.getSubscriber());
    }
}
