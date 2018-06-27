package org.eclipse.basyx.aas.api.services;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.eclipse.basyx.aas.api.exception.ServerException;
import org.eclipse.basyx.aas.api.reference.IElementReference;



/**
 * Basic model provider backend interface
 * 
 * @author kuhn
 *
 */
public interface IModelProvider {

	
	/**
	 * Get scope of a provided element. 
	 * 
	 * This is the namespace that is served by this model provider. E.g. de.fraunhofer.iese
	 */
	public String getElementScope(String elementPath);

	
	/**
	 * Get a sub model property value
	 * 
	 * @param path Path to the requested value
	 * @return Property value. Object type is assumed to be [Integer | ... | Collection]
	 */
	public Object getModelPropertyValue(String path);


	/**
	 * Set a sub model property value
	 * 
	 * @param path Path to the requested value
	 * @param newValue Updated value
	 * @throws ServerException 
	 */
	public void setModelPropertyValue(String path, Object newValue) throws ServerException ;
	
	
	/**
	 * Create/insert a value in a collection
	 * 
	 * @param path Path to the collection
	 * @param newValue Inserted value
	 * @throws Exception 
	 */
	public void createValue(String path, Object addedValue) throws Exception;
	
	
	/**
	 * Delete a value from a collection
	 * 
	 * @param path Path to the collection
	 * @param deletedValue Collection value to delete
	 * @throws Exception 
	 */
	public void deleteValue(String path, Object deletedValue) throws Exception;
	

	/**
	 * Invoke an operation
	 *
	 * @param path Path to operation
	 * @param parameter Operation parameter
	 * @return Return value
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws ServerException 
	 * @throws Exception 
	 */
	public Object invokeOperation(String path, Object[] parameter) throws Exception;
	

	/**
	 * Get contained elements
	 * 
	 * Contained sub model elements are returned as Map of key/value pairs. Keys are Strings, values are either primitive values or
	 * ElementRef objects that contain a reference to a complex object instance.
	 * 
	 * @param path Path to sub model or property
	 * @return Collection of contained elements
	 */
	public Map<String, IElementReference> getContainedElements(String path);
}

