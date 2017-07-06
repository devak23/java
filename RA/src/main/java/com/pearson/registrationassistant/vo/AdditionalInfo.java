package com.pearson.registrationassistant.vo;

/**
 * Additional information that captured by Pearson VUE
 * Created by abhaykulkarni on 03/12/16.
 */
public class AdditionalInfo {
	private String employerName;
	private String workForPartnerOrSeller;
	private String takenExamBefore;
	private String employee;
	private String employeeNumber;
	private String networkingAcademyId;
	private String networkingAcademyUsername;

	public String getEmployerName() {
		return employerName;
	}

	public AdditionalInfo setEmployerName(String employerName) {
		this.employerName = employerName;
		return this;
	}

	public String isWorkForPartnerOrSeller() {
		return workForPartnerOrSeller;
	}

	public AdditionalInfo setWorkForPartnerOrSeller(String workForPartnerOrSeller) {
		this.workForPartnerOrSeller = workForPartnerOrSeller;
		return this;
	}

	public String isTakenExamBefore() {
		return takenExamBefore;
	}

	public AdditionalInfo setTakenExamBefore(String takenExamBefore) {
		this.takenExamBefore = takenExamBefore;
		return this;
	}

	public String isEmployee() {
		return employee;
	}

	public AdditionalInfo setEmployee(String employee) {
		this.employee = employee;
		return this;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public AdditionalInfo setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
		return this;
	}

	public String getNetworkingAcademyId() {
		return networkingAcademyId;
	}

	public AdditionalInfo setNetworkingAcademyId(String networkingAcademyId) {
		this.networkingAcademyId = networkingAcademyId;
		return this;
	}

	public String getNetworkingAcademyUsername() {
		return networkingAcademyUsername;
	}

	public AdditionalInfo setNetworkingAcademyUsername(String networkingAcademyUsername) {
		this.networkingAcademyUsername = networkingAcademyUsername;
		return this;
	}

	@Override
	public String toString() {
		return "AdditionalInfo{" +
				"employerName='" + employerName + '\'' +
				", workForPartnerOrSeller=" + workForPartnerOrSeller +
				", takenExamBefore=" + takenExamBefore +
				", employee=" + employee +
				", employeeNumber='" + employeeNumber + '\'' +
				", networkingAcademyId='" + networkingAcademyId + '\'' +
				", networkingAcademyUsername='" + networkingAcademyUsername + '\'' +
				'}';
	}
}
