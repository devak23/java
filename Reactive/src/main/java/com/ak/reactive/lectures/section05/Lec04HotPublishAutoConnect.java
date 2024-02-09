package com.ak.reactive.lectures.section05;

import com.ak.reactive.utils.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;

public class Lec04HotPublishAutoConnect {
    private static Stream<String> playMovie() {
        return Stream.of("Scene1","Scene2","Scene3","Scene4","Scene5","Scene6","Scene7");
    }

    public static void main(String[] args) {
        Flux<String> movie = Flux.fromStream(() -> playMovie())
                .delayElements(Duration.ofSeconds(1))
                .publish()
                .autoConnect(0);

        Util.sleep(3);

        movie.subscribe(Util.getSubscriber("Abhay"));

        Util.sleep(10);

        System.out.println("Guru is about to join");

        movie.subscribe(Util.getSubscriber("Guru"));

        Util.sleep(10);
    }
}