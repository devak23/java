package problems;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class StringProblems {
    public Map<Character, Integer> findDupes1(String inputString) {
        Map<Character, Integer> elementsOfString = new HashMap<>();
        // In order to find the dupes, we employ 2 strategies. One with hashmap's compute method which checks
        // for a given value and manipulates it using a BiFunction. This BiFunction is defined below. Here the function
        // takes a key and value (which is an integer) and returns an Integer.
        BiFunction<Character, Integer, Integer> checkAndSet = (key, count) -> count == null ? 1 : ++count;

        // So here we set the occurrence of each character using the compute function as shown below
        inputString.codePoints().forEach(ch -> elementsOfString.compute((char)ch, checkAndSet));

        // Next we return only those elements from the Map which are truly duplicate by streaming the contents
        // which have a count > 1
        return elementsOfString.entrySet()
                .stream()
                .filter(e -> e.getValue() > 1)
                .collect(
                        Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)
                );
    }

    public Map<String, Long> findDupes2(String inputString) {
        // The second method relies on the Java stream API's Collectors.groupingBy() clause.
        // The groupingBy() method of Collectors class in Java are used for grouping objects by some property and
        // storing results in a Map instance. In order to use it, we always need to specify a property by which the
        // grouping would be performed. This method provides similar functionality to SQL’s GROUP BY clause.

        // In this case, we will be getting a "string character" as explained below and then we will create a map by
        // invoking the Collectors.groupingBy
        Collector<String, ?, Map<String, Long>> stringMapCollector = Collectors.groupingBy(ch -> ch, Collectors.counting());

        // In order to use the above groupingBy clause, we first need to manipulate the string into a sequence of
        // characters and then convert them into a string of characters finally grouping them  and counting them.
        Map<String, Long> elementsOfString = inputString.codePoints()
                .mapToObj(ch -> String.valueOf((char)ch))
                .collect(stringMapCollector);


        // finally we will weed out only the dupes as shown in method above
        return elementsOfString.entrySet()
                .stream()
                .filter(e -> e.getValue() > 1)
                .collect(
                        Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)
                );
    }
}
