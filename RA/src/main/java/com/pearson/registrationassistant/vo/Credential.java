package com.pearson.registrationassistant.vo;

/**
 * Credential class
 *
 * Created by abhaykulkarni on 03/12/16.
 */
public class Credential {
	private String username;
	private String password;
	private String secQ1;
	private String secAns1;
	private String secQ2;
	private String secAns2;


	public String getUsername() {
		return username;
	}

	public Credential setUsername(String username) {
		this.username = username;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public Credential setPassword(String password) {
		this.password = password;
		return this;
	}

	public String getSecQ1() {
		return secQ1;
	}

	public Credential setSecQ1(String secQ1) {
		this.secQ1 = secQ1;
		return this;
	}

	public String getSecAns1() {
		return secAns1;
	}

	public Credential setSecAns1(String secAns1) {
		this.secAns1 = secAns1;
		return this;
	}

	public String getSecQ2() {
		return secQ2;
	}

	public Credential setSecQ2(String secQ2) {
		this.secQ2 = secQ2;
		return this;
	}

	public String getSecAns2() {
		return secAns2;
	}

	public Credential setSecAns2(String secAns2) {
		this.secAns2 = secAns2;
		return this;
	}

	@Override
	public String toString() {
		return "Credential{" +
				"username='" + username + '\'' +
				", password='" + password + '\'' +
				", secQ1='" + secQ1 + '\'' +
				", secAns1='" + secAns1 + '\'' +
				", secQ2='" + secQ2 + '\'' +
				", secAns2='" + secAns2 + '\'' +
				'}';
	}
}
