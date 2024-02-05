package com.ak.functional;

public class Tripple implements Operation<Integer,Integer> {
    @Override
    public Integer apply(Integer arg) {
        return arg * 3;
    }
}
