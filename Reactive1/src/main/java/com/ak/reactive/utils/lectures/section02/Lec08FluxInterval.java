package com.ak.reactive.utils.lectures.section02;

import com.ak.reactive.utils.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Lec08FluxInterval {
    public static void main(String[] args) {
        // interval method essentially just publishes data at a certain interval of time. It acts very much like
        // `TimeUnit.SECONDS.sleep` that we have defined in out Util package. However, note that Flux.interval is a
        // non-blocking delay.
        Flux.interval(Duration.ofSeconds(1))
                .map(i -> Util.faker().name().bloodGroup())
                .subscribe(Util.onNext());

        // If you run the above program without the delay (coded below), the main thread will simply run and get
        // terminated without letting subscribes onNext() method consume any item from the publisher who is emitting
        // data at 1-second delay. We therefore need to block the main thread to actually see what data is the publisher
        // send us.
        Util.sleep(5);

        // where can we use this (Flux.interval)? Any place where period updates are required or if polling is required.
    }
}
