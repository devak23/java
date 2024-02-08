package com.ak.reactive.lectures.section05;

import com.ak.reactive.utils.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;

/**
 * So what's new here?
 * In this class we made changes to 3 things:
 * 1. We have only 7 scenes in the movie (line# 17)
 * 2. We have reduced the duration of scene completion to 1 second (line# 22)
 * 3. We have increased the duration between the connection of two subscribers !IMPORTANT (line# 28)
 */
public class Lec03HotPublishResubscribe {
    private static Stream<String> playMovie() {
        return Stream.of("Scene1","Scene2","Scene3","Scene4","Scene5","Scene6","Scene7");
    }

    public static void main(String[] args) {
        Flux<String> movie = Flux.fromStream(() -> playMovie())
                .delayElements(Duration.ofSeconds(1))
                .publish()
                .refCount(1);

        movie.subscribe(Util.getSubscriber("Abhay"));
        Util.sleep(10);
        movie.subscribe(Util.getSubscriber("Guru"));

        Util.sleep(60);
    }
}

// So if you see the output, this time our so called "Hot Publisher" has behaved like a cold publisher i.e. when Guru
// connects, the publisher plays him all the movie scenes starting from Scene1. Before Guru connected, the publisher was
// done sending the scenes to Abhay. So the `refCount` got reset back to 1 and the publisher started again.
// Contrast this example with the Lec03HotPublish where Guru joins watching the movie from Scene 3 skipping Scene1 and
// Scene2