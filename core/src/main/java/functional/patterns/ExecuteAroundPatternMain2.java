package functional.patterns;

import functional.spec.ScannerDoubleFunction;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.function.Function;

@Slf4j
public class ExecuteAroundPatternMain2 {

    public static void main(String[] args) throws IOException {

        Double sumValue = read((Scanner scanner) -> performOperation(scanner, 0.0, sum -> {
            sum += scanner.nextDouble();
            return sum;
        }));
        log.info("summation: {}", sumValue);


        Double productValue = read((Scanner scanner) -> performOperation(scanner, 1.0, prod -> {
            prod *= scanner.nextDouble();
            return prod;
        }));
        log.info("product: {}", productValue);


        Double maxValue = read((Scanner scanner) -> performOperation(scanner, 0.0, max -> {
            double value = scanner.nextDouble();
            return value > max ? value : max;
        }));

        log.info("max: {}", maxValue);
    }


    private static double performOperation(Scanner scanner, Double initialValue, Function<Double, Double> doubleOperation ) {
        double acc = initialValue;
        while (scanner.hasNextDouble()) {
            acc = doubleOperation.apply(acc);
        }
        return acc;
    }

    private static Double read(ScannerDoubleFunction scanningFunction) throws IOException {
        try (Scanner sc = new Scanner(Path.of("doubles.txt"), StandardCharsets.UTF_8)) {
            return scanningFunction.readDouble(sc);
        }
    }
}


// OUTPUT:
// 02:46:39.263 [main] INFO functional.patterns.ExecuteAroundPatternMain2 -- summation: 111.59
// 02:46:39.269 [main] INFO functional.patterns.ExecuteAroundPatternMain2 -- product: 457363.2873600001
// 02:46:39.276 [main] INFO functional.patterns.ExecuteAroundPatternMain2 -- max: 45.2