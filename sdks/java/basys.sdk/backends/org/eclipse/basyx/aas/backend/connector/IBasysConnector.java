package org.eclipse.basyx.aas.backend.connector;

import org.eclipse.basyx.aas.api.exception.ServerException;
import org.json.JSONObject;

public interface IBasysConnector {
	
	
	/**
	 * Invoke a BaSys get operation. Retrieves the AAS, Submodel, Property, Operation or value at the given path.
	 * @return the de-serialized ElementRef
	 */
	public Object basysGet(String address, String servicePath);
	
	/**
	 * Invoke a BaSys get operation without de-serialization
	 * @return the serialized element as a JSONObject
	 */
	public JSONObject basysGetRaw(String address, String servicePath);
	
	/**
	 * Invoke a Basys Set operation. Sets or overrides existing property, operation or event.
	 * @throws ServerException that carries the Exceptions thrown on the server
	 */
	public void basysSet(String address, String servicePath, Object newValue) throws ServerException;
	
	/**
	 * Invokes a Basys Set operation. Adds an entry to a map or collection
	 * @throws ServerException that carries the Exceptions thrown on the server
	 */
	public void basysSet(String address, String servicePath, Object... parameters) throws ServerException;
	
	/**
	 * Invoke a Basys Post operation. Creates a new Property, Operation, Event, Submodel or AAS
	 * @throws ServerException that carries the Exceptions thrown on the server
	 */
	public Object basysCreate(String address, String servicePath, Object... parameters) throws ServerException;
	
	/**
	 * Invoke a Basys Invoke operation. Invokes an operation on the server.
	 * @throws ServerException that carries the Exceptions thrown on the server
	 */
	public Object basysInvoke(String address, String servicePath, Object... parameters) throws ServerException;
	
	/**
	 * Invoke a Basys operation. Deletes any resource under the given path.
	 * @throws ServerException that carries the Exceptions thrown on the server
	 */
	public void basysDelete(String address, String servicePath) throws ServerException;
	
	/**
	 * Invoke a Basys oxperation. Deletes an entry from a map or collection by the given key or index
	 * @throws ServerException that carries the Exceptions thrown on the server
	 */
	public void basysDelete(String address, String servicePath, Object obj) throws ServerException;
	
	/**
	 * Assemble request path connector specific (without address here)
	 * @return the path which the server can process
	 */
	public String buildPath(String aasId, String aasSubmodelID, String path, String type) ;
}
