package com.ak.reactive.utils.lectures.section01;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class Lec01Stream {

    public static void main(String[] args) {
        // Let's create a stream of integers which when iterated, pauses for 2 seconds and emits twice of the given
        // number. This makes it a publisher of data.
        Stream<Integer> intStream = Stream.of(1, 2, 3).map(i -> {
            try {
                System.out.println("sleeping for 2 seconds... (simulating a delay!)");
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            return i * 2;
        });

        // If you print the publisher (stream) now, it won't execute anything. It will only print the stringified
        // intStream Object
        System.out.println(intStream);

        // Why? - Because, stream by default, is lazy and won't do anything unless you connect a "terminal operator".
        // How do you attach a terminal operator? one way is to iterate...

        intStream.forEach(System.out::println); // now this will print the result

    }
}
