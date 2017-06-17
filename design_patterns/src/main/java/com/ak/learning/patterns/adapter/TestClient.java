package com.ak.learning.patterns.adapter;

public class TestClient {
    public static void main(String[] args) {
        Socket socket = new Socket();
        SocketAdapter adapter = new SocketAdapterComp(socket);
        Laptop laptop = new Laptop(adapter.get120Volts());
        laptop.connectToPowerSupply();
        try {
            laptop = new Laptop(adapter.get12Volts());
            laptop.connectToPowerSupply();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        CellPhone phone = new CellPhone(adapter.get3Volts());
        phone.connectToPowerSupply();

    }
}
