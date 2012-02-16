package com.masteriti.manager.shared.client;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.ServiceName;
import com.masteriti.manager.shared.proxy.PersonProxy;

@ServiceName(value = "com.masteriti.manager.server.access.PersonDao", locator = "com.masteriti.manager.server.locator.DaoServiceLocator")
public interface PersonRequest extends RequestContext {
	
	Request<List<PersonProxy>> listAll();
	
	Request<Void> save(PersonProxy person);
	
	Request<PersonProxy> saveAndReturn(PersonProxy person);
	
	Request<Void> delete(PersonProxy person);
	
}
