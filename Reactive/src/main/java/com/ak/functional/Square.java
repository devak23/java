package com.ak.functional;

public class Square implements Operation<Integer, Integer> {
    @Override
    public Integer apply(Integer arg) {
        return arg * arg;
    }
}
