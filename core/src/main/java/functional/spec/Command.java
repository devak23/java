package functional.spec;

public interface Command<T, R> {

    R execute(T input);
}
