package com.ak.reactive.lectures.section05;

import com.ak.reactive.utils.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;

public class Lec03HotPublish {
    // Let's create a movie theatre
    private static Stream<String> playMovie() {
        return Stream.of("Scene1","Scene2","Scene3","Scene4","Scene5","Scene6","Scene7","Scene8","Scene9","Scene10");
    }

    // now let's turn to the main method
    public static void main(String[] args) {
        Flux<String> movie = Flux.fromStream(() -> playMovie())
                .delayElements(Duration.ofSeconds(2))
                //.share() // the `share()` is an alias for publish().refCount(1)
                .publish() // The `publish()` returns a ConnectableFlux which is a different thing than Flux we are going through
                .refCount(1); // the minSubscribers is the number of subscribers required for this Flux to start emitting data

        // You can change the minSubscribers to say 2 (in our example), in which case the Flux will start data only when both Abhay and Guru
        // start watching movie.

        movie.subscribe(Util.getSubscriber("Abhay"));
        Util.sleep(5);
        movie.subscribe(Util.getSubscriber("Guru"));

        Util.sleep(60);
    }
}
