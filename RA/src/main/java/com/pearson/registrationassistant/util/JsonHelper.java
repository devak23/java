package com.pearson.registrationassistant.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.pearson.registrationassistant.vo.*;
import com.pearson.registrationassistant.vo.deserializers.*;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * A helper class to deal with Json conversions.
 * Created by abhaykulkarni on 08/12/16.
 */
public class JsonHelper {
    private static Logger logger = Logger.getLogger(JsonHelper.class);

    public static <T> T convertFromJson(String jsonString,
                                        Class<T> target,
                                        Map<Class, JsonDeserializer> map) {
        GsonBuilder builder = new GsonBuilder();
        map.forEach(builder::registerTypeAdapter);
        return builder.create().fromJson(jsonString, target);
    }

    public static <T> String convertToJson(T type) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(type);
    }


    public static UserSelection getUserSelection(String json) {
        Map<Class, JsonDeserializer> deserializerByClassMap = new HashMap<>();
        deserializerByClassMap.put(Person.class, new PersonDeserializer());
        deserializerByClassMap.put(AdditionalInfo.class, new AdditionalInfoDeserializer());
        deserializerByClassMap.put(Credential.class, new CredentialDeserializer());
        deserializerByClassMap.put(Address.class, new AddressDeserializer());
        deserializerByClassMap.put(Applicant.class, new ApplicantDeserializer());

        UserSelection userSelection =
                JsonHelper.convertFromJson(json, UserSelection.class, deserializerByClassMap);
        return userSelection;
    }
}
