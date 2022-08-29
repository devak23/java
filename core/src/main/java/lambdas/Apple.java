package lambdas;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Apple {
    private Float weight;
    private COLOR color;
    private COUNTRY country;

    public String toString() {
        return "(" + weight + "," + color + "," + country + ")";
    }
}
