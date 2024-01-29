package com.ak.reactive.lectures.section03;

import com.ak.reactive.utils.Util;
import reactor.core.publisher.Flux;

public class Lec03FluxTakeOperator {
    // What is an operator?
    // These are simple decorators like map, filter which will help us to decorate our Mono or pipeline

    public static void main(String[] args) {
        Flux.range(1, 10).subscribe(Util.getSubscriber());

        // take is an operator as such.
        Flux.range(1, 10)
                .take(3)
                .subscribe(Util.getSubscriber());

        // after the 3rd item is taken, the subscription is canceled and complete signal is sent to downstream component
        // Upon receiving the cancel signal (by upstream), the publishing of events are canceled which means that the
        // publisher will not emit anymore events.

        // Why are we talking about all this? Because in Lec02FluxCreate we have introduced an issue that we need to fix.
        // Let's talk about the issue and its fix in the next example @see Lec04FluxCreateIssueFix
    }
}
