package com.masteriti.manager.server.locator;

import com.google.web.bindery.requestfactory.shared.Locator;
import com.masteriti.manager.server.access.ObjectifyDao;
import com.masteriti.manager.server.domain.DatastoreObject;

public class ObjectifyLocator extends Locator<DatastoreObject, Long> {

	@Override
	public DatastoreObject create(Class<? extends DatastoreObject> clazz) {
		try {
			return clazz.newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public DatastoreObject find(Class<? extends DatastoreObject> clazz, Long id) {
		
		@SuppressWarnings("rawtypes")
		ObjectifyDao daoBase = new ObjectifyDao();
		return daoBase.ofy().find(clazz, id);
	}

	@Override
	public Class<DatastoreObject> getDomainType() {
		// never called	
		return null;
	}

	@Override
	public Long getId(DatastoreObject domainObject) {
		
		return domainObject.getId();
	}

	@Override
	public Class<Long> getIdType() {

		return Long.class;
	}

	@Override
	public Object getVersion(DatastoreObject domainObject) {

		return domainObject.getVersion();
	}
	
}
