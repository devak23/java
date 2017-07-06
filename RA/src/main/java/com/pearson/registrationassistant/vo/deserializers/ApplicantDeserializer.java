package com.pearson.registrationassistant.vo.deserializers;

import com.google.gson.*;
import com.pearson.registrationassistant.vo.*;

import java.lang.reflect.Type;

/**
 * Parent level deserializer
 *
 * Created by abhaykulkarni on 08/12/16.
 */
public class ApplicantDeserializer implements JsonDeserializer {
  public static final String PERSON = "person";
  public static final String ADDITIONAL_INFO = "additionalInfo";
  public static final String CREDENTIAL = "credential";
  public static final String ADDRESSES = "addresses";
  public static final String LANGUAGE_CODE = "languageCode";
  public static final String UNIQUE_ID = "uniqueId";


  @Override
  public Object deserialize(
      JsonElement jsonElement,
      Type type,
      JsonDeserializationContext context) throws JsonParseException {

    JsonObject jsonObject = jsonElement.getAsJsonObject();

    Applicant applicant = new Applicant();

    Person person = context.deserialize(jsonObject.get(PERSON),Person.class);
    AdditionalInfo additionalInfo = context.deserialize(jsonObject.get(ADDITIONAL_INFO), AdditionalInfo.class);
    Credential credential = context.deserialize(jsonObject.get(CREDENTIAL), Credential.class);

    JsonArray jsonAddressArray = jsonObject.get(ADDRESSES).getAsJsonArray();
    Address[] addresses = context.deserialize(jsonAddressArray, Address[].class);

    applicant
        .setPerson(person)
        .setAddresses(addresses)
        .setCredential(credential)
        .setAdditionalInfo(additionalInfo)
        .setUniqueId(getAsString(jsonObject.get(UNIQUE_ID)))
        .setLanguagePreference(getAsString(jsonObject.get(LANGUAGE_CODE)));

    return applicant;
  }

  private String getAsString(JsonElement element) {
    if (element == null) {
      return "";
    }

    return element.getAsString();
  }
}
