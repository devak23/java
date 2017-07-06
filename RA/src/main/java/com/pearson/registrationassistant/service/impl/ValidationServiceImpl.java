package com.pearson.registrationassistant.service.impl;

import com.pearson.registrationassistant.service.ValidationService;
import com.pearson.registrationassistant.vo.Address;
import com.pearson.registrationassistant.vo.Applicant;
import com.pearson.registrationassistant.vo.Outcome;
import com.pearson.registrationassistant.vo.Person;
import org.apache.log4j.Logger;

import java.util.Map;

import static com.pearson.registrationassistant.util.CommonUtil.isEmpty;

/**
 * Implements the validation of the applicant data.
 * Created by abhaykulkarni on 03/12/16.
 */
public class ValidationServiceImpl implements ValidationService {
	private Logger logger = Logger.getLogger(ValidationServiceImpl.class);

	@Override
	public Outcome validateApplicant(Applicant applicant) {
		Outcome outcome = new Outcome();
		Map<String, String> messages = outcome.getValidationMessages();
		logger.info("applicant: " + applicant);

		if (applicant == null) {
			messages.put("ERR", "Applicant not found in the request. Check the request parameters. If that's ok, then JSON->Java conversion may have failed");
			outcome.setValid(false);
			return outcome;
		}

		validatePerson(applicant.getPerson(), messages);
		validateAddress(applicant.getAddresses(), messages);

		if (!outcome.getValidationMessages().isEmpty()) {
			outcome.setValid(false);
		} else {
			outcome.getValidationMessages().put("AllOk","The data is valid");
			outcome.setValid(true);
		}
		return outcome;
	}

	private void validateAddress(Address[] addresses, Map<String, String> messages) {
		if (addresses == null || addresses.length == 0) return;

		Address addr = addresses[0];
		if (isEmpty(addr.getCountry())) {
			messages.put("SEC02CT", "Country of origin is a required feed");
		}

		if (isEmpty(addr.getLine1())) {
			logger.error("Line1 found null");
			messages.put("SEC02L1", "Address Line 1 is a required feed");
		}

		if (isEmpty(addr.getCity())) {
			messages.put("SEC02CY", "City is a required feed");
		}

		if (isEmpty(addr.getTelephone())) {
			messages.put("SEC02TP", "Telephone number is a required feed");
		}

	}

	private void validatePerson(Person person, Map<String, String> messages) {
		if (isEmpty(person.getFirstName())) {
			logger.error("First name found null");
			messages.put("SEC01FN","First name of the applicant is a required feed");
		}

		if (isEmpty(person.getLastName())) {
			logger.error("Last name found null");
			messages.put("SEC01LN","Last name of the applicant is a required feed");
		}

		if (isEmpty(person.getEmail())) {
			messages.put("SEC01EM","Email of the applicant is a required feed");
		}
	}

}
