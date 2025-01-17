package com.masteriti.manager.server.domain;

public class Address {
	
	private String street1;
	private String street2;
	private String city;
	private String state;
	private String zip;
	
	/**
	 * Constructors
	 */
	public Address(){}
	
	public Address(Address address) {
		this.street1 = address.street1;
		this.street2 = address.street2;
		this.city = address.city;
		this.state = address.state;
		this.zip = address.zip;
	}
	
	/**
	 * Getters and Setters
	 */
	public String getStreet1() {
		return street1;
	}
	public String getStreet2() {
		return street2;
	}
	public String getCity() {
		return city;
	}
	public String getState() {
		return state;
	}
	public String getZip() {
		return zip;
	}
	public void setStreet1(String street1) {
		this.street1 = street1;
	}
	public void setStreet2(String street2) {
		this.street2 = street2;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setState(String state) {
		this.state = state;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public void copyAddress(Address address) {
		this.street1 = address.street1;
		this.street2 = address.street2;
		this.city = address.city;
		this.state = address.state;
		this.zip = address.zip;
	}
	
}
