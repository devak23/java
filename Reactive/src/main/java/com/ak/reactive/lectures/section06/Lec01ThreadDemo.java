package com.ak.reactive.lectures.section06;

import com.ak.reactive.utils.Util;
import reactor.core.publisher.Flux;

public class Lec01ThreadDemo {

    public static void printThreadName(String msg) {
        System.out.println(msg + "\t\t\t: Thread: " + Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        Flux<Object> flux = Flux.create(fluxSink -> {
                    printThreadName("create");
                    fluxSink.next(1);
                })
                .doOnNext(i -> printThreadName("next " + i));

        flux.subscribe(v -> printThreadName("sub " + v));

        Runnable runnable = () -> flux.subscribe(v -> printThreadName("subR " + v));

        for (int i=0; i<2; i++) {
            new Thread(runnable).start();
        }

        Util.sleep(3);
    }
}
