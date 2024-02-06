package com.ak.reactive.lectures.section04.helper;

import com.ak.reactive.utils.Util;
import lombok.Data;
import lombok.ToString;

@Data
@ToString

public class Person {
    private String name;
    private int age;

    public Person() {
        this.name = Util.faker().name().firstName();
        this.age = Util.faker().random().nextInt(1, 30);
    }
}
