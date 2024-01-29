package com.ak.reactive.lectures.section03;

import com.ak.reactive.utils.Util;
import reactor.core.publisher.Flux;

public class Lec04FluxCreateIssueFix {

    public static void main(String[] args) {
        // So what was the issue with Lec02FluxCreate? This is the definition that we had defined with a slight modification
        // that we are now printing what is getting emitted and the client is interested in taking onl 3 items from the
        // entire list.

        System.out.println("************ Before the Fix *****************");
        Flux.create(fluxSink -> {
                    String country;
                    do {
                        country = Util.faker().country().name();
                        System.out.println("Emitted: " + country);
                        fluxSink.next(country);
                    } while (!country.equalsIgnoreCase("Canada"));
                })
                .take(3)
                .subscribe(Util.getSubscriber());

        // If you run the above program you will notice that regardless of client sending a "cancel signal" to the
        // publisher, you still get to see a boat load of emissions till Canada pops up.


        // So we are going to fix this by telling the fluxSink to check if the subscriber is sending us a cancel signal.
        // Rewriting the above code we should fix it as such...
        System.out.println("************ After the Fix *****************");
        Flux.create(fluxSink -> {
                    String country;
                    do {
                        country = Util.faker().country().name();
                        System.out.println("Emitted: " + country);
                        fluxSink.next(country);
                    } while (!country.equalsIgnoreCase("Canada") && !fluxSink.isCancelled());
                })
                .take(3)
                .subscribe(Util.getSubscriber());

        // So with that extra check !fluxSink.isCancelled(), we are able to stop the publisher from emitting more data
    }
}
