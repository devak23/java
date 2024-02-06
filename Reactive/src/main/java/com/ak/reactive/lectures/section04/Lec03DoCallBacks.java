package com.ak.reactive.lectures.section04;

import com.ak.reactive.utils.Util;
import reactor.core.publisher.Flux;

public class Lec03DoCallBacks {

    public static void main(String[] args) {

        System.out.println("We are exploring various callbacks of Flux");
        System.out.println("-------------------------------------------");
        Flux.create(fluxSink -> {
                    System.out.println("Inside Create.");
                    for (int i = 0; i < 5; i++) {
                        fluxSink.next(i);
                    }
                    fluxSink.complete();
                    System.out.println("-- COMPLETED");
                })
                .doOnComplete(() -> System.out.println("doOnComplete is called."))
                .doOnRequest(l -> System.out.println("doOnRequest- 1: " + l)).doFirst(() -> System.out.println("doFirst-1 is called."))
                .doOnNext(o -> System.out.println("doOnNext: " + o))
                .doOnSubscribe(s -> System.out.println("doOnSubscribe - 1: " + s))
                .doOnRequest(l -> System.out.println("doOnRequest- 2: " + l)).doFirst(() -> System.out.println("doFirst-2 is called."))
                .doOnError(e -> System.out.println("doOnError: " + e.getMessage()))
                .doOnTerminate(() -> System.out.println("doOnTerminate - 1 is called."))
                .doOnSubscribe(s -> System.out.println("doOnSubscribe - 2: " + s))
                .doOnCancel(() -> System.out.println("doOnCancel is called."))
                .doOnTerminate(() -> System.out.println("doOnTerminate - 2 is called.")).doFinally(signalType -> System.out.println("doFinally: " + signalType)).doFirst(() -> System.out.println("doFirst-3 is called."))
                .doOnRequest(l -> System.out.println("doOnRequest- 3: " + l))
                .doOnSubscribe(s -> System.out.println("doOnSubscribe - 3: " + s))
                .doOnDiscard(Object.class, o -> System.out.println("doOnDiscard: " + o))
                .subscribe(Util.getSubscriber())
        ;
    }
}
