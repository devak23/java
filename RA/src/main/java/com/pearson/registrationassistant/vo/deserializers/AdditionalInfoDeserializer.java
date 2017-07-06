package com.pearson.registrationassistant.vo.deserializers;

import com.google.gson.*;
import com.pearson.registrationassistant.vo.AdditionalInfo;

import java.lang.reflect.Type;

/**
 * Responsible for decoding the "AdditionalInfo section" of the input JSON
 * Created by abhaykulkarni on 07/12/16.
 */
public class AdditionalInfoDeserializer implements JsonDeserializer<AdditionalInfo> {

    public static final String EMPLOYER_NAME = "employerName";
    public static final String EMPLOYEE = "employee";
    public static final String WORK_FOR_PARTNER_OR_SELLER = "workForPartnerOrSeller";
    public static final String TAKEN_EXAM_BEFORE = "takenExamBefore";
    public static final String EMPLOYEE_NUMBER = "employeeNumber";
    public static final String NETWORKING_ACADEMY_ID = "networkingAcademyId";
    public static final String NETWORKING_ACADEMY_USERNAME = "networkingAcademyUsername";

    @Override
    public AdditionalInfo deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        return new AdditionalInfo()
                .setEmployerName(jsonObject.get(EMPLOYER_NAME).getAsString())
                .setEmployee(jsonObject.get(EMPLOYEE).getAsString())
                .setWorkForPartnerOrSeller(jsonObject.get(WORK_FOR_PARTNER_OR_SELLER).getAsString())
                .setTakenExamBefore(jsonObject.get(TAKEN_EXAM_BEFORE).getAsString())
                .setEmployeeNumber(jsonObject.get(EMPLOYEE_NUMBER).getAsString())
                .setNetworkingAcademyId(jsonObject.get(NETWORKING_ACADEMY_ID).getAsString())
                .setNetworkingAcademyUsername(jsonObject.get(NETWORKING_ACADEMY_USERNAME).getAsString());
    }
}
