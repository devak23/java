package com.ak.learning.nio;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

// Just run the run_streaming_file.sh script to execute this program.
// the large file here is zipped under resources and the shell script does the job
// of unzipping it and putting it under /tmp
public class StreamingLargeFiles {
    public static void main(String[] args) throws IOException {
        try (FileInputStream fis
                     = new FileInputStream(String.valueOf(Paths.get("/tmp/sherlock-large3.txt")));
             Scanner in = new Scanner(fis, "UTF-8");
        ) {
            while (in.hasNextLine()) {
                String line = in.nextLine();
                System.out.println(line);
            }
        }
    }
}
