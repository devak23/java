package functional.util;

import java.util.Collection;
import java.util.stream.Stream;

public class AsStream {
    private AsStream() {
        throw new IllegalStateException("Utility class. Cannot be instantiated");
    }

    public static <T> Stream<T> elementAsStream(T element) {
        return Stream.ofNullable(element);
    }

    public static <T> Stream<T> elementsWithNullsAsStream(Collection<T> elements) {
        return Stream.ofNullable(elements).flatMap(Collection::stream);
    }

    public static <T> Stream<T> elementsWithoutNullsAsStream(Collection<T> elements) {
        return elements.stream().flatMap(Stream::ofNullable);
    }

    public static <T> Stream<T> collectionAsStream(Collection<T> elements) {
        return Stream.ofNullable(elements)
                .flatMap(Collection::stream)
                .flatMap(Stream::ofNullable);
    }
}
