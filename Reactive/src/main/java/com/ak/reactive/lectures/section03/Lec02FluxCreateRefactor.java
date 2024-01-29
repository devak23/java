package com.ak.reactive.lectures.section03;

import com.ak.reactive.lectures.section03.helper.NameProducer;
import com.ak.reactive.utils.Util;
import reactor.core.publisher.Flux;

public class Lec02FluxCreateRefactor {

    public static void main(String[] args) {

        NameProducer nameProducer = new NameProducer();
        Flux.create(nameProducer).subscribe(Util.getSubscriber());

        Runnable runnable = () -> nameProducer.produce();
        for (int i=0; i<10; i++) {
            new Thread(runnable).start();
        }

        Util.sleep(3);

        // Flux sink can be shared with multiple threads as is shown by the output. so if you want to create a Flux with
        // the help of multiple threads, you can use FluxSink
    }
}
