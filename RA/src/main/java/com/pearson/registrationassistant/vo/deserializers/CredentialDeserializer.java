package com.pearson.registrationassistant.vo.deserializers;

import com.google.gson.*;
import com.pearson.registrationassistant.vo.Credential;

import java.lang.reflect.Type;

/**
 * Responsible for decoding "Credential section" of the input JSON
 * Created by abhaykulkarni on 07/12/16.
 */
public class CredentialDeserializer implements JsonDeserializer<Credential> {

    public static final String PASSWORD = "password";
    public static final String SEC_ANS_1 = "secAns1";
    public static final String SEC_ANS_2 = "secAns2";
    public static final String SEC_Q_1 = "secQ1";
    public static final String SEC_Q_2 = "secQ2";
    public static final String USERNAME = "username";

    @Override
    public Credential deserialize(JsonElement jsonElement,
                                  Type type,
                                  JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        return new Credential()
                .setPassword(jsonObject.get(PASSWORD).getAsString())
                .setSecAns1(jsonObject.get(SEC_ANS_1).getAsString())
                .setSecAns2(jsonObject.get(SEC_ANS_2).getAsString())
                .setSecQ1(jsonObject.get(SEC_Q_1).getAsString())
                .setSecQ2(jsonObject.get(SEC_Q_2).getAsString())
                .setUsername(jsonObject.get(USERNAME).getAsString());
    }
}
