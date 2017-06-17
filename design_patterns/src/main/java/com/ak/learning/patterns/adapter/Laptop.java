package com.ak.learning.patterns.adapter;

public class Laptop {
    private Volt volts;

    public Laptop(Volt volts) {
        this.volts = volts;
    }

    public void connectToPowerSupply() {
        if (volts.getVolts() > 120) {
            throw new RuntimeException("Too much of power to charge the laptop");
        }

        if (volts.getVolts() < 120) {
            throw new RuntimeException(String.format("%sV - Power source is questionable to charge the laptop",volts.getVolts()));
        }

        System.out.println("Laptop is being charged at 120V");
    }
}
