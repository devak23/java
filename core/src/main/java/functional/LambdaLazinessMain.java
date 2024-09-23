package functional;

import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Slf4j
public class LambdaLazinessMain {
    public static void main(String[] args) {

        Supplier<Integer> supplier = () -> {
            log.info("Supplier: Invoking count");
            return Counter.count();
        };

        Consumer<Integer> consumer = c -> {
            log.info("Consumer: Invoking count");
            c = c + Counter.count();
            log.info("Consumer: {}", c);
        };

        // So, at this point, what is the value of Counter.c?
        log.info("1. Counter: {}", Counter.c);          // 0
        // The supplier and the consumer are lazy, so none of the get() or accept() methods were called at their
        // declarations. The Counter.count() was not invoked, so Counter.c was not incremented.

        log.info("2. Supplier: {}", supplier.get());    // 0
        // we use the post-increment operation in the count method, so the current value of c is used in our statement
        // (in this case, return), and afterward, we increment it by 1. This means that supplier.get() gets back the
        // value of c as 0, while the incrementation takes place after this return, and Counter.c is now 1:

        log.info("3. Counter: {}", Counter.c);          // 1

        consumer.accept(Counter.count());

        log.info("4. Counter: {}", Counter.c);          // 3
        log.info("5. Supplier: {}", supplier.get());    // 3
        log.info("6. Counter: {}", Counter.c);          // 4
    }

    static class Counter {
        static int c;

        public static int count() {
            log.info("Returning {} and incrementing the count to {}", c, (c+1));
            return c++;
        }
    }
}
