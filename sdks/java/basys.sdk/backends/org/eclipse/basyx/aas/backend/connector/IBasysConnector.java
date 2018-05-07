package org.eclipse.basyx.aas.backend.connector;

import org.json.JSONObject;

public interface IBasysConnector {

	
	/**
	 * Invoke a BaSys get operation. Retrieves the AAS, Submodel, Property, Operation or value at the given path.
	 */
	public Object basysGet(String address, String servicePath);
	
	
	/**
	 * Invoke a BaSys get operation without de-serialization
	 */
	public JSONObject basysGetRaw(String address, String servicePath);
	
	
	/**
	 * Invoke a BaSys set operation. Sets the property at the given path to the specified value.
	 */
	public void basysSet(String address, String servicePath, Object newValue);
	
	/**
	 * Invoke a BaSys post operation. This function has three different semantics depending on the "action" parameter
	 * 1) To invoke an operation with the given parameters
	 * 2) To create a value in a collection OR hash map
	 * 3) To delete a value in a collection OR hash map
	 * @param action Valid arguments are "invoke", "create" or "delete".
	 */
	public Object basysPost(String address, String servicePath, String action, Object... parameters);
	
	/**
	 * Invoke a BaSys invoke operation. This function can be superseded by basysPost using "invoke" as action parameter
	 */
	public Object basysInvoke(String address, String servicePath, Object... parameters);
}
