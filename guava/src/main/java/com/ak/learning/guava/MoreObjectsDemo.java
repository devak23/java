package com.ak.learning.guava;

import com.google.common.base.MoreObjects;

// a demonstration of MoreObjects helper provided by Guava
public class MoreObjectsDemo {

    public static void main(String[] args) {
        Customer customer = new Customer();
        customer.setName("Abhay");
        customer.setAge(38.6f);
        Address address = new Address();
        customer.setAddress(address);

        address.setLine1("206, shiv srishti");
        address.setLine2("Andheri (W)");
        address.setCity("Mumbai");
        address.setCountry("India");

        System.out.println("Customer details: " + customer);
    }

    private static final class Customer {
        private String name;
        private float age;
        private Address address;

        public String getName() {
            return name;
        }

        public Customer setName(String name) {
            this.name = name;
            return this;
        }

        public float getAge() {
            return age;
        }

        public Customer setAge(float age) {
            this.age = age;
            return this;
        }

        public Address getAddress() {
            return address;
        }

        public Customer setAddress(Address address) {
            this.address = address;
            return this;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(Customer.class)
                    .add("name", name)
                    .add("age", age)
                    .add("address", address)
                    .toString();
        }
    }

    private static final class Address {
        private String line1;
        private String line2;
        private String city;
        private String state;
        private String country;

        public String getLine1() {
            return line1;
        }

        public Address setLine1(String line1) {
            this.line1 = line1;
            return this;
        }

        public String getLine2() {
            return line2;
        }

        public Address setLine2(String line2) {
            this.line2 = line2;
            return this;
        }

        public String getCity() {
            return city;
        }

        public Address setCity(String city) {
            this.city = city;
            return this;
        }

        public String getState() {
            return state;
        }

        public Address setState(String state) {
            this.state = state;
            return this;
        }

        public String getCountry() {
            return country;
        }

        public Address setCountry(String country) {
            this.country = country;
            return this;
        }

        public String toString() {
            return MoreObjects.toStringHelper(Address.class)
                    .add("line1", line1)
                    .add("line2", line2)
                    .add("city", city)
                    .toString();
        }
    }
}
