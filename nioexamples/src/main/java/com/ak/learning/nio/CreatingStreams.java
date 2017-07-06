package com.ak.learning.nio;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CreatingStreams {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("sherlock.txt");
        String contents = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);

        Stream<String> words = Stream.of(contents.split("\\P{L}+}"));
        show("words", words);

        Stream<String> song = Stream.of("row", "row", "row", "your", "boat", "gently", "down", "the", "stream");
        show("song", song);

        Stream<String> silence = Stream.empty();
        show("silence", silence);

        Stream<String> echos = Stream.generate(() -> "Echo");
        show("echos", echos);

        Stream<Double> doubles = Stream.generate(Math::random);
        show("doubles", doubles);

        Stream<BigInteger> integers = Stream.iterate(BigInteger.ONE, n -> n.add(BigInteger.ONE));
        show("integers", integers);

        Stream<String> anotherWayWords = Pattern.compile("\\PL+").splitAsStream(contents);
        show("anotherWayWords", anotherWayWords);

        try (Stream<String> lines = Files.lines(Paths.get("sherlock.txt"))) {
            show("lines", lines);
        }
    }

    private static <T> void show(String title, Stream<T> stream) {
        final int SIZE = 10;
        List<T> firstNElements = stream.limit(SIZE+1).collect(Collectors.toList());
        System.out.println(title + " : ");
        for (int i = 0; i < firstNElements.size(); i++) {
            if ( i > 0)
                System.out.print(",");
            if (i < SIZE)
                System.out.print(firstNElements.get(i));
            else
                System.out.print("...");
        }
        System.out.println();
    }
}
