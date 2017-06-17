package com.ak.learning.patterns.adapter;

public class CellPhone {
    private Volt voltage;

    public CellPhone(Volt volt) {
        this.voltage = volt;
    }

    public void connectToPowerSupply() {
        if (voltage.getVolts() > 3) {
            throw new RuntimeException("Too much Voltage for a cell phone. Cellphone destroyed.");
        }

        if (voltage.getVolts() < 3) {
            throw new RuntimeException("Cell phone needs 3V of power to be charged");
        }

        System.out.println("cell phone is being charged on 3V");
    }
}
