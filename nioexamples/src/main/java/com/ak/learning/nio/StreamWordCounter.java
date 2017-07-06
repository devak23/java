package com.ak.learning.nio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class StreamWordCounter {

    public static void main(String[] args) {
        StreamWordCounter streamWordCounter = new StreamWordCounter();
        long count = streamWordCounter.countWords("sherlock.txt");
        System.out.println("Total words = " + count);
    }

    private long countWords(String file) {
        String content = null;
        try {
            content = new String(Files.readAllBytes(Paths.get(file)));
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
        Stream<String> words = Stream.of(content.split("\\P{L}+"));
        return words.count();
    }
}
