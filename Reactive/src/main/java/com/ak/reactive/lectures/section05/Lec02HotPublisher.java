package com.ak.reactive.lectures.section05;

import com.ak.reactive.utils.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;

/**
 * A Hot publisher on the other hand, starts producing the data the moment it receives the request for data and does
 * NOT care about how many subscribers were added later on.
 */
public class Lec02HotPublisher {
    // So we create a Movie first
    public static Stream<String> streamMovie() {
        System.out.println("Got request for a movie...");
        return Stream.of("Scene1", "Scene2", "Scene3", "Scene4", "Scene5", "Scene6", "Scene7", "Scene8");
    }

    // now lets see what happens when we invoke a method called 'share' on the publisher
    public static void main(String[] args) {
        // Creating a movie stream
        Flux<String> movieStream = Flux.fromStream(() -> streamMovie())
                .delayElements(Duration.ofSeconds(2))
                .share(); // This makes the publisher hot.

        // Now I start watching the movie
        movieStream.subscribe(Util.getSubscriber("Abhay"));

        // After some time, my friend starts watching the same movie
        Util.sleep(5);

        movieStream.subscribe(Util.getSubscriber("Guru"));

        // and let's wait for the program to complete.
        Util.sleep(60);

        // As you can see that "Got request for a movie..." was printed only once. When second subscriber joined after
        // the delay, he/she has missed 2 scenes and thereafter both subscribers receive the same data/movie.
        // This is similar to a TV show, movie theatre or radio station which is played at a stipulated time. If you
        // miss it, you don't get to watch the show.
    }
}
