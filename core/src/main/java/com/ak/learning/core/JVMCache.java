package com.ak.learning.core;

public class JVMCache {

    public static void main(String[] args) {
        Integer num1 = 100, num2 = 1000;
        System.out.println(num1 == num2);

        Integer age1 = 23, age2 = 23;
        System.out.println(age1 == age2);
    }
    // the second one prints true because age1 and age2 refer to the same constant in
    // JVM's cache and therefore the references point to same object and hence
    // both objects (actually just one object) are the same.
}
