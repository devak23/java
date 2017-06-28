package com.ak.learning.nio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordCounter {
    private String content = null;

    public static void main(String[] args) {
        WordCounter counter = new WordCounter();
        System.out.println("================= WORD COUNT ====================");
        Map<String, Integer> countWords = counter.countWords("sherlock.txt");
        System.out.println("Word count = " + countWords);

        Map<String, Integer> largeWords = counter.countWords("sherlock.txt", 7);
        System.out.println("================= LARGE WORDS ======================");
        System.out.println("Word Count = " + largeWords);
    }

    private Map<String, Integer> countWords(String file, int largeCount) {
        Map<String, Integer> wordCount = new HashMap<>();
        if (content == null) {
            readFile(file);
        }
        if (content == null) {
            return wordCount;
        }

        List<String> words = Arrays.asList(content.split("\\P{L}+"));
        words.forEach(w -> {
            if (w.length() > largeCount) {
                wordCount.merge(w, 1, Integer::sum);
            }
        });

        return wordCount;
    }

    private Map<String, Integer> countWords(String file) {
        Map<String, Integer> wordCount = new HashMap<String, Integer>();
        if (content == null) {
            readFile(file);
        }

        if (content == null) {
            return wordCount;
        }
        List<String> words = Arrays.asList(content.split("\\P{L}+"));
        words.forEach (word -> {
           wordCount.merge(word, 1, Integer::sum);
        });

        return wordCount;

    }

    private void readFile(String file) {
        try {
            content = new String(Files.readAllBytes(Paths.get(file)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
