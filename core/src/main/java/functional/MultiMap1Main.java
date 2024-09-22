package functional;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class MultiMap1Main {

    public static void main(String[] args) {
        List<Integer> integers = List.of(3, 2, 5, 6, 7, 8);

        // let's filter even numbers and double their value
        List<Integer> evenDoubledClassic = integers.stream().filter(i -> i%2 == 0).map(i -> i * 2).toList();
        log.info("evenDoubledClassic: {}", evenDoubledClassic);

        // Using multimap,
        List<Integer> evenDoubleMultiMap = integers.stream().<Integer>mapMulti((i, consumer) -> {
            if (i % 2 == 0) {
                consumer.accept(i * 2);
            }
        }).toList();
        log.info("evenDoubleMultiMap: {}", evenDoubleMultiMap);
    }
}
