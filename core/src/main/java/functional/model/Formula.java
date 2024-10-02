package functional.model;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.function.Function;


@Slf4j
public class Formula {

    private final Scanner scanner;
    @Getter
    private double result;

    public Formula() throws IOException {
        result = 0.0;
        this.scanner = new Scanner(Path.of("doubles.txt"), StandardCharsets.UTF_8);
    }

    public Formula add() {
        if (scanner.hasNextDouble()) {
            result += scanner.nextDouble();
        }

        return this;
    }

    public Formula minus() {
        if (scanner.hasNextDouble()) {
            result -= scanner.nextDouble();
        }
        return this;
    }

    public Formula multiply() {
        if (scanner.hasNextDouble()) {
            result *= scanner.nextDouble();
        }
        return this;
    }

    public Formula multiplyWithSqrt() {
        if (scanner.hasNextDouble()) {
            result *= Math.sqrt(scanner.nextDouble());
        }
        return this;
    }

    public Formula divide() {
        if (scanner.hasNextDouble()) {
            result /= scanner.nextDouble();
        }
        return this;
    }

    private void close() {
        try (scanner) {
            result = 0.0d;
        }
    }


    public static double compute(Function<Formula, Double> function) throws IOException {
        Formula formula = new Formula();
        double result = 0.0d;

        try {
            result = function.apply(formula);
        } finally {
            formula.close();
        }

        return result;
    }
}
