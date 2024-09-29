package functional.patterns;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Scanner;

// The Execute Around pattern is quite useful for eliminating the boilerplate code that's specific for opening and
// closing resources (I/O operations). Here we check why is there a need for the execute around Pattern
@Slf4j
public class ExecuteAroundPatternNeedMain {

    public static void main(String[] args) throws IOException {

        Double doubleVal = getDouble(); // contains boilerplate code
        log.info("value = {}", doubleVal);
        printDouble(); // contains boilerplate code

    }

    // Both getDouble() and printDouble() have this boilerplate code
    // try ( try (Scanner sc = new Scanner(Path.of("doubles.txt"), StandardCharsets.UTF_8)) {}
    // So how do we get rid of it ?  This is demonstrated in ExecuteAroundPatternMain

    private static void printDouble() throws IOException {
        try (Scanner sc = new Scanner(Path.of("doubles.txt"), StandardCharsets.UTF_8)) {
            while (sc.hasNextDouble()) {
                double doubleVal = sc.nextDouble();
                log.info("double: {}", doubleVal);
            }
        }
    }

    private static Double getDouble() throws IOException {
        try (Scanner sc = new Scanner(Path.of("doubles.txt"), StandardCharsets.UTF_8)) {
            if (sc.hasNextDouble()) {
                return sc.nextDouble();
            }
        }

        throw new RuntimeException("Didn't find any double");
    }
}
