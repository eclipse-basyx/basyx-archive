package org.eclipse.basyx.aas.backend.connector;

import org.eclipse.basyx.aas.api.exception.ServerException;
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
	 * Invoke a Basys Put operation. Overrides existing property, operation or event.
	 * @throws ServerException 
	 */
	public void basysPut(String address, String servicePath, Object newValue) throws ServerException;
	
	/**
	 * Invokes a Basys Update operation. Adds an entry to a map or collection
	 * @throws ServerException
	 */
	public void basysUpdate(String address, String servicePath, Object... parameters) throws ServerException;
	
	/**
	 * Invoke a Basys Post operation. Creates a new Property, Operation, Event, Submodel or AAS
	 * @throws ServerException 
	 */
	public Object basysPost(String address, String servicePath, Object... parameters) throws ServerException;
	
	/**
	 * Invoke a Basys Invoke operation. Invokes an operation on the server.
	 * @throws ServerException 
	 */
	public Object basysInvoke(String address, String servicePath, Object... parameters) throws ServerException;
	
	/**
	 * Invoke a Basys operation. Deletes any resource under the given path.
	 * @throws Exception 
	 */
	public void basysDelete(String address, String servicePath) throws ServerException;
	
	/**
	 * Invoke a Basys operation. Deletes an entry from a map or collection by the given key or index
	 * @throws Exception 
	 */
	public void basysDelete(String address, String servicePath, Object obj) throws ServerException;
	
	/**
	 * Assemble request path connector specific
	 * @return 
	 */
	public String buildPath(String aasId, String aasSubmodelID, String path, String type) ;
}
