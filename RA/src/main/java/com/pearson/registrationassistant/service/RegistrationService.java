package com.pearson.registrationassistant.service;

import com.pearson.registrationassistant.vo.Applicant;
import com.pearson.registrationassistant.vo.Outcome;

/**
 * Specification for the Service layer implementation
 *
 * Created by abhaykulkarni on 03/12/16.
 */
public interface RegistrationService {

    Outcome registerCandidateData(Applicant applicant, String client, String fileName);

    Outcome processClientData(String client);

    String generateUniqueID(String client);
}
