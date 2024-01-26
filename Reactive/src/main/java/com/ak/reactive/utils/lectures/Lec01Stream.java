package com.ak.reactive.utils.lectures;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class Lec01Stream {

    public static void main(String[] args) {
        Stream<Integer> intStream = Stream.of(1).map(i -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            return i * 2;
        });

        // this won't execute anything. It will only print the stringified intStream Object
        System.out.println(intStream);
        // Stream by default is lazy and won't do anything unless you connect a "terminal operator".
        // How do you attach a terminal operator? one way is to iterate...

        intStream.forEach(System.out::println); // now this will print the result

    }
}
