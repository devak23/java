package com.ak.reactive.utils;

import com.github.javafaker.Faker;
import org.reactivestreams.Subscriber;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public final class Util {

    public static <T> Consumer<T> onNext() {
        return o -> System.out.println("Util::onNext received: " + o);
    }

    public static Consumer<Throwable> onError() {
        return e -> System.out.println("Util::onError received: " + e.getMessage());
    }

    public static Runnable onComplete() {
        return () -> System.out.println("Util::onComplete: Operation Completed");
    }

    private static final Faker FAKER = Faker.instance();

    public static Faker faker() {
        return FAKER;
    }

    public static void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> Subscriber<T> getSubscriber(String name) {
        return new DefaultSubscriber<>(name);
    }

    public <T> Subscriber<T> getSubscriber() {
        return new DefaultSubscriber<>();
    }
}
