package com.ak.learning.patterns.adapter;

public class SocketAdapterComp implements SocketAdapter {
    private Socket socket;

    public SocketAdapterComp(Socket socket) {
        this.socket = socket;
    }

    @Override
    public Volt get120Volts() {
        return socket.getVolt();
    }

    @Override
    public Volt get12Volts() {
        Volt v = socket.getVolt();
        return convertVolt(v,10);
    }

    @Override
    public Volt get3Volts() {
        Volt v = socket.getVolt();
        return convertVolt(v,40);
    }

    private Volt convertVolt(Volt volts, int factor) {
        return new Volt(volts.getVolts() / factor);
    }
}
