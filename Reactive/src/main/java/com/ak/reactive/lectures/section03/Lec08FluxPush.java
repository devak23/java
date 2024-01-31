package com.ak.reactive.lectures.section03;

import com.ak.reactive.lectures.section03.helper.NameProducer;
import com.ak.reactive.utils.Util;
import reactor.core.publisher.Flux;

/**
 * This is the same as Flux.create with one difference. Flux.push is not threadsafe but Flux.create is.
 */
public class Lec08FluxPush {

    public static void main(String[] args) {

        NameProducer nameProducer = new NameProducer();
        Flux.push(nameProducer).subscribe(Util.getSubscriber());

        Runnable runnable = () -> nameProducer.produce();
        for (int i=0; i<10; i++) {
            new Thread(runnable).start();
        }

        Util.sleep(3);

    }
}
