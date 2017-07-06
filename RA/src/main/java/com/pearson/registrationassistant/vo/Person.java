package com.pearson.registrationassistant.vo;

/**
 * The object that holds the Person data
 *
 * Created by abhaykulkarni on 03/12/16.
 */
public class Person {
	private String title;
	private String firstName;
	private String middleName;
	private String lastName;
	private String suffix;
	private String email;
	private String ID;

	public String getTitle() {
		return title;
	}

	public Person setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getFirstName() {
		return firstName;
	}

	public Person setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public String getMiddleName() {
		return middleName;
	}

	public Person setMiddleName(String middleName) {
		this.middleName = middleName;
		return this;
	}

	public String getLastName() {
		return lastName;
	}

	public Person setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public String getSuffix() {
		return suffix;
	}

	public Person setSuffix(String suffix) {
		this.suffix = suffix;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public Person setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getID() {
		return ID;
	}

	public Person setID(String ID) {
		this.ID = ID;
		return this;
	}

	@Override
	public String toString() {
		return "Person{" +
				"title='" + title + '\'' +
				", firstName='" + firstName + '\'' +
				", middleName='" + middleName + '\'' +
				", lastName='" + lastName + '\'' +
				", suffix='" + suffix + '\'' +
				", email='" + email + '\'' +
				", ID='" + ID + '\'' +
				'}';
	}
}
