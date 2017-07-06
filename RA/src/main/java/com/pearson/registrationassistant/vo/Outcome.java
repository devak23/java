package com.pearson.registrationassistant.vo;

import java.util.HashMap;
import java.util.Map;

/**
 * This object holds the result of the validation
 * Created by abhaykulkarni on 03/12/16.
 */
public class Outcome {
	private Map<String, String> validationMessages = new HashMap<>();
	private boolean valid;
	private String uniqueId;
	private String firstName;
	private String middleName;
	private String lastName;


	public Map<String, String> getValidationMessages() {
		return validationMessages;
	}

	public Outcome setValidationMessages(Map<String, String> validationMessages) {
		this.validationMessages = validationMessages;
		return this;
	}

	public boolean isValid() {
		return valid;
	}

	public Outcome setValid(boolean valid) {
		this.valid = valid;
		return this;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public Outcome setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
		return this;
	}

	public String getFirstName() {
		return firstName;
	}

	public Outcome setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public String getMiddleName() {
		return middleName;
	}

	public Outcome setMiddleName(String middleName) {
		this.middleName = middleName;
		return this;
	}

	public String getLastName() {
		return lastName;
	}

	public Outcome setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	@Override
	public String toString() {
		return "Outcome{" +
				"validationMessages=" + validationMessages +
				", valid=" + valid +
				", uniqueId='" + uniqueId + '\'' +
				", firstName='" + firstName + '\'' +
				", middleName='" + middleName + '\'' +
				", lastName='" + lastName + '\'' +
				'}';
	}
}
