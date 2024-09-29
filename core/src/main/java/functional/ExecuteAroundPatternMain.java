package functional;

import functional.spec.ScannerDoubleFunction;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Scanner;

// First we define a functional interface ScannerDoubleFunction which defines an input Scanner and returns a double
// @FunctionalInterface
// public interface ScannerDoubleFunction {
//     double readDouble(Scanner scanner) throws IOException;
// }

// We now need a method that receives scanner as input and executes it. So we define the following method
//    private static Double read(ScannerDoubleFunction scanningFunction) throws IOException {
//        try (Scanner sc = new Scanner(Path.of("doubles.txt"), StandardCharsets.UTF_8)) {
//            return scanningFunction.readDouble(sc);
//        }
//    }

// Now in order to use this function, we need to have an implementation of the function via traditional way or we can
// pass in a lambda that executes it.

@Slf4j
public class ExecuteAroundPatternMain {

    public static void main(String[] args) throws IOException {
        // When we pass in a lambda, we are actually passing an implementation of the readDouble() directly inline.
        Double doubleNumber = read((Scanner scanner) -> {
            if (scanner.hasNextDouble()) {
                return scanner.nextDouble();
            }
            return Double.NaN;
        });
        log.info("doubleNumber: {}", doubleNumber);

        // For a simple function the passing of implementation is as easy one but if the implementation is large then
        // the in-line implementation can get clumsy and might need to be refactored into a function like so.
        Double summation = read((Scanner scanner) -> getSummation(scanner));
        log.info("summation: {}", summation);

        // This can of course be shortened further by using method references. But let's say there is another requirement
        // to find the product of all doubles, then we don't have to write boilerplate code all over the place and can
        // simply write:
        Double product = read(ExecuteAroundPatternMain::getProduct);
        log.info("product: {}", product);

        // And then it goes on. What's the max of all the doubles?
        Double max = read(ExecuteAroundPatternMain::getMax);
        log.info("max: {}", max);
    }

    private static double getMax(Scanner scanner) {
        double max = 0;
        while (scanner.hasNextDouble()) {
            double value = scanner.nextDouble();
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    private static double getProduct(Scanner scanner) {
        double product = 1.0;
        while (scanner.hasNextDouble()) {
            product *= scanner.nextDouble();
        }
        return product;
    }

    private static double getSummation(Scanner scanner) {
        double sum = 0.0d;
        while (scanner.hasNextDouble()) {
            sum += scanner.nextDouble();
        }
        return sum;
    }

    private static Double read(ScannerDoubleFunction scanningFunction) throws IOException {
        try (Scanner sc = new Scanner(Path.of("doubles.txt"), StandardCharsets.UTF_8)) {
            return scanningFunction.readDouble(sc);
        }
    }
}
