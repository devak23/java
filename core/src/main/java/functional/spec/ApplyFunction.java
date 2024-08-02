package functional.spec;

@FunctionalInterface
interface ApplyFunction<T, R> {
    R apply(T t);
}