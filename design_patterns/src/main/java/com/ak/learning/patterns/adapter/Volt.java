package com.ak.learning.patterns.adapter;

public class Volt {
    private int magnitude;

    public Volt(int volts) {
        setVolts(volts);
    }

    public int getVolts() {
        return magnitude;
    }

    public void setVolts(int volts) {
        this.magnitude = volts;
    }
}
