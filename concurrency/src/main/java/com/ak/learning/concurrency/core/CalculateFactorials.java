package com.ak.learning.concurrency.core;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CalculateFactorials {

    public static void main(String[] args) throws InterruptedException {
        List<Long> inputNumbers = Arrays.asList(0L, 3435L, 35435L, 2324L, 4656L, 23L, 2435L, 5566L);
        // We want to calculate the factorial of all of these numbers
        // 0!, 3435!, 35435!, 2324!, 4656!, 23!, 2435!, 5566!

        List<FactorialThread> threads = new ArrayList<>(inputNumbers.size());

        for (Long number : inputNumbers) {
            threads.add(new FactorialThread(number));
        }
        System.out.println("Threads added for execution.");

        for (Thread t : threads) {
            t.start();
        }
        System.out.println("Threads started executing...");

        System.out.println("Waiting for threads to finish...");
        for (Thread t : threads) {
            t.join(2000);
        }

        for (int i = 0; i < inputNumbers.size(); i++) {
            FactorialThread t = threads.get(i);
            if (t.isFinished()) {
                System.out.println("Factorial of " + inputNumbers.get(i) + " = " + t.getResult());
            } else {
                System.out.println("The calculation for " + inputNumbers.get(i) + " is still in progress.");
            }
        }

    }

    private static final class FactorialThread extends Thread {
        private Long number;
        private BigInteger result = BigInteger.ZERO;
        private boolean finished = Boolean.FALSE;

        public FactorialThread(Long inputNumber) {
            this.number = inputNumber;
        }

        public void run() {
            this.result = factorial(number);
            finished = Boolean.TRUE;
        }

        public BigInteger factorial(Long number) {
            BigInteger tempResult = BigInteger.ONE;

            for (long i = number; i > 0; i--) {
                tempResult = tempResult.multiply(new BigInteger(Long.toString(i)));
            }

            return tempResult;
        }

;        public boolean isFinished() {
            return finished;
        }

        public BigInteger getResult() {
            return result;
        }
    }
}
