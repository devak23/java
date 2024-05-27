package annotations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@VeryImportant
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cat {
    @ImpParam
    String name;
    int age;

    @RunImmediately(times = 3)
    public void meow() {
        System.out.println("Meow!");
    }

    public void drink() {
        System.out.println("Slurp!");
    }
}
