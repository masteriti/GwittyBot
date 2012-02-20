package com.masteriti.manager.shared.proxy;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyForName;

@ProxyForName(value = "com.masteriti.manager.server.domain.Person", locator = "com.masteriti.manager.server.locator.ObjectifyLocator")
public interface PersonProxy extends EntityProxy {
	
	Long getId();
	
	String getNameFirst();
	
	String getNameLast();
	
	String getPhoneMain();
	
	String getPhoneAlt();
	
	String getEmail();
	
	AddressProxy getAddress();
	
	void setNameFirst(String firstname);
	
	void setNameLast(String lastname);
	
	void setPhoneMain(String phone);
	
	void setPhoneAlt(String phone);
	
	void setEmail(String email);
	
	void setAddress(AddressProxy address);
}
