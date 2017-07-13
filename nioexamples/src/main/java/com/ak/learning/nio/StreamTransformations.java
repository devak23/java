package com.ak.learning.nio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StreamTransformations {

    public static void main(String[] args) throws IOException {
        List<String> wordList = Arrays.asList(new String(
                Files.readAllBytes(Paths.get("sherlock.txt"))).split("\\PL+")
        );
        System.out.println(wordList);


        // using the filter function to filter out words.
        List<String> longWords = wordList
                .stream()
                .filter(w -> w.length() > 12)
                .collect(Collectors.toList());
        System.out.println("==================== Long Words ===============================");
        System.out.println(longWords);


        // using the map function to make everything upper case. The argument of the filter
        // is a Predicate<T> that is a function from T to Boolean. In other words, the filter
        // should return a true/false in-order for the stream to include or exlude the element.
        // A map function however doesn't have this condition. It will apply the predicate
        // function to every element.
        List<String> upperCaseLongWords = wordList
                .stream()
                .filter(w -> w.length() > 12)
                .map(String::toUpperCase).collect(Collectors.toList());
        System.out.println("================= UPPER CASED Long Words =======================");
        System.out.println(upperCaseLongWords);


        // Collect just the first letters of every long word
        Set<String> firstLetters = wordList
                .stream()
                .filter(w -> w.length() > 12    )
                .map(w -> w.substring(0,1))
                .collect(Collectors.toSet());
        System.out.println("============ Unique UPPER CASE first Letters");
        System.out.println(firstLetters);
    }
}
