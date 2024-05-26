package reflection.constructors;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class Address {
    private String street;
    private int number;
}
