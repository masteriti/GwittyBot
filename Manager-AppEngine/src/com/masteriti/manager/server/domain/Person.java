package com.masteriti.manager.server.domain;

import com.googlecode.objectify.annotation.Entity;


@Entity
public class Person extends DatastoreObject {

	private String nameFirst;
	private String nameLast;
	private String phoneMain;
	private String phoneAlt;
	private String email;
	
	/**
	 * Getters and Setters
	 */
	
	public String getNameFirst() {
		return nameFirst;
	}
	public String getNameLast() {
		return nameLast;
	}
	public String getPhoneMain() {
		return phoneMain;
	}
	public String getPhoneAlt() {
		return phoneAlt;
	}
	public String getEmail() {
		return email;
	}
	public void setNameFirst(String nameFirst) {
		this.nameFirst = nameFirst;
	}
	public void setNameLast(String nameLast) {
		this.nameLast = nameLast;
	}
	public void setPhoneMain(String phoneMain) {
		this.phoneMain = phoneMain;
	}
	public void setPhoneAlt(String phoneAlt) {
		this.phoneAlt = phoneAlt;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	

}
