package com.pearson.registrationassistant.vo;

/**
 * Created by abhaykulkarni on 03/12/16.
 */
public class Address {
	private String addressType;
	private String line1;
	private String line2;
	private String line3;
	private String city;
	private String zipcode;
	private String state;
	private String country;
	private String telephone;
	private String primary;
	private String languageCode;
	private String company;
	private String telephoneCode;
	private String extension;
	private String mobileCode;
	private String mobile;

	public String getAddressType() {
		return addressType;
	}

	public Address setAddressType(String addressType) {
		this.addressType = addressType;
		return this;
	}

	public String getLine1() {
		return line1;
	}

	public Address setLine1(String line1) {
		this.line1 = line1;
		return this;
	}

	public String getLine2() {
		return line2;
	}

	public Address setLine2(String line2) {
		this.line2 = line2;
		return this;
	}

	public String getLine3() {
		return line3;
	}

	public Address setLine3(String line3) {
		this.line3 = line3;
		return this;
	}

	public String getCity() {
		return city;
	}

	public Address setCity(String city) {
		this.city = city;
		return this;
	}

	public String getZipcode() {
		return zipcode;
	}

	public Address setZipcode(String zipcode) {
		this.zipcode = zipcode;
		return this;
	}

	public String getState() {
		return state;
	}

	public Address setState(String state) {
		this.state = state;
		return this;
	}

	public String getCountry() {
		return country;
	}

	public Address setCountry(String country) {
		this.country = country;
		return this;
	}

	public String getTelephone() {
		return telephone;
	}

	public Address setTelephone(String telephone) {
		this.telephone = telephone;
		return this;
	}

	public String isPrimary() {
		return primary;
	}

	public Address setPrimary(String primary) {
		this.primary = primary;
		return this;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public Address setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
		return this;
	}

	public Address setCompany(String company) {
		this.company = company;
		return this;
	}

	public String getCompany() {
		return company;
	}

	public Address setTelephoneCode(String telephoneCode) {
		this.telephoneCode = telephoneCode;
		return this;
	}

	public String getTelephoneCode() {
		return telephoneCode;
	}

	public Address setExtension(String extension) {
		this.extension = extension;
		return this;
	}

	public String getExtension() {
		return extension;
	}

	public Address setMobileCode(String mobileCode) {
		this.mobileCode = mobileCode;
		return this;
	}

	public String getMobileCode() {
		return mobileCode;
	}

	public Address setMobile(String mobile) {
		this.mobile = mobile;
		return this;
	}

	public String getMobile() {
		return mobile;
	}

	@Override
	public String toString() {
		return "Address{" +
				"addressType='" + addressType + '\'' +
				", line1='" + line1 + '\'' +
				", line2='" + line2 + '\'' +
				", line3='" + line3 + '\'' +
				", city='" + city + '\'' +
				", zipcode='" + zipcode + '\'' +
				", state='" + state + '\'' +
				", country='" + country + '\'' +
				", telephone='" + telephone + '\'' +
				", primary=" + primary +
				", languageCode='" + languageCode + '\'' +
				", company='" + company + '\'' +
				", telephoneCode='" + telephoneCode + '\'' +
				", extension='" + extension + '\'' +
				", mobileCode='" + mobileCode + '\'' +
				", mobile='" + mobile + '\'' +
				'}';
	}
}
