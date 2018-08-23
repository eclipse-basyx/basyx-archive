package org.eclipse.basyx.aas.backend.connector.http;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.basyx.aas.api.exception.ServerException;
import org.eclipse.basyx.aas.backend.connector.IBasysConnector;
import org.eclipse.basyx.aas.backend.http.tools.JSONTools;
import org.glassfish.jersey.client.HttpUrlConnectorProvider;
import org.json.JSONException;
import org.json.JSONObject;




/**
 * HTTP connector class
 * 
 * @author kuhn, pschorn
 *
 */
public class HTTPConnector implements IBasysConnector {

	private static String ADD_ACTION 	= "add";
	private static String REMOVE_ACTION = "remove";
	
	
	/**
	 * Invoke a BaSys get operation via HTTP GET
	 * @param address the server address from the directory
	 * @param servicePath the URL suffix for the requested path
	 * @return the requested object
	 */
	public Object basysGet(String address, String servicePath) {
		
		return httpGet(address, servicePath);
		
	}

	
	/**
	 * FIXME remove this method?
	 * Invoke a BaSys get operation via HTTP 
	 * @param address the server address from the directory
	 * @param servicePath the URL suffix for the requested path
	 * @return the requested object, but not deserialized
	 */
	public JSONObject basysGetRaw(String address, String servicePath) {
		
		System.out.println("[HTTP BasysGet Raw] "+ address+ ": " + servicePath);
		
		// Invoke service call via web services
		Client client = ClientBuilder.newClient();

		// Build web service URL
		Builder request = buildRequest(client, address+servicePath);
		
		// Perform request, return response
		return new JSONObject(request.get(String.class)); 
	}
	
	/**
	 * Invokes BasysPut method via HTTP PUT. Overrides existing property, operation or event.
	 * @param address the server address from the directory
	 * @param servicePath the URL suffix for the requested property, operation or event
	 * @param newValue should be an IElement of type Property, Operation or Event
	 */
	@Override
	public void basysSet(String address, String servicePath, Object newValue) throws ServerException {
		
		httpPut(address, servicePath, newValue);
	}

	
	/**
	 * Invoke a BaSys Patch operation via HTTP PATCH.  Updates a map or collection
	 * @param address the server address from the directory
	 * @param servicePath the URL suffix for the requested property
	 * @param newValue (pair) that should be added
	 * @throws ServerException 
	 */
	public void basysSet(String address, String servicePath, Object... newValue) throws ServerException {
		
		httpPatch(address, servicePath, ADD_ACTION, newValue);
	}
	
	/**
	 * Invoke a BaSys Delete operation via HTTP PATCH.  Deletes an element from a map or collection by key
	 * @param address the server address from the directory
	 * @param servicePath the URL suffix for the requested property
	 * @param obj the key or index of the entry that should be deleted
	 * @throws ServerException 
	 */
	@Override
	public void basysDelete(String address, String servicePath, Object obj) throws ServerException {
		
		httpPatch(address, servicePath, REMOVE_ACTION, obj);
	}
	
	
	/**
	 * Invoke a Basys Post operation. Creates a new Property, Operation, Event, Submodel or AAS via HTTP POST
	 * @param address the server address from the directory
	 * @param servicePath the URL suffix for the requested property
	 * @param parameter 
	 * @throws ServerException 
	 */
	public Object basysCreate(String address, String servicePath, Object... parameter) throws ServerException {
		
		return httpPost(address, servicePath, parameter);
	}
	
	/**
	 * Invoke a BaSys invoke operation via HTTP. Implemented as HTTP POST.
	 * @throws ServerException 
	 */
	@Override
	public Object basysInvoke(String address, String servicePath, Object... newValue) throws ServerException {
		
		return httpPost(address, servicePath, newValue);
		
	 }
	
	/**
	 * Invoke basysDelete operation via HTTP DELETE.  Deletes any resource under the given path.
	 * @throws ServerException
	 */
	@Override
	public void basysDelete(String address, String servicePath) throws ServerException {
		
		httpDelete(address, servicePath);
	}
	
	/**
	 * Execute a web service, return JSON string
	 */
	protected Builder buildRequest(Client client, String wsURL) {
		// Called URL
		WebTarget resource = client.target(wsURL);
				
		// Build request, set JSON encoding
		Builder request = resource.request();		
		request.accept(MediaType.APPLICATION_JSON); 
		
		// Return JSON request
		return request;
	}
	
	
	/**
	 * Create web service path
	 */
	protected String createWSPath(String part1, String part2) {
		// Null pointer check
		if (part1 == null) return part2;
		if (part2 == null) return part1;
		
		// Return combined string
		if (part1.endsWith("/")) return part1+part2;
		
		return part1+"/"+part2;
	}
	
