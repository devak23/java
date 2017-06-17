package com.ak.learning.patterns.nullobject;

public class TestClient {
    public static void main(String... args) {
        AbstractCustomer einstein = CustomerFactory.getCustomer("einstein");
        AbstractCustomer copernicus = CustomerFactory.getCustomer("copernicus");
        AbstractCustomer laplace = CustomerFactory.getCustomer("laplace");
        AbstractCustomer newton = CustomerFactory.getCustomer("newton");
        AbstractCustomer fourier = CustomerFactory.getCustomer("fourier");

        System.out.println(einstein.getName());
        System.out.println(copernicus.getName());
        System.out.println(laplace.getName());
        System.out.println(newton.getName());
        System.out.println(fourier.getName());

    }
}
