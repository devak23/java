package com.ak.learnings.testprograms;

import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by ares on 21/1/19.
 */
public class SortedSetTest {

    public static void main(String[] args) {
        Comparator<Person> personComparator = new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return Float.compare(o1.getAge(), o2.getAge());
            }
        };
        Set<Person> people = new TreeSet<>();

        people.add(new Person("Abhay", 41.0f));
        people.add(new Person("Manik", 40.8f));
        people.add(new Person("Soham", 5.9f));
        people.add(new Person("Suchitra", 65.3f));
        people.add(new Person("Bindurao", 75.4f));

        System.out.println(people);

        people.stream()
                .filter(p -> p.getName().equals("Suchitra"))
                .findFirst()
                .ifPresent(p -> p.setAge(5.4f));

        Person p = Collections.min(people);
        System.out.println(p);

    }

    private static class Person implements Comparable {
        private String name;
        private float age;

        public Person(String name, float age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public Person setName(String name) {
            this.name = name;
            return this;
        }

        public float getAge() {
            return age;
        }

        public Person setAge(float age) {
            this.age = age;
            return this;
        }

        @Override
        public String toString() {
            return "{" +
                    "name: '" + name + '\'' +
                    ", age: " + age +
                    '}';
        }

        @Override
        public int compareTo(Object o) {
            Person that = (Person) o;
            return Float.compare(this.age, that.age);
        }
    }
}
