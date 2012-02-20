package com.masteriti.manager.server.access;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
//import java.util.logging.Logger;

import javax.persistence.Embedded;
import javax.persistence.Transient;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;
import com.googlecode.objectify.util.DAOBase;
//import com.masteriti.manager.server.domain.Message;
import com.masteriti.manager.server.domain.Person;
import com.masteriti.manager.shared.client.TooManyResultsException;


/**
 * Generic DAO for use with Objectify
 * @author Marco
 *
 * @param <T>
 */
public class ObjectifyDao<T> extends DAOBase {
	
//	private static final Logger log = Logger.getLogger(Message.class.getName());
	static final int BAD_MODIFIERS = Modifier.FINAL | Modifier.STATIC | Modifier.TRANSIENT;
	
	static
	{
		ObjectifyService.register(Person.class);
	}
	
	protected Class<T> clazz;
	
	@SuppressWarnings("unchecked")
	public ObjectifyDao()
	{
		Type genericSuperclass = getClass().getGenericSuperclass();
		// Allow this class to be safely instantiated with or without a parameterized type
		if(genericSuperclass instanceof ParameterizedType)
			clazz = (Class<T>)((ParameterizedType)genericSuperclass).getActualTypeArguments()[0];
	}
	
	public Key<T> put(T entity)
	{
		return ofy().put(entity);
	}
	
	public Map<Key<T>, T> putAll(Iterable<T> entities)
	{
		return ofy().put(entities);
	}
	
	public void delete(T entity)
	{
		ofy().delete(entity);
	}
	
	public void deleteKey(Key<T> entityKey)
	{
		ofy().delete(entityKey);
	}
	
	public void deleteAll(Iterable<T> entities)
	{
		ofy().delete(entities);
	}
	
	public void deleteKeys(Iterable<Key<T>> keys)
	{
		ofy().delete(keys);
	}
	
	public T get(Long id) throws EntityNotFoundException
	{
		return ofy().get(this.clazz, id);
	}
	
	public T get(Key<T> key) throws EntityNotFoundException
	{
		return ofy().get(key);
	}
	
	public Map<Key<T>, T> get(Iterable<Key<T>> keys)
	{
		return ofy().get(keys);
	}
	
	public List<T> listAll()
	{
		Query<T> q = ofy().query(clazz).filter("active", true);
		return q.list();
	}
	
	/**
 	 * Convenience method to get all objects matching a single property
 	 * 
	 * @param propName
	 * @param propValue
	 * @return T matching object
	 * @throws TooManyResultsException
	 */
	public T getByProperty(String propName, Object propValue) throws TooManyResultsException
	{
		Query<T> q = ofy().query(clazz);
		q.filter(propName, propValue);
		Iterator<T> fetch = q.limit(2).list().iterator();
		if(!fetch.hasNext())
		{
			return null;
		}
		T obj = fetch.next();
		if(fetch.hasNext())
		{
			throw new TooManyResultsException(q.toString() +" returned too many results");
		}
		return obj;
	}
	
	public List<T> listByProperty(String propName, Object propValue)
	{
		Query<T> q = ofy().query(clazz).filter("active", true);
		q.filter(propName, propValue);
		return q.list();
	}
	
	public List<Key<T>> listKeysByProperty(String propName, Object propValue)
	{
		Query<T> q = ofy().query(clazz);
		q.filter(propName, propValue);
		return q.listKeys();
	}
	
	public T getByExample(T exampleObj) throws TooManyResultsException
	{
		Query<T> q = ofy().query(clazz);
		Iterator<T> fetch = q.limit(2).list().iterator();
		if(!fetch.hasNext())
		{
			return null;
		}
		T obj = fetch.next();
		if(fetch.hasNext())
		{
			throw new TooManyResultsException(q.toString() +" returned too many results!");
		}
		return obj;
	}
	
	public List<T> listByExample(T exampleObj)
	{
		Query<T> queryByExample = buildQueryByExample(exampleObj);
		return queryByExample.list();
	}
	
	public Key<T> getKey(Long id)
	{
		return new Key<T>(this.clazz, id);
	}
	
	public Key<T> key(T obj)
	{
		return ObjectifyService.factory().getKey(obj);
	}
	
	public List<T> listChildren(Object parent)
	{
		return ofy().query(clazz).filter("active", true).ancestor(parent).list();
	}
	
	public List<Key<T>> listChildrenKeys(Object parent)
	{
		return ofy().query(clazz).filter("active", true).ancestor(parent).listKeys();
	}

	protected Query<T> buildQueryByExample(T exampleObj)
	{
		Query<T> q = ofy().query(clazz).filter("active", true);
		
		// Add all non-null properties to query filter
		for (Field field : clazz.getDeclaredFields())
		{
			// Ignore Transient, embedded, array, and collection properties
			if (field.isAnnotationPresent(Transient.class)
					|| (field.isAnnotationPresent(Embedded.class))
					|| (field.getType().isArray())
					|| ((field.getModifiers() & BAD_MODIFIERS) != 0)
					|| (Collection.class.isAssignableFrom(field.getType()))
					)
				continue;
			
			field.setAccessible(true);
			
			Object value;
			try {
				value = field.get(exampleObj);
			}
			catch (IllegalArgumentException e) {
				
				throw new RuntimeException(e);
			} 
			catch (IllegalAccessException e) {
				
				throw new RuntimeException(e);
			}
			
			if(value != null)
			{
				q.filter(field.getName(), value);
			}
		}
		
		return q;
	}
	
	public List<T> listByExamle(T exampleObj)
	{
		Query<T> queryByExample = buildQueryByExample(exampleObj);
		return queryByExample.list();
	}
	
	

}
