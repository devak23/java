package reflection.constructors;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Person {
    private String name;
    private int age;
    private String address;

    public Person() {
        this.name = "anonymous";
        this.age = -1;
        this.address = "NA";
    }

    public Person(String name) {
        this.name = name;
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Person(String name, int age, String address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }
}
