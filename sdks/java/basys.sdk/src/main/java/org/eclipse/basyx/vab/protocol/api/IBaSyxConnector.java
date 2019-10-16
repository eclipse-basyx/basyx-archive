package org.eclipse.basyx.vab.protocol.api;

/**
 * Connector interface for technology specific communication. Returns the
 * response including meta information
 * 
 * @author pschorn
 *
 */
public interface IBaSyxConnector {

	/**
	 * Get a sub model property value
	 * 
	 * @param path
	 *            Path to the requested value
	 * @return Property value. Object type is assumed to be [Integer | ... |
	 *         Collection]
	 */
	public String getModelPropertyValue(String path);

	/**
	 * Sets or overrides existing property, operation or event.
	 * 
	 * @param path
	 *            Path to the requested value
	 * @param newValue
	 *            Updated value
	 */
	public String setModelPropertyValue(String path, String newValue) throws Exception;

	/**
	 * Create a new property, operation, event submodel or aas under the given path
	 * 
	 * @param path
	 *            Path to the entity where the element should be created
	 * @param newEntity
	 *            new Element to be created on the server
	 */
	public String createValue(String path, String newEntity) throws Exception;

	/**
	 * Delete a property, operation, event, submodel or aas under the given path
	 * 
	 * @param path
	 *            Path to the entity that should be deleted
	 */
	public String deleteValue(String path) throws Exception;

	/**
	 * Deletes an entry from a map or collection by the given key
	 * 
	 * @param path
	 *            Path to the entity that should be deleted
	 */
	public String deleteValue(String path, String obj) throws Exception;

	/**
	 * Invoke an operation
	 *
	 * @param path
	 *            Path to operation
	 * @param jsonObject
	 *            Operation parameter
	 * @return Return value
	 */
	public String invokeOperation(String path, String jsonObject) throws Exception;
}
