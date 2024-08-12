package streams;

import com.arakelian.faker.model.Person;
import com.arakelian.faker.service.RandomPerson;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
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

        // Convert a List of Person to Map of Firstname and Lastname
        List<Person> people = RandomPerson.get().listOf(20);
        Map<String, String> peopleMap = people.stream()
                .collect(Collectors.toMap(Person::getFirstName, Person::getLastName, (name1, name2) -> name1));
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

        // Another way and probably more efficient way to achieve the above
        Map<String, Long> nameCountMap = names.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println(nameCountMap);

        // Using Teeing to find the max and min marks of a Student. Starting with JDK 12, we can merge the results of
        // two collectors via Collectors.teeing(). The result is a Collector that is a composite of two passed downstream
        // collectors. Every element that's passed to the resulting collector is processed by both downstream collectors,
        // and then their results are merged into the final result using the specified BiFunction.
        List<Student> students = List.of(
                Student.builder().name("abhay").id(1).marks(91).build(),
                Student.builder().name("manik").id(2).marks(96).build(),
                Student.builder().name("soham").id(3).marks(99).build(),
                Student.builder().name("kapil").id(4).marks(58).build(),
                Student.builder().name("susheel").id(5).marks(57).build()
        );

        Map<String, Student> mapOfStudent = students.stream().collect(
                Collectors.teeing(
                        Collectors.maxBy(Comparator.comparing(Student::marks)),
                        Collectors.minBy(Comparator.comparing(Student::marks)),
                        (s1, s2) -> {
                            Map<String, Student> highestAndLowest = new HashMap<>(5);
                            highestAndLowest.put("Highest", s1.orElseThrow());
                            highestAndLowest.put("Lowest", s2.orElseThrow());
                            return highestAndLowest;
                        }
                )
        );
        System.out.println(mapOfStudent);

        // Using teeing to get the mean of numbers
        List<Integer> numbersAgain = List.of(1, 2, 3 ,4, 5, 6, 7, 8, 9, 10);
        Long mean = numbersAgain.stream().collect(Collectors.teeing(
                Collectors.summingInt(i -> i),
                Collectors.counting(),
                ((sumOfNumbers, n) -> sumOfNumbers / n)
        ));
        System.out.println("mean = " + mean);

        System.out.println("Total number of distinct characters in the name list: " + names.stream().distinct().mapToInt(String::length).sum());

        // Yet another teeing example:
        CountSum countSum = Stream.of(2, 11, 1, 5, 7, 8, 12)
                .collect(Collectors.teeing(
                        Collectors.counting(),
                        Collectors.summingInt(num -> num),
                        CountSum::new
                ));
        System.out.println("Count = " + countSum.count + ", Sum = " + countSum.sum);
        // Here, we have applied two collectors to each element from the stream (counting() and summingInt()) and the
        // results have been merged in an instance of CountSum

        // Another problem: Find the min and max of a given stream of numbers
        MinMax minMax = Stream.of(1, 3, -2, 34, 21, 21, -22, 21, 56, 33, -45).collect(Collectors.teeing(
                Collectors.minBy(Comparator.naturalOrder()),
                Collectors.maxBy(Comparator.naturalOrder()),
                (Optional<Integer> num1, Optional<Integer> num2) -> new MinMax(num1.orElse(Integer.MIN_VALUE), num2.orElse(Integer.MAX_VALUE))
        ));
        System.out.println("Max: " + minMax.max + ", Min: " + minMax.min);

        // Let's have another one:
        Map<Double, Long> elevations = DoubleStream.of(
                22, -10, 100, -5, 100, 123, 123, 22, 230, -1, -21, 250, 22, 21, -34, -12)
                .filter(e -> e > 0)
                .map(e -> e * 0.393701)
                .boxed()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println("elevations = " + elevations);

        // We start with a list of elevations given in centimeters (based on sea level being 0). We first filter the
        // negative elevations via filter, then convert them into inches via map, and then we count them via groupingBy
        // and counting.

        // The resulting data is carried by Map<Double, Long>, which is not very expressive. If we pull this map out of
        // the context (for instance, we pass it as an argument into a method), it is hard to say what Double and Long
        // represent. It would be more expressive to have something such as Map<Elevation, ElevationCount>, which
        // clearly describes its content. Using the record classes Elevation and Elevation count, the same code now looks
        // much better!
        Map<Elevation, ElevationCount> elevationCountMap = DoubleStream.of(
                22, -10, 100, -5, 100, 123, 22, 230, -1, 250, 22)
                .filter(e -> e > 0)
                .mapToObj(Elevation::new)
                .collect(Collectors.groupingBy(Function.identity()
                        , Collectors.collectingAndThen(Collectors.counting(), ElevationCount::new))
                );


        // find the longest name in the list
        String longestName = names.stream().reduce((n1, n2) -> n1.length() > n2.length() ? n1 : n2).orElseThrow();
        String shortestName = names.stream().reduce((n1, n2) -> n1.length() < n2.length() ? n1 : n2).orElseThrow();
        System.out.println("longest name = " + longestName + ", shortest name = " + shortestName);

        // Streaming characters from a String
        var lineOfText = "The sky is falling down";
        lineOfText.chars().forEach(i -> System.out.println((char)i));
        System.out.println("============ Again ================");
        lineOfText.chars().mapToObj(i -> (char) i).forEach(System.out::println);

        // Arrange the students in their ascending marks
        List<Student> sortedAscending = students.stream().sorted(Comparator.comparing(Student::marks)).toList();
        System.out.println(sortedAscending);
        // Arrange the students in their descending order of marks
        List<Student> sortedDescending = students.stream().sorted(Comparator.comparing(Student::marks).reversed()).toList();
        System.out.println(sortedDescending);

        // Here, we construct a map that contains, for each language in the available locales, its name in your default
        // locale (such as "German") as key, and its localized name (such as "Deutsch") as value.
        Stream<Locale> locales = Stream.of(Locale.getAvailableLocales());
        Map<String, String> languageNames = locales.collect(
          Collectors.toMap(
                  Locale::getDisplayLanguage,
                  l -> l.getDisplayLanguage(l),
                  (existingValue, newValue) -> existingValue
          )
        );
        System.out.println(languageNames);
    }

    // The following class simply stores the number of elements in a stream of integers and their sum
    static class CountSum {
        private final Long count;
        private final Integer sum;
        public CountSum(Long count, Integer sum) {
            this.count = count;
            this.sum = sum;
        }
    }

    record MinMax(Integer min, Integer max) {}

    record Elevation(double value) {
        Elevation(double value) {
            this.value = value * 0.393701;
        }
    }

    record ElevationCount(long count) {}
}
