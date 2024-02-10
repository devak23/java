package com.ak.reactive.lectures.section06;

import com.ak.reactive.utils.Util;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import static com.ak.reactive.lectures.section06.Lec01ThreadDemo.printThreadName;

public class Lec03SubscribeOnMultipleItems {

    public static void main(String[] args) {

        Flux<Object> flux = Flux.create(fluxSink -> {
                    printThreadName("create");
                    for (int i = 0; i < 4; i++) {
                        fluxSink.next(i);
                        Util.sleep(i);
                    }
                    fluxSink.complete();
                })
                .doOnNext(i -> printThreadName("next " + i));

        Runnable runnable = () -> flux
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe(v -> printThreadName("sub " + v));

        for (int i = 0; i < 4; i++) {
            new Thread(runnable).start();
        }

        Util.sleep(5);
    }
}
