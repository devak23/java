package functional;

public class UpperString implements StringProcessor {
    @Override
    public String process(String input) {
        return input.toUpperCase();
    }
}
