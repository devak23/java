package functional;

import java.util.Objects;

@FunctionalInterface
public interface StringProcessor {
    String process(String input);

    static boolean isLowerCase(String input) {
        Objects.requireNonNull(input);
        return input
                .chars()
                .mapToObj(i -> (char) i)
                .toList()
                .stream()
                .noneMatch(Character::isUpperCase);
    }
}
