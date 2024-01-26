package com.ak.reactive.utils.lectures.section01;

import com.ak.reactive.utils.Util;
import reactor.core.publisher.Mono;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

/**
 * You can create a publisher (Mono in this case) from a "Supplier". This is the case when you dont know/have the data
 * in advance.
 */
public class Lec05MonoFromSupplier {

    // For example let's assume we are required to provide names to new born kids. So we build a "service/method" which
    // does that. For simplicity, let's take the names from our beloved faker.
    public static String getName() {
        System.out.println("Getting name from repository...");
        return Util.faker().name().firstName();
    }

    // We now can create a Mono instance from this method which now becomes a "supplier" for us. In previous examples,
    // the mono was provided with hardcoded values like integers and strings, not something that gets dynamically
    // calculated
    public static void main(String[] args) {
        Mono<String> receiver1 = Mono.fromSupplier(Lec05MonoFromSupplier::getName);
        // ... and there you go! Easy-peasy!

        // of course the rest of the operations remain the same as before:
        receiver1.subscribe(Util.onNext());


        // Instead of the method, you can have an explicit supplier defined too...
        Supplier<String> namingService = () -> getName();
        Mono<String> receiver2 = Mono.fromSupplier(namingService);

        // and then read it from the mono as before
        receiver2.subscribe(Util.onNext());

        // Finally, you can create a publisher from a "Callable" interface too
        Callable<String> callableNameService = () -> getName();
        Mono<String> receiver3 = Mono.fromCallable(callableNameService);

        // and read it from the receiver
        receiver3.subscribe(Util.onNext());
    }
}
