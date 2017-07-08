package com.ak.learning.concurrency.callablesandfutures.calculatingpi;

import java.util.concurrent.Callable;

/**
 * This code uses the inefficient Leibniz method for generating pi. The focus
 * here is only on the ExecutorFrameWork and how is it supposed to be used.
 */
public class PiCalculator implements Callable<Double> {
    @Override
    public Double call() throws Exception {
        double current = 1.0;
        double next = 0.0;
        double denominator = 1.0;

        for (int i = 0; Math.abs(next - current) > 0.000000001d; denominator+= 2, i++) {
            current = next;
            if (i % 2 == 1) {
                next = current - (1/denominator);
            } else {
                next = current + (1/denominator);
            }
        }
        return current * 4;
    }
}
