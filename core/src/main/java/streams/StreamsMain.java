package streams;

import com.arakelian.faker.model.Person;
import com.arakelian.faker.service.RandomPerson;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.stream.Collectors;
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

        long summation = Stream.iterate(1L, i -> i + 1) //Generate an infinite stream of numbers
                .limit(20) // limit them to 20
                .reduce(0L, Long::sum); // add them up
        log.info("Summation: {}", summation);
        // that was a serial operation.

        // We can now make it parallel by using:
        long summationParallel = Stream.iterate(1L, i -> i + 1)
                .limit(20)
                .parallel() // turn into a parallel stream
                .reduce(0L, Long::sum);
        log.info("summationParallel = {}", summationParallel);
        /*
        Note that, in reality, calling the method parallel on a sequential stream doesn’t imply any concrete
        transformation on the stream itself. Internally, a boolean flag is set to signal that you want to run in
        parallel all the operations that follow the invocation to parallel.

        Parallel streams internally use the default ForkJoinPool (you’ll learn more about the fork/join framework
        which by default has as many threads as you have processors, as returned by Runtime.getRuntime().availableProcessors().
        But you can change the size of this pool using the system property java.util.concurrent.ForkJoinPool.common.parallelism,
        as in the following example: System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism","12");

        However, it is a global setting.
        */

        List<Person> people = RandomPerson.get().listOf(20);

        Map<String, String> peopleMap = people.stream()
                .collect(Collectors.toMap(Person::getFirstName, Person::getLastName));

        System.out.println(peopleMap);

        // Find the number of occurrences of the names in the list.
        List<String> names = List.of("Guru", "Abhay", "Meenakshi", "Abhay", "Kavita", "Guru", "Suhas", "Dhiren", "Abhay");
        Map<String, Long> nameMap = names.stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        name -> names.stream().filter(n -> n.equals(name)).count(),
                        (n1, n2) -> n1
                ));
        System.out.println(nameMap);

        // Another way to achieve the above
        Map<String, Long> nameCountMap = names.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println(nameCountMap);
    }
}
