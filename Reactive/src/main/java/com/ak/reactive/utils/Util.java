package com.ak.reactive.utils;

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
}
