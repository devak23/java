package functional;

import java.util.function.Function;
import java.util.stream.IntStream;

public class CompositionMain {

    public static void main(String[] args) {
        var ints = IntStream.rangeClosed(1, 100).boxed().toList();
        Function<Integer, Integer> doubleIt = i -> i * 2;
        var toHex = doubleIt.andThen(Integer::toHexString);
        var toBinary = doubleIt.andThen(Integer::toBinaryString);

        ints.stream().map(toBinary).forEach(System.out::println);
        ints.stream().map(toHex).forEach(System.out::println);
    }
}
