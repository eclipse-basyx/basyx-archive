package org.eclipse.basyx.vab.core;

import java.util.Map;

import org.eclipse.basyx.aas.api.reference.IElementReference;

/**
 * Basic model provider backend interface
 * 
 * @author kuhn, pschorn
 *
 */
public interface IModelProvider {

	/**
	 * Get a sub model property value
	 * 
	 * @param path
	 *            Path to the requested value
	 * @return Property value. Object type is assumed to be [Integer | ... |
	 *         Collection]
	 */
	public Object getModelPropertyValue(String path);

	/**
	 * Sets or overrides existing property, operation or event.
	 * 
	 * @param path
	 *            Path to the requested value
	 * @param newValue
	 *            Updated value
	 */
	public void setModelPropertyValue(String path, Object newValue) throws Exception;

	/**
	 * Create a new property, operation, event submodel or aas under the given path
	 * 
	 * @param path
	 *            Path to the entity where the element should be created
	 * @param newEntity
	 *            new Element to be created on the server
	 */
	public void createValue(String path, Object newEntity) throws Exception;

	/**
	 * Delete a property, operation, event, submodel or aas under the given path
	 * 
	 * @param path
	 *            Path to the entity that should be deleted
	 */
	public void deleteValue(String path) throws Exception;

	/**
	 * Deletes an entry from a map or collection by the given key
	 * 
	 * @param path
	 *            Path to the entity that should be deleted
	 */
	public void deleteValue(String path, Object obj) throws Exception;

	/**
	 * Invoke an operation
	 *
	 * @param path
	 *            Path to operation
	 * @param parameter
	 *            Operation parameter
	 * @return Return value
	 */
	public Object invokeOperation(String path, Object[] parameter) throws Exception;

	/**
	 * Get contained elements
	 * 
	 * Contained sub model elements are returned as Map of key/value pairs. Keys are
	 * Strings, values are either primitive values or ElementRef objects that
	 * contain a reference to a complex object instance.
	 * 
	 * @param path
	 *            Path to sub model or property
	 * @return Collection of contained elements
	 */
	public Map<String, IElementReference> getContainedElements(String path);
}
