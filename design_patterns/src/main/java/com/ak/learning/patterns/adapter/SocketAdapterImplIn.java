package com.ak.learning.patterns.adapter;

public class SocketAdapterImplIn extends Socket implements SocketAdapter {
    @Override
    public Volt get120Volts() {
        return getVolt();
    }

    @Override
    public Volt get12Volts() {
        Volt v = getVolt();
        return convertVolt(v, 10);
    }

    @Override
    public Volt get3Volts() {
        Volt v = getVolt();
        return convertVolt(v, 40);
    }

    private Volt convertVolt(Volt volts, int factor) {
        return new Volt(volts.getVolts()/factor);
    }
}
