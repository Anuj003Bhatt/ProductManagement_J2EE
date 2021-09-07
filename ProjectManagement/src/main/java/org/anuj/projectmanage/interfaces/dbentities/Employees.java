package org.anuj.projectmanage.interfaces.dbentities;

import java.sql.Timestamp;

import org.apache.log4j.Logger;

public class Employees {
	private static final Logger logger = Logger.getLogger(Employees.class);
	private String firstname;
	private String lastname;
	private String email;
	private Timestamp lastlogin;
	public static final String baseQuery="SELECT * FROM EMPLOYEES";
	
	public String getFirstname() {
		logger.trace("Get first name: "+firstname);
		return firstname;
	}
	public void setFirstname(String firstname) {
		logger.trace("Set first name: "+firstname);
		this.firstname = firstname;
	}
	public String getLastname() {
		logger.trace("Get last name: "+lastname);
		return lastname;
	}
	public void setLastname(String lastname) {
		logger.trace("Set last name: "+lastname);
		this.lastname = lastname;
	}
	public String getEmail() {
		logger.trace("Get email: "+email);
		return email;
	}
	public void setEmail(String email) {
		logger.trace("Set email: "+email);
		this.email = email;
	}
	public Timestamp getLastlogin() {
		logger.trace("Get last login: "+lastlogin);
		return lastlogin;
	}
	public void setLastlogin(Timestamp lastlogin) {
		logger.trace("Set last login: "+lastlogin);
		this.lastlogin = lastlogin;
	}
	
}
