package com.ak.learning.concurrency.virtualthreads;

import java.util.Arrays;

public record TestingTeam(String ...testers) {

    @Override
    public String toString() {
        return "TestingTeam(" +
                "testers=" + Arrays.toString(testers) +
                ")";
    }
}
