package com.ak.reactive.lectures.section03;

import com.ak.reactive.lectures.section03.helper.NameProducer;
import com.ak.reactive.utils.Util;
import reactor.core.publisher.Flux;

public class Lec02FluxCreateRefactor {

    public static void main(String[] args) {

        NameProducer nameProducer = new NameProducer();
        Flux.create(nameProducer).subscribe(Util.getSubscriber());

        nameProducer.produce();
    }
}
