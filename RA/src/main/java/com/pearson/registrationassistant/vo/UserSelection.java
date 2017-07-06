package com.pearson.registrationassistant.vo;

/**
 * The wrapper POJO that contains the applicant information
 *
 * Created by abhaykulkarni on 03/12/16.
 */
public class UserSelection {
	private Applicant applicant;

	public Applicant getApplicant() {
		return applicant;
	}

	public UserSelection setApplicant(Applicant applicant) {
		this.applicant = applicant;
		return this;
	}

	@Override
	public String toString() {
		return "UserSelection{" +
				"applicant=" + applicant +
				'}';
	}
}
