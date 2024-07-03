package streams;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Slf4j
public class StreamsMain {
    public static void main(String[] args) throws URISyntaxException {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        log.info("numbers: {}", numbers);

        List<Integer> evenNumbers = numbers.stream().filter(n -> n%2 ==0).toList();
        log.info("even numbers: {}", evenNumbers);

        Consumer<Integer> integerProcessor = n -> log.info("number: {}", n);
        numbers.forEach(integerProcessor);

        int[] arrayOfNumbers = {1, 2, 3, 4, 5, 6};
        int sum = Arrays.stream(arrayOfNumbers).sum();
        log.info("Sum of arrayOfNumbers: {} = {}", Arrays.toString(arrayOfNumbers), sum);

        IntConsumer numberProcessor = n -> log.info("number: {}", n);
        IntStream.range(1, 5).forEach(numberProcessor);

        URI uri = StreamsMain.class.getClassLoader().getResource("numbers.txt").toURI();
        try (Stream<String> stream = Files.lines(Paths.get(uri))) {
            Consumer<String> numberConsumer = n -> log.info("yet another number: {}", n);
            stream.forEach(numberConsumer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
