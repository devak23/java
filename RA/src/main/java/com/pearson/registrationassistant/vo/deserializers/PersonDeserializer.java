package com.pearson.registrationassistant.vo.deserializers;

import com.google.gson.*;
import com.pearson.registrationassistant.vo.Person;

import java.lang.reflect.Type;

/**
 * This class is responsible for decoding the "Person section" of the json
 * Created by abhaykulkarni on 07/12/16.
 */
public class PersonDeserializer implements JsonDeserializer<Person> {

    public static final String FIRST_NAME = "firstName";
    public static final String MIDDLE_NAME = "middleName";
    public static final String LAST_NAME = "lastName";
    public static final String SUFFIX = "suffix";
    public static final String EMAIL = "email";
    public static final String TITLE = "title";
    public static final String ID = "id";

    @Override
    public Person deserialize(JsonElement jsonElement,
                              Type type,
                              JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        return new Person()
                .setID(getAsString(jsonObject.get(ID)))
                .setFirstName(getAsString(jsonObject.get(FIRST_NAME)))
                .setMiddleName(getAsString(jsonObject.get(MIDDLE_NAME)))
                .setLastName(getAsString(jsonObject.get(LAST_NAME)))
                .setSuffix(getAsString(jsonObject.get(SUFFIX)))
                .setEmail(getAsString(jsonObject.get(EMAIL)))
                .setTitle(getAsString(jsonObject.get(TITLE)));
    }

    private String getAsString(JsonElement element) {
        if (element == null) {
            return "";
        }

        return element.getAsString();
    }
}
