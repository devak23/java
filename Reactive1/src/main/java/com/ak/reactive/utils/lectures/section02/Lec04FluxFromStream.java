package com.ak.reactive.utils.lectures.section02;

import com.ak.reactive.utils.Util;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * We can create Flux from Stream as well. However unlike other ways, a stream, once consumed gets closed and therefore
 * the consumption pattern differs slightly.
 */
public class Lec04FluxFromStream {

    public static void main(String[] args) {

        // Let's create out data source first. We will again generate 5 random addresses
        List<String> addresses = IntStream.range(1,5).mapToObj(i -> Util.faker().address().cityName()).toList();
        Stream<String> addressStream = addresses.stream();

        // We can print the address first time
        System.out.println("********* printing addresses (first time) ***********");
        addressStream.forEach(System.out::println);

        try {
            // but the next time, it throws the "Stream is closed" error
            addressStream.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // The same problem appears if there are multiple subscribers to the steam via the reactive API. For ex:
        Stream<String> addressStream2 = addresses.stream();
        Flux.fromStream(addressStream2).subscribe(Util.onNext()); // will print all the adresses

        // but the same call yields the same issue we saw before
        try  {
            Flux.fromStream(addressStream2).subscribe(Util.onNext());
        } catch (Exception e) {
            System.out.println("ERROR again: " + e.getMessage());
        }

        // so the way to get around this problem is to "consume the stream from supplier" rather feed that stream
        // directly to the Flux producer for instance:
        Flux<String> addressProducerFlux = Flux.fromStream(() -> addresses.stream());
        addressProducerFlux.subscribe(Util.onNext(), Util.onError(), Util.onComplete());
        // and that will print the output every single time... why? - notice how we are now passing the supplier
        // () -> address.stream() which gets executed on every subscription of the addressProducerFlux

        System.out.println("**** Again ****");
        addressProducerFlux.subscribe(Util.onNext(), Util.onError(), Util.onComplete());

        System.out.println("**** and again ****");
        addressProducerFlux.subscribe(Util.onNext(), Util.onError(), Util.onComplete());

        System.out.println("**** and yet again ****");
        addressProducerFlux.subscribe(Util.onNext(), Util.onError(), Util.onComplete());
    }
}
