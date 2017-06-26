package com.ak.learning.concurrency.producerconsumer.priorityqueue;

public class Person implements Comparable<Person> {
    private int age;

    public int getAge() {
        return age;
    }

    public Person setAge(int age) {
        this.age = age;
        return this;
    }

    @Override
    public int compareTo(Person that) {
        if (that == null) return 1;
        return this.age - that.age;
    }
}
