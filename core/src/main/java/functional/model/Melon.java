package functional.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Melon implements Fruit {

    private String type;
    private float weight;
    private String color;

    @Override
    public String getName() {
        return "Melon";
    }
}
