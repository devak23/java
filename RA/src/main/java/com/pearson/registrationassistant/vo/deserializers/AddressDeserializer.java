package com.pearson.registrationassistant.vo.deserializers;

import com.google.gson.*;
import com.pearson.registrationassistant.vo.Address;

import java.lang.reflect.Type;

/**
 * Responsible for decoding the "address section" of the input JSON
 * Created by abhaykulkarni on 07/12/16.
 */
public class AddressDeserializer implements JsonDeserializer<Address> {

    public static final String ADDRESS_TYPE = "addressType";
    public static final String LINE_1 = "line1";
    public static final String LINE_2 = "line2";
    public static final String LINE_3 = "line3";
    public static final String COMPANY = "company";
    public static final String CITY = "city";
    public static final String COUNTRY = "country";
    public static final String ZIPCODE = "zipcode";
    public static final String TELEPHONE_CODE = "telephoneCode";
    public static final String TELEPHONE = "telephone";
    public static final String EXTENSION = "extension";
    public static final String MOBILE_CODE = "mobileCode";
    public static final String MOBILE = "mobile";
    public static final String PRIMARY = "primary";

    @Override
    public Address deserialize(JsonElement jsonElement,
                               Type type,
                               JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        return new Address()
                .setAddressType(getAsString(jsonObject.get(ADDRESS_TYPE)))
                .setLine1(getAsString(jsonObject.get(LINE_1)))
                .setLine2(getAsString(jsonObject.get(LINE_2)))
                .setLine3(getAsString(jsonObject.get(LINE_3)))
                .setCompany(getAsString(jsonObject.get(COMPANY)))
                .setCity(getAsString(jsonObject.get(CITY)))
                .setCountry(getAsString(jsonObject.get(COUNTRY)))
                .setZipcode(getAsString(jsonObject.get(ZIPCODE)))
                .setTelephoneCode(getAsString(jsonObject.get(TELEPHONE_CODE)))
                .setTelephone(getAsString(jsonObject.get(TELEPHONE)))
                .setExtension(getAsString(jsonObject.get(EXTENSION)))
                .setMobileCode(getAsString(jsonObject.get(MOBILE_CODE)))
                .setMobile(getAsString(jsonObject.get(MOBILE)))
                .setPrimary(getAsString(jsonObject.get(PRIMARY)));

    }


    private String getAsString(JsonElement element) {
        if (element == null) {
            return "";
        }

        return element.getAsString();
    }
}
