package com.masteriti.manager.server.access;

import com.masteriti.manager.server.domain.Person;
import com.masteriti.manager.server.access.ObjectifyDao;

public class PersonDao extends ObjectifyDao<Person> {

	/**
	 * Wraps put() so as not to return a Key, which RF can't handle
	 * @param person
	 */
	public void save(Person person)
	{
		this.put(person);
	}
	
	public Person saveAndReturn(Person person)
	{
		try
		{
			return person;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void delete(Person person)
	{
		ofy().delete(person);
	}
	
}
