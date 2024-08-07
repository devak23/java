package functional;

import functional.spec.StringProcessor;

public class UpperString implements StringProcessor {
    @Override
    public String process(String input) {
        return input.toUpperCase();
    }
}
