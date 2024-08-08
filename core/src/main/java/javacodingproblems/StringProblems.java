package javacodingproblems;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
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
        inputString.chars().forEach(ch -> elementsOfString.compute((char)ch, checkAndSet));

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
        Map<String, Long> elementsOfString = inputString.chars()
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

/* FROM Java Coding Problems - Anghel Leonard:
 * We are pretty familiar with ASCII characters. We have unprintable control codes between 0-31, printable characters
 * between 32-127, and extended ASCII codes between 128-255. But what about Unicode characters? Consider this section
 * for each problem that requires that we manipulate Unicode characters.
 * So, in a nutshell, early Unicode versions contained characters with values less than 65,535 (0xFFFF). Java represents
 *  these characters using the 16-bit char data type. Calling charAt(i) works as expected as long as i doesn't exceed
 * 65,535. But over time, Unicode has added more characters and the maximum value has reached 1,114,111 (0x10FFFF).
 * These characters don't fit into 16 bits, and so 32-bit values (known as code points) were considered for the UTF-32
 * encoding scheme.
 *
 * Unfortunately, Java doesn't support UTF-32! Nevertheless, Unicode has come up with a solution for still using 16 bits
 * to represent these characters. This solution implies the following:
 *
 * 16-bit high surrogates: 1,024 values (U+D800 to U+DBFF)
 * 16-bit low surrogates: 1,024 values (U+DC00 to U+DFFF)
 *
 * Now, a high surrogate followed by a low surrogate defines what is known as a surrogate pair. Surrogate pairs are used
 * to represent values between 65,536 (0x10000) and 1,114,111 (0x10FFFF). So, certain characters, known as Unicode
 * supplementary characters, are represented as Unicode surrogate pairs (a one-character (symbol) fits in the space of
 * a pair of characters) that are merged into a single code point. Java takes advantage of this representation and
 * exposes it via a suite of methods such as codePointAt(), codePoints(), codePointCount(), and offsetByCodePoints()
 * (take a look at the Java documentation for details). Calling codePointAt() instead of charAt(), codePoints() instead
 * of chars(), and so on helps us to write solutions that cover ASCII and Unicode characters as well.
 *
 * For example, the well-known two hearts symbol is a Unicode surrogate pair that can be represented as a char[]
 * containing two values: \uD83D and \uDC95. The code point of this symbol is 128149.
 * - To obtain a String object from this code point, call String str = String.valueOf(Character.toChars(128149)).
 * - Counting the code points in str can be done by calling str.codePointCount(0, str.length()), which returns 1 even
 * if the str length is 2.
 * - Calling str.codePointAt(0) returns 128149.
 * - Calling str.codePointAt(1) returns 56469.
 * - Calling Character.toChars(128149) returns 2 since two characters are needed to represent this code point being a
 * Unicode surrogate pair.
 * For ASCII and Unicode 16- bit characters, it will return 1.
 */

    public Map<String, Integer> findDupes3(String inputString) {
        Map<String, Integer> elementsOfString = new HashMap<>();

        for (int i = 0; i < inputString.length(); i++) {
            int cp = inputString.codePointAt(i);
            String ch = String.valueOf(Character.toChars(cp));
            if(Character.charCount(cp) == 2) { // 2 means a surrogate pair
                i++;
            }

            elementsOfString.compute(ch, (k, v) -> (v == null) ? 1 : ++v);
        }

        return elementsOfString;
    }

    public Map<String, Long> findDupes4(String inputString) {

        return inputString.codePoints()
                .mapToObj(c -> String.valueOf(Character.toChars(c)))
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()));
    }


    public String firstNonRepeatingChar(String inputString) {
        // Solution that's presented here relies on LinkedHashMap. This Java map is an insertion-order map (it maintains
        // the order in which the keys were inserted into the map) and is very convenient for this solution.
        // LinkedHashMap is populated with characters as keys and the number of occurrences as values.
        // Once LinkedHashMap is complete, it will return the first key that has a value equal to 1. Thanks to the
        // insertion-order feature, this is the first non-repeatable character in the string:

        // So here we will create map using the Collectors.groupingBy providing it a LinkedHashMap as the second argument
        // The first one is Function.identity() which essentially means the element that is being passed. So instead of
        // writing (ch) -> ch, the shorthand is Function.identity(). Finally Collectors.counting() will count the
        // occurrences of the "Function.identity()" and that becomes the value. This always returns a Long.
        Map<Integer, Long> elementsOfString = inputString.codePoints()
                .boxed()
                .collect(Collectors.groupingBy(
                        Function.identity(), LinkedHashMap::new, Collectors.counting()
                ));

        // next up we iterate through this map and find the first character whose count == 1. There could be more such
        // characters but LinkedHashMap ensures that we get the right one because it preserves the order of elements
        // during insertion.
        int cp = elementsOfString.entrySet().stream()
                .filter(e -> e.getValue() == 1L)
                .findFirst()
                .map(Map.Entry::getKey)
                .orElse((int) Character.MIN_VALUE);

        return String.valueOf(cp);
    }
}