package functional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class ApplyFunctionMain {
    public static void main(String[] args) {
        List<String> strings = Arrays.stream(
                "It's slow. It's methodical and most of the people dont have the stomach for it"
                .split(" ")
        ).toList();

        List<Integer> lengths = map(strings, String::length);
        System.out.println("lengths = " + lengths);
    }

    public static  <T, R> List<R> map(List<T> list, Function<T, R> f) {
        var result = new ArrayList<R>(10);
        for(T  t: list) {
            result.add(f.apply(t));
        }

        return result;
    }
}


@FunctionalInterface
interface ApplyFunction<T, R> {
    R apply(T t);
}
