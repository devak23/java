package com.ak.reactive.lectures.section05;

import com.ak.reactive.utils.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;

/**
 * The cache() will store all the elements in a buffer and relay it to anyone who joins fresh WITHOUT any delay.
 */
public class Lec05HotPublishCache {
    private static Stream<String> playMovie() {
        return Stream.of("Scene1", "Scene2", "Scene3", "Scene4", "Scene5", "Scene6", "Scene7");
    }

    public static void main(String[] args) {
        Flux<String> movie = Flux.fromStream(() -> playMovie())
                .delayElements(Duration.ofSeconds(1))
                .cache(); // cache is another alias for publish().replay() (with history). It remembers till Integer.MAX_VALUE

        Util.sleep(3);

        movie.subscribe(Util.getSubscriber("Abhay"));

        Util.sleep(10);

        System.out.println("Guru is about to join");

        movie.subscribe(Util.getSubscriber("Guru"));

        Util.sleep(2);

        movie.subscribe(Util.getSubscriber("Tejas"));

        Util.sleep(5);
    }
}
