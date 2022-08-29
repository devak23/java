package lambdas;

import java.util.function.Predicate;

public class IntPredicateDemo {

    public static void main(String[] args) {
        // in this case no autoboxing is required as the functional interface accepts an int
        IntPredicate evenNumbers = (int i) -> i %2 == 0;
        evenNumbers.test(1000);


        // following code boxes the 1000 into Integer and thus has performance costs. Boxed values are essentially a
        // wrapper around primitive types and are stored on the heap. Therefore, boxed values use more memory and
        // require additional memory lookups to fetch the wrapped primitive value.
        Predicate<Integer> oddNumbers = (Integer i) -> i % 2 != 0;
        oddNumbers.test(1000);

    }
}
