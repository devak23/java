package com.ak.reactive.lectures.section06;

import com.ak.reactive.utils.Util;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import static com.ak.reactive.lectures.section06.Lec01ThreadDemo.*;

public class Lec04PublishOn {

    public static void main(String[] args) {

        Flux<Object> flux = Flux.create(fluxSink -> {
            printThreadName("create");
            for (int i = 0; i < 4; i++) {
                fluxSink.next(i);
            }
        })
                .doOnNext(i -> printThreadName("publish " + i));

        flux
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(i -> printThreadName("received " + i))
                .publishOn(Schedulers.parallel())
                .subscribe(v -> printThreadName("consumed " + v));

        Util.sleep(5);
    }
}
