package com.ak.learning.concurrency.core;

import java.math.BigInteger;

/**
 * In this example, we will efficiently calculate the following result = base1 ^ power1 + base2 ^ power2
 * Where a^b means: a raised to the power of b.
 * For example 10^2 = 100
 * We know that raising a number to a power is a complex computation, so we like to execute:
 * result1 = x1 ^ y1
 * result2 = x2 ^ y2
 * In parallel...and combine the result in the end : result = result1 + result2
 * This way, we can speed up the entire calculation.
 */

public class ComplexCalculation {
    public BigInteger calculateResult(BigInteger base1, BigInteger power1, BigInteger base2, BigInteger power2) throws InterruptedException {
        BigInteger result;
        /*
            Calculate result = ( base1 ^ power1 ) + (base2 ^ power2).
            Where each calculation in (..) is calculated on a different thread
        */
        Thread t1 = new PowerCalculatingThread(base1, power1);
        Thread t2 = new PowerCalculatingThread(base2, power2);
        t1.start();
        t2.start();

        t1.join();
        t2.join();


        result = ((PowerCalculatingThread) t1).getResult().add(((PowerCalculatingThread) t2).getResult());
        return result;
    }

    private static final class PowerCalculatingThread extends Thread {
        private BigInteger result = BigInteger.ONE;
        private BigInteger base;
        private BigInteger power;

        public PowerCalculatingThread(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;

        }

        public void run() {
//            for (int i = 1; i <= power.intValue(); i++) {
//                result = result.multiply(base);
//            }

            for (BigInteger i = BigInteger.ZERO;
                 i.compareTo(power) != 0;
                 i = i.add(BigInteger.ONE)) {
                result = result.multiply(base);
            }
        }

        public BigInteger getResult() {
            return result;
        }
    }
}
