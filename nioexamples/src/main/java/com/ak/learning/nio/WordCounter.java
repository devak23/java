package com.ak.learning.nio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class WordCounter {
    private String content = null;
    private long count = 0;

    public long getCount() {
        return count;
    }

    public static void main(String[] args) {
        WordCounter counter = new WordCounter();
        System.out.println("================= WORD COUNT ====================");
        Map<String, Integer> countWords = counter.countWords("sherlock.txt");
        System.out.println("Word count = " + countWords);
        System.out.println("Total word count = " + counter.getCount());

        Map<String, Integer> largeWords = counter.countWords("sherlock.txt", 7);
        System.out.println("================= LARGE WORDS ======================");
        System.out.println("Word Count = " + largeWords);
        System.out.println("Total word count = " + counter.getCount());
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
        this.count = words.size();
        words.forEach(w -> {
            if (w.length() > largeCount) {
                wordCount.merge(w, 1, Integer::sum);
            }
        });

        return wordCount;
//        return sortByValues(wordCount);
    }

    private Map<String, Integer> countWords(String file) {
        Map<String, Integer> wordCount = new HashMap<>();
        if (content == null) {
            readFile(file);
        }

        if (content == null) {
            return wordCount;
        }
        List<String> words = Arrays.asList(content.split("\\P{L}+"));
        this.count = words.size();
        words.forEach (word -> {
           wordCount.merge(word, 1, Integer::sum);
        });

        return wordCount;
//        return sortByValues(wordCount);

    }

    private void readFile(String file) {
        try {
            content = new String(Files.readAllBytes(Paths.get(file)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    /**  To sort the TreeMap by values */
//    private static <K,V extends Comparable<V>>  Map<K, V> sortByValues(final Map<K, V> map) {
//        Comparator<K> valueComparator = new Comparator<K>() {
//            @Override
//            public int compare(K key1, K key2) {
////                return map.get(key1).compareTo(map.get(key2));
//                int comparison = map.get(key1).compareTo(map.get(key2));
//                if (comparison == 0) {
//                    return 1;
//                } else {
//                    return comparison;
//                }
//            }
//        };
//        Map<K,V> sortedByValues = new TreeMap<>(valueComparator);
//        sortedByValues.putAll(map);
//        return sortedByValues;
//    }
}
