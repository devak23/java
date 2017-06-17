package com.ak.learning.patterns.builder;

public class TestClient {

    public static void main(String... args) {
        CarAssembly assembly = new CarAssembly();
        assembly.addBuilder(new EngineBuilder());
        assembly.addBuilder(new CarSkeletonBuilder());
        assembly.addBuilder(new ChasisBuilder());
        assembly.addBuilder(new WheelBuilder());
        Car car = assembly.buildCar();
        System.out.println(car);
    }
}
