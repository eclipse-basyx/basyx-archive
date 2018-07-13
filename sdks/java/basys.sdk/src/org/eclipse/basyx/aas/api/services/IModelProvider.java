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
	 * Get a property value
	 * 
	 * @param path Path to the requested value
	 * @return Property value. Object type is assumed to be [Integer | ... | Collection]
	 */
	public Object getModelPropertyValue(String path);


	/**
	 * Set a property value
	 * 
	 * @param path Path to the requested value
	 * @param newValue Updated value
	 * @throws ServerException 
	 * @throws Exception 
	 */
	public void setModelPropertyValue(String path, Object newValue) throws Exception ;
	
	
	/**
	 * Create a new property, operation, evnet submodel or aas under the given path
	 * 
	 * @param path Path to the collection
	 * @param newValue Inserted value
	 * @throws Exception 
	 */
	public void createValue(String path, Object addedValue) throws Exception;
	

	/**
	 * Delete a property, operation, event, submodel or aas under the given path
	 * 
	 * @param path Path to the collection
	 * @param deletedValue Collection value to delete
	 * @throws Exception 
	 */
	public void deleteValue(String path) throws Exception;

	/**
	 * Invoke an operation with the specified paramter
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
	
	
	/**
	 * Adds an entry to a collection or map 
	 * @param path
	 * @param parameter For collections, parameter is expected to be a single element that will be added to
	 * 					For maps, parameter is expected to be a key-value pair that will be added to the map
	 * @throws Exception
	 */
	public void setContainedValue(String path, Object[] parameter) throws Exception;
	
	/**
	 * Removes an entry from a collection or map 
	 * @param path
	 * @param parameter For collections, parameter is the exact object that shall be deleted from the collection
	 * 					For maps, parameter is a key for a key-value pair that shall be removed from the map	
	 * @throws Exception
	 */
	public void deleteContainedValue(String path, Object[] parameter) throws Exception;
}

