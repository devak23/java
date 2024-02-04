package com.ak.reactive.utils;

import com.github.javafaker.Faker;
import org.reactivestreams.Subscriber;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public final class Util {

    private static final Path PATH = Paths.get("src/main/resources");

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

    public static <T> Subscriber<T> getSubscriber(String name) {
        return new DefaultSubscriber<>(name);
    }

    public static <T> Subscriber<T> getSubscriber() {
        return new DefaultSubscriber<>();
    }

    public static <T> Subscriber<T> getSlowSubscriber() {
        return new SlowSubscriber<>("SlowPoke");
    }

    public static Mono<String> getQuotes() {
        return Mono.fromSupplier(() -> {
            String content;
            try {
                return Files.readString(PATH.resolve("quotes.txt"));
            } catch (IOException e) {
                e.printStackTrace();
                return Util.faker().backToTheFuture().quote();
            }
        });
    }
}
