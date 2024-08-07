package functional;

import java.util.function.IntPredicate;
import java.util.stream.IntStream;

public class PrimesMain {

    public static void main(String[] args) {
        IntStream.range(1, 100)
                .filter(PrimesMain::isPrime)
                .boxed()
                .forEach(System.out::println);
    }

    private static boolean isPrime(int i) {
        IntPredicate isDivisible = index -> i % index == 0;
        return i > 1 && IntStream.range(2, i).noneMatch(isDivisible);
    }
}
