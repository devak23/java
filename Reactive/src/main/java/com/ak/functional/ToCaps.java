package com.ak.functional;

public class ToCaps implements StringProcessor {
    @Override
    public String process(String input) {
        return input.toUpperCase();
    }
}
