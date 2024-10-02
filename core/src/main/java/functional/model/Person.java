package functional.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class Person {
    private Data data;
    public String fullName() {
        return STR."\{data.getFirstName()} \{data.getLastName()}";
    }
}
