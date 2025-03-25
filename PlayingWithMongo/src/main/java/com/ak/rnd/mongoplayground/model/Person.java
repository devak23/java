package com.ak.rnd.mongoplayground.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "people_data")
public class Person {
    @Id
    private String id;
    private String name;
    private String bio;
    private String language;
    private String uid;
}