	/**
	 * Create servicepath depending on server technology
	 * @param qualifier refers to a qualifier "properties", "operations" or "events"
	 * @param path can be null if a type qualifier and submodel is specified
	 */
	public String buildPath(String aasID, String aasSubmodelID, String path, String qualifier) {
		String servicePath = aasID;
		if (aasSubmodelID!=null) {
			servicePath = servicePath + "/aas/submodels/"+aasSubmodelID;
			
			if (qualifier!=null) {
				servicePath = servicePath +  "/" + qualifier;
				
			}
			if (path!=null) {
				servicePath = servicePath + "/" + path;
			}
		} 
		
		return servicePath;
	}


	private Object httpGet(String address, String servicePath) {
		System.out.println("[HTTP Get] "+ address + servicePath);
		
		// Invoke service call via web services
		Client client = ClientBuilder.newClient();

		// Build web service URL
		Builder request = buildRequest(client, address+servicePath);
		
		// Perform request, return response
		String result = request.get(String.class); 
		
		// Deserialize and return property value
		Object res = JSONTools.Instance.deserialize(new JSONObject(result));
		
		return res;
	}
	
	
	private void httpPut(String address, String servicePath, Object newValue) throws ServerException {
		System.out.println("[HTTP Put] "+ address+ servicePath + "  "+ newValue);
		
		// Invoke service call via web services
		Client client = ClientBuilder.newClient();
		
		// Create JSON value Object 
		JSONObject jsonObject = JSONTools.Instance.serialize(newValue);
		
		// Build web service URL
		Builder request = buildRequest(client, address+servicePath);
		
		// Perform request
		Response rsp = request.put(Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON));
		
		// Try to extract response if any
		try {
			Object result = JSONTools.Instance.deserialize(new JSONObject(rsp.readEntity(String.class)));
			
			if (result instanceof ServerException) {
				
				// Throw server exception
				throw (ServerException) result;
			}
			
		}
		catch (JSONException e){
		    // If there is no return value or deserialization failed
			return;
		}
	}
	
	
	
	/**
	 * Implements HTTP Patch
	 * @param address
	 * @param servicePath
	 * @param action
	 * @param newValue
	 * @throws ServerException
	 */
	private void httpPatch(String address, String servicePath, String action, Object... newValue) throws ServerException {
		System.out.println("[HTTP Patch] "+ address+ servicePath + "  "+ newValue);
		
		// Invoke service call via web services
		Client client = ClientBuilder.newClient();
		
		// Create JSON value Object 
		JSONObject jsonObject = JSONTools.Instance.serialize(newValue);
		
		// Create and invoke HTTP PATCH request
		Response rsp = client.target(address + servicePath)
							.queryParam("action", action)
							.request()
							.build("PATCH", Entity.text(jsonObject.toString()))
			                .property(HttpUrlConnectorProvider.SET_METHOD_WORKAROUND, true)
			                .invoke();
		
		try {
			Object result = JSONTools.Instance.deserialize(new JSONObject(rsp.readEntity(String.class)));
			
			if (result instanceof ServerException) {
				throw (ServerException) result;
			}
		}
		catch (JSONException e){
		    // If there is no return value or deserialization failed
			return;
		}
	}
	
	private Object httpPost(String address, String servicePath, Object... parameter)  throws ServerException {
		
		System.out.println("[HTTP Post] "+ address+ servicePath + " " + parameter);
		
		// Invoke service call via web services
		Client client = ClientBuilder.newClient();

		// Create JSON value Object 
		JSONObject jsonObject = JSONTools.Instance.serialize(parameter);

		// Build web service URL
		Builder request = buildRequest(client, address+servicePath);
		
		// Perform request
		Response rsp = request.post(Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON));
		
		// Try to extract and return response if any
		try {
			Object result = JSONTools.Instance.deserialize(new JSONObject(rsp.readEntity(String.class)));
			
			if (result instanceof ServerException) {
				
				// Throw server exception
				throw (ServerException) result;
			} else {
				
				// Return result
				return result;
			}
			
		}
		catch (JSONException e){
		    // If there is no return value or deserialization failed
			return null;
		}
	}
	
	private void httpDelete(String address, String servicePath) throws ServerException {
		System.out.println("[HTTP Delete] "+ address+ servicePath);
		
		// Invoke service call via web services
		Client client = ClientBuilder.newClient();
		
		// Build web service URL
		Builder request = buildRequest(client, address+servicePath);
		
		// Perform request
		Response rsp = request.delete();
		
		// Try to extract response if any
		try {
			Object result = JSONTools.Instance.deserialize(new JSONObject(rsp.readEntity(String.class)));
			
			if (result instanceof ServerException) {
				
				// Throw server exception
				throw (ServerException) result;
			}
			
		}
		catch (JSONException e){
		    // If there is no return value or deserialization failed
			return;
		}
	}
	
}

