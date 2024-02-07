package com.ak.reactive.lectures.section05;

import com.ak.reactive.utils.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;

/**
 * A cold publisher is what we have seen all along (except `just`). An example of cold publisher can be thought of a
 * nextflix streaming platform where each individual consumer gets the whole content right from start regardless of
 * when he/she subscribes to the producer.
 */
public class Lec01ColdPublisher {

    // so imagine this method to be a netflix streaming platform which is playing the movie scenes one by one
    private static Stream<String> streamMovie() {
        System.out.println("Got movie streaming request...");
        return Stream.of("Scene1", "Scene2", "Scene3", "Scene4", "Scene5", "Scene6", "Scene7");
    }

    // Now we are going to examine the behavior in the main...
    public static void main(String[] args) {
        // let's first create a movie stream
        Flux<String> movieStream = Flux.fromStream(() -> streamMovie())
                // since playing a scene is going to take some time, lets assume a delay of 2 seconds
                .delayElements(Duration.ofSeconds(2));

        // Now imagine, there are 2 subscribers watching the same movie. Let's assume, I am watching the movie
        movieStream.subscribe(Util.getSubscriber("Abhay"));

        // After some time ...say 30 mins later in real life... which is simulated by a delay of 5 seconds.
        Util.sleep(5);

        // ... let's say my friend Guru also happens to watch the same movie.
        movieStream.subscribe(Util.getSubscriber("Guru"));

        // Now let's examine the program's behavior by blocking the main thread for 60 seconds
        Util.sleep(60);

        // You can now see that "Got movie streaming request..." is seen twice. This means the streaming is done for
        // every subscriber as and when the subscriber receives the request. This is called Cold Publisher.
    }

}
