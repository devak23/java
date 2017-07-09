package com.ak.learning.guava;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;

public class GuavaCollections {
    private enum Sex {MALE, FEMALE};
    public static void main(String[] args) {

        Map items = ImmutableMap.of("coin", "glass", 2.0, 4, Sex.FEMALE, "pencil");
        // map is immutable
        //items.put("key", 1); enable this line to get a java.lang.UnsupportedOperationException
        items
                .entrySet()
                .stream()
                .forEach(System.out::println);


        // arraylist is mutable
        List<String> fruits = Lists.newArrayList("orange", "banana", "mango", "grapes", "pineapple");
        fruits.add("chikoo");
        fruits.stream().forEach(System.out::println);
    }
}
