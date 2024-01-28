package com.ak.reactive.utils;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.time.LocalDateTime;

/**
 * We are creating a simple default subscriber which is pretty much the same as our Broker (@see Broker) class
 */
public class DefaultSubscriber<T> implements Subscriber<T> {
    private String name = "";

    public DefaultSubscriber() {}

    public DefaultSubscriber(String name) {
        this.name = name;
    }
    @Override
    public void onSubscribe(Subscription subscription) {
        subscription.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(T t) {
        System.out.println(name + " Received [" + LocalDateTime.now() +"]: " + t);
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println(name + " ERROR: " + throwable.getMessage());
    }

    @Override
    public void onComplete() {
        System.out.println(name + " Completed.");
    }
}
