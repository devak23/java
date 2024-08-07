package functional.spec;

public interface Effect<T> {
    void apply(T t);
}
