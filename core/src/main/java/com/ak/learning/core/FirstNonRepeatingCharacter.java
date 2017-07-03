package com.ak.learning.core;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

// Write a program which prints the first non-repeating character
// in a String
public class FirstNonRepeatingCharacter {
    public static void main(String[] args) {
        System.out.print("Please enter a line of text: ");
        try (Scanner in = new Scanner(System.in)) {
            String input = in.nextLine();
            Character nonRepeatingChar = findNonRepeatingCharIgnoreCase(input);
            if (nonRepeatingChar != null) {
                System.out.println("First non-repeating character is: " + nonRepeatingChar);
            } else {
                System.out.println("All characters in the string are being repeated");
            }
        }
    }

    private static Character findNonRepeatingCharIgnoreCase(String input) {
        if (input == null || input.length() == 0)
            return null;
        return findNonRepeatingChar(input.toLowerCase());
    }

    private static Character findNonRepeatingChar(String input) {
        if (input == null) {
            return null;
        }

        // get rid of the punctuation characters
        List<String> words = Arrays.asList(input.split("\\P{L}+"));
        String modifiedInput = words.stream().collect(Collectors.joining(" "));
        //System.out.println("Modified string: " + modifiedInput);

        for (int i = 0; i < modifiedInput.length(); i++) {
            char c = modifiedInput.charAt(i);
            // search forward
            if (modifiedInput.indexOf(c, i+1) == -1) {
                // search backwards by constructing a string till this character
                String substring = modifiedInput.substring(0, i);
                if (substring.indexOf(c) == -1) {
                    return c;
                }
            }
        }
        return null;
    }
}
