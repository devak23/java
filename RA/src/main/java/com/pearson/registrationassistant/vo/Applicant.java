package com.pearson.registrationassistant.vo;

import java.util.Arrays;

/**
 * POJO that holds all the information to be passed around in the service layer.
 *
 * Created by abhaykulkarni on 03/12/16.
 */
public class Applicant {
	private Person person;
	private Address[] addresses;
	private AdditionalInfo additionalInfo;
	private Credential credential;
	private String languagePreference;
	private String uniqueId;

	public Person getPerson() {
		return person;
	}

	public Applicant setPerson(Person person) {
		this.person = person;
		return this;
	}

	public Address[] getAddresses() {
		return addresses;
	}

	public Applicant setAddresses(Address[] addresses) {
		this.addresses = addresses;
		return this;
	}

	public AdditionalInfo getAdditionalInfo() {
		return additionalInfo;
	}

	public Applicant setAdditionalInfo(AdditionalInfo additionalInfo) {
		this.additionalInfo = additionalInfo;
		return this;
	}

	public Credential getCredential() {
		return credential;
	}

	public Applicant setCredential(Credential credential) {
		this.credential = credential;
		return this;
	}

	public String getLanguagePreference() {
		return languagePreference;
	}

	public Applicant setLanguagePreference(String languagePreference) {
		this.languagePreference = languagePreference;
		return this;
	}

	public Applicant setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
		return this;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	@Override
	public String toString() {
		return "Applicant{" +
				"person=" + person +
				", addresses=" + Arrays.toString(addresses) +
				", additionalInfo=" + additionalInfo +
				", credential=" + credential +
				", languagePreference='" + languagePreference + '\'' +
				", uniqueId='" + uniqueId + '\'' +
				'}';
	}
}
