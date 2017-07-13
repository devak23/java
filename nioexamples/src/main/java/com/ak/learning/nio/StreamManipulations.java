package com.ak.learning.nio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamManipulations {
    public static void main(String[] args) throws IOException {
        String[] wordArray = new String(Files.readAllBytes(Paths.get("sherlock.txt"))).split("\\PL+");


        // the limit function puts bounds on the stream
        List<Double> doubles = Stream.generate(Math::random).limit(100).collect(Collectors.toList());
        System.out.println("--------------- A 100 Doubles ------------------");
        System.out.println(doubles);

        // the skip function can be used to skip n elements
        List<Double> skippedDoubles = Stream.generate(Math::random).limit(100).skip(50).collect(Collectors.toList());
        System.out.println("--------------- 50 skipped Doubles ------------------");
        System.out.println(skippedDoubles);

        // streams can be concatenated
        Stream<String> concatenatedStream = Stream.concat(letters("Hello"), letters("World"));
        System.out.println("---------------- Concatenated Streams ----------------------");
        List<String> letters = concatenatedStream.collect(Collectors.toList());
        System.out.println(letters);

        // Unique words in the stream
        List<String> uniqueWords =
                Arrays.stream (wordArray)
                .distinct()
                .collect(Collectors.toList());
        System.out.println("--------------------- Unique Words ------------------------");
        System.out.println(uniqueWords);


        // Streams can be sorted too
        List<String> sortedWords = Arrays
                .stream(wordArray)
                .sorted(Comparator.comparing(String::length).reversed())
                .collect(Collectors.toList());
        System.out.println("------------------ Reverse sorted words -----------------------");
        System.out.println(sortedWords);
    }


    public static Stream<String> letters(String s) {
        List<String> list = new ArrayList<>();
        for (Character c : s.toCharArray()) {
            list.add(c.toString());
        }

        return list.stream();
    }
}
