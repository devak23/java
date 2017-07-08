package com.ak.learning.nio;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class StreamingLargeFiles {
    public static void main(String[] args) throws IOException {
        try (FileInputStream fis
                     = new FileInputStream(String.valueOf(Paths.get("sherlock-large1.txt")));
             Scanner in = new Scanner(fis, "UTF-8");
        ) {
            while (in.hasNextLine()) {
                String line = in.nextLine();
                System.out.println(line);
            }
        }
    }
}
