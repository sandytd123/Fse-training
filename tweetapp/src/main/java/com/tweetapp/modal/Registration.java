package com.tweetapp.modal;

import java.sql.Date;

public class Registration {
	
	private String firstname;
	private String lastname;
	private String gender;
	private Date dob;
	private String email;
	private String password;
	private Boolean isUserLoggedIn;
	
	public Registration() {
		
	}

	public Registration(String firstname, String lastname, String gender, Date dob, String email, String password,
			Boolean isUserLoggedIn) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.gender = gender;
		this.dob = dob;
		this.email = email;
		this.password = password;
		this.isUserLoggedIn = isUserLoggedIn;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getIsUserLoggedIn() {
		return isUserLoggedIn;
	}

	public void setIsUserLoggedIn(Boolean isUserLoggedIn) {
		this.isUserLoggedIn = isUserLoggedIn;
	}

	@Override
	public String toString() {
		return "Registration [firstname=" + firstname + ", lastname=" + lastname + ", gender=" + gender + ", dob=" + dob
				+ ", email=" + email + ", password=" + password + ", isUserLoggedIn=" + isUserLoggedIn + "]";
	}

	

}
