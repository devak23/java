package com.ak.learning.core;

import com.ak.learning.core.connectionpool.ResourceType;
import com.ak.learning.core.connectionpool.connection.DBConnection;

public class TestObjectSizeFetcher {

    public static void main(String[] args) {
        System.out.println("Size of DBConnection object = " + ObjectSizeFetcher.getObjectSize(new DBConnection()));
        System.out.println("Size of ResourceType enum = " + ObjectSizeFetcher.getObjectSize(ResourceType.DATABASE));
        System.out.println("Size of Customer object = " + ObjectSizeFetcher.getObjectSize(new Customer()));
        System.out.println("Size of Name object = " + ObjectSizeFetcher.getObjectSize(new Name()));
        System.out.println("Size of Contact object = " + ObjectSizeFetcher.getObjectSize(new Contact()));
        System.out.println("Size of Address object = " + ObjectSizeFetcher.getObjectSize(new Address()));
        System.out.println("Size of TestObjectSizeFetcher object = " + ObjectSizeFetcher.getObjectSize(new TestObjectSizeFetcher()));
        System.out.println("Size of Empty object = " + ObjectSizeFetcher.getObjectSize(new Empty()));
        System.out.println("Size of Point object = " + ObjectSizeFetcher.getObjectSize(new Point()));
    }

    static final class Empty {

    }

    static final class Point {
        private int x;
        private int y;
        private String label;
    }

    static final class Customer {
        private Name name;
        private float age;
        private Address address;
        private Contact contact;

        public Name getName() {
            return name;
        }

        public Customer setName(Name name) {
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

        public Contact getContact() {
            return contact;
        }

        public Customer setContact(Contact contact) {
            this.contact = contact;
            return this;
        }
    }

    static final class Contact {
        private String email;
        private String cellNumber;
        private String homeTelephone;
        private String officeTelephone;

        public String getEmail() {
            return email;
        }

        public Contact setEmail(String email) {
            this.email = email;
            return this;
        }

        public String getCellNumber() {
            return cellNumber;
        }

        public Contact setCellNumber(String cellNumber) {
            this.cellNumber = cellNumber;
            return this;
        }

        public String getHomeTelephone() {
            return homeTelephone;
        }

        public Contact setHomeTelephone(String homeTelephone) {
            this.homeTelephone = homeTelephone;
            return this;
        }

        public String getOfficeTelephone() {
            return officeTelephone;
        }

        public Contact setOfficeTelephone(String officeTelephone) {
            this.officeTelephone = officeTelephone;
            return this;
        }
    }
    static final class Name {
        private String firstName;
        private String lastName;
        private String middleName;
        private String givenName;
        private String saluation;
        private String alias;

        public String getFirstName() {
            return firstName;
        }

        public Name setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public String getLastName() {
            return lastName;
        }

        public Name setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public String getMiddleName() {
            return middleName;
        }

        public Name setMiddleName(String middleName) {
            this.middleName = middleName;
            return this;
        }

        public String getGivenName() {
            return givenName;
        }

        public Name setGivenName(String givenName) {
            this.givenName = givenName;
            return this;
        }

        public String getSaluation() {
            return saluation;
        }

        public Name setSaluation(String saluation) {
            this.saluation = saluation;
            return this;
        }

        public String getAlias() {
            return alias;
        }

        public Name setAlias(String alias) {
            this.alias = alias;
            return this;
        }
    }

    static final class Address {
        private String line1;
        private String line2;
        private String city;
        private String state;
        private String country;
        private String zipCode;

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

        public String getZipCode() {
            return zipCode;
        }

        public Address setZipCode(String zipCode) {
            this.zipCode = zipCode;
            return this;
        }
    }
}
