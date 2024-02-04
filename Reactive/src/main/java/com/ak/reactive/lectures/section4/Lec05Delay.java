package com.ak.reactive.lectures.section4;

import com.ak.reactive.utils.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Lec05Delay {

    public static void main(String[] args) {
        System.setProperty("reactor.bufferSize.x", "9");

        Flux.range(1, 100)
                .log()
                // Delay elements causes the producer to NOT create anymore new items as the subscriber is not consuming
                // at the requisite speed.
                .delayElements(Duration.ofSeconds(1))
                .subscribe(Util.getSubscriber());

//        Util.sleep(60);

    }
}
