package functional;

import java.util.List;
import java.util.function.Consumer;

public class ForEachMain {
    public static  <T> void forEachIn(List<T> list, Consumer<T> consumer) {
        for(T t: list) {
            consumer.accept(t);
        }
    }

    public static void main(String[] args) {
        List<Integer> numbers = List.of(1,2,3,4,5,6,7,8,9,10);

        // write the consumer function that does something with the given input
        Consumer<Integer> doPrintThem = System.out::println;
        // equivalent to: Consumer<Integer> doPrintThem = num -> System.out.println(num);

        forEachIn(numbers, doPrintThem);
    }
}
