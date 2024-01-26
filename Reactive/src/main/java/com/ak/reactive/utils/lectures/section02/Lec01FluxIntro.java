package com.ak.reactive.utils.lectures.section02;

import com.ak.reactive.utils.Util;
import reactor.core.publisher.Flux;

/**
 * So Mono creates 0 or 1 element. What if we had more than one element? Enter Flux. A flux is again a data publisher
 * which can emit 0 or N elements.
 */
public class Lec01FluxIntro {

    public static void main(String[] args) {
        // This is how we create a "Flux of objects". Again, this is a publisher of data just similar to Java stream
        // that we have seen in the traditional Java development

        Flux<Integer> integers = Flux.just(1, 2 ,3 ,4);

        // once again you subscribe to the producer/publisher and extract data one at a time.
        integers.subscribe(
                Util.onNext(),
                Util.onError(),
                Util.onComplete()
        );

        // if you do not have any data to emit, you can just use Flux.empty like so
        Flux<String> names = Flux.empty();
        System.out.println("***** Empty Flux ******");
        names.subscribe(
                Util.onNext(),
                Util.onError(),
                Util.onComplete()
        );

        System.out.println(" ******** Objects flux ********");
        // You can technically have any mix of data such as
        Flux<Object> objects = Flux.just(1, 2.5, 'a', Boolean.TRUE, Util.faker().name().firstName());

        objects.subscribe(Util.onNext(), Util.onError(), Util.onComplete());
    }
}
