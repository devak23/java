package com.ak.reactive.lectures.section06;

import com.ak.reactive.utils.Util;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import static com.ak.reactive.lectures.section06.Lec01ThreadDemo.printThreadName;

public class Lec02SubscribeOnDemo {

    public static void main(String[] args) {

        Flux<Object> flux = Flux.create(fluxSink -> {
            printThreadName("create");
            fluxSink.next(1);
        })
                .subscribeOn(Schedulers.newParallel("Creator"))
                .doOnNext(i -> printThreadName("next " + i));

        System.out.println("***** Running in Main *****");
        flux
                .doFirst(() -> printThreadName("[Main]: First 2"))
                // if you select parallel, it provides 1 thread per CPU. So if you have a 4 core CPU, you will get 4 threads
                // if you select boundedElastic, it provides 10 threads per CPU. So you will have 40 threads for a 4 core CPU
                // so if you have time-consuming tasks, boundedElastic is nice.
                .subscribeOn(Schedulers.boundedElastic())
                .doFirst(() -> printThreadName("[Main]: First 1"))
                .subscribe(v -> printThreadName("[Main]: sub " + v));

        System.out.println("***** Running in Thread *****");
        Runnable runnable = () -> flux
                .doFirst(() -> printThreadName("[Runnable]: First2 in Thread"))
                .subscribeOn(Schedulers.boundedElastic())
                .doFirst(() -> printThreadName("[Runnable]: First1 in Thread"))
                .subscribe(v -> printThreadName("[Runnable]: sub " + v));

        for (int i=0; i<2; i++) {
            new Thread(runnable).start();
        }

        Util.sleep(5);
    }
}
