package com.ak.learning.patterns.nullobject;

public class CustomerFactory {

    private static String[] getCustomersFromDatabase() {
        return new String[] {"joule", "laplace", "newton", "kirchoff"};
    }

    public static AbstractCustomer getCustomer(String name) {
        for(String customerName : getCustomersFromDatabase()) {
            if (customerName.equals(name)) {
                return new RealCustomer(name);
            }
        }

        return NullCustomer.getInstance();
    }
}
