package functional;

import lombok.extern.slf4j.Slf4j;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Slf4j
public class SupplierConsumerMain {

    public static void main(String[] args) {
        Supplier<String> supplier = () -> "This is fun right?";
        log.info("Supplier provides: {}", supplier.get());

        Consumer<String> consumer = (str) -> log.info("Consumer consumes: {}", str);
        consumer.accept(supplier.get());


        BiConsumer<String, String> biConsumer = (str1, str2) -> log.info("Accepting parameters as: {}", STR."\{str2}, \{str1}");
        biConsumer.accept(supplier.get(), "Abhay");
    }
}
