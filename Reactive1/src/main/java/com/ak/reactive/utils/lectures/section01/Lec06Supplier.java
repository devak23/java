package com.ak.reactive.utils.lectures.section01;

import com.ak.reactive.utils.Util;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * Can we consume the publisher's event on a different thread? Let's find out.
 */
public class Lec06Supplier {

    // Let's create a supplier who produces names with a certain delay
    private static Mono<String> getName() {
        System.out.println("Generating name...");

        return Mono.fromSupplier(() -> {
            System.out.println("Generating name from supplier");
            Util.sleep(2);
            return Util.faker().name().fullName();
        });
    }

    // Let's subscribe to this publisher
    public static void main(String[] args) {
        // This will obviously not print anything as you already know we have not subscribed to it
        System.out.println("*********** MAIN THREAD starts... *************");
        getName();
        getName();
        getName().subscribe(Util.onNext()); // this will print the name with some delay

        getName(); // this call will be blocked till the above line is printed because you're invoking getName on the main thread.

        // Let's invoke the getName() one a different thread
        System.out.println("******* Invoking on a different thread **********");
        getName()
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe(Util.onNext());

        System.out.println("This line should get printed before the name is consumed by the consumer");

        // This is where the program ends and you will never get to see the output of the above code. The reason is simple
        // the consumer got invoked on a separate thread and before that thread could complete, the main thread was
        // completed. In order to view the output, you will have to pause the main thread. Uncomment the following lines
        // and observe the behavior.

        // System.out.println("********* Now the name will appear on the console **************");
        // Util.sleep(4);
        System.out.println("*********** MAIN THREAD ends. *************");
    }
}
