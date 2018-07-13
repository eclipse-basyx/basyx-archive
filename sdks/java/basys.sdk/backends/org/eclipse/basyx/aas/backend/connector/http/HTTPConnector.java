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
 * @author kuhn
 *
 */
public class HTTPConnector implements IBasysConnector {

	private static String ADD_ACTION 	= "add";
	private static String REMOVE_ACTION = "remove";
	
	/**
	 * Invoke a BaSys get operation via HTTP
	 */
	public Object basysGet(String address, String servicePath) {
		
		System.out.println("[HTTP BasysGet] "+ address + servicePath);
		
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

	
	/**
	 * Invoke a BaSys get operation via HTTP
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
	 */
	@Override
	public void basysPut(String address, String servicePath, Object newValue) throws ServerException {
		System.out.println("[HTTP BasysPut] "+ address+ servicePath + "  "+ newValue);
		
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
	 * Invoke a BaSys Patch operation via HTTP PATCH.  Updates a map or collection
	 * @throws ServerException 
	 */
	public void basysUpdate(String address, String servicePath, Object... newValue) throws ServerException {
		
		httpPatch(address, servicePath, ADD_ACTION, newValue);
	}
	
	/**
	 * Invoke a BaSys Delete operation via HTTP PATCH.  Deletes an element from a map or collection
	 * @throws ServerException 
	 */
	@Override
	public void basysDelete(String address, String servicePath, Object obj) throws ServerException {
		
		httpPatch(address, servicePath, REMOVE_ACTION, obj);
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
		System.out.println("[HTTP BasysPatch] "+ address+ servicePath + "  "+ newValue);
		
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
	
	
	/**
	 * Invoke a BaSys post operation via HTTP POST
	 * @throws Exception 
	 */
	public Object basysPost(String address, String servicePath, Object... newValue) throws ServerException {
		
		System.out.println("[HTTP BasysPost] "+ address+ servicePath + " " + newValue);
		
		// Invoke service call via web services
		Client client = ClientBuilder.newClient();

		// Create JSON value Object 
		JSONObject jsonObject = JSONTools.Instance.serialize(newValue);

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
	
	/**
	 * Invoke a BaSys invoke operation via HTTP. Implemented as HTTP POST.
	 * @throws Exception 
	 */
	@Override
	public Object basysInvoke(String address, String servicePath, Object... newValue) throws ServerException {
		
		return basysPost(address, servicePath, newValue);
		
	 }
	
	/**
	 * Invoke basysDelete operation via HTTP DELETE.  Deletes any resource under the given path.
	 */
	@Override
	public void basysDelete(String address, String servicePath) throws ServerException {
		System.out.println("[HTTP BasysDelete] "+ address+ servicePath);
		
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
	 * @param type refers to properties, operations or events
	 * @param path can be null if a type qualifier and submodel is specified
	 */
	public String buildPath(String aasID, String aasSubmodelID, String path, String type) {
		String servicePath = aasID;
		if (aasSubmodelID!=null) {
			servicePath = servicePath + "/submodels/"+aasSubmodelID;
			
			if (type!=null) {
				servicePath = servicePath +  "/" + type;
				
			}
			if (path!=null) {
				servicePath = servicePath + "/" + path;
			}
		}
		
		return servicePath;
	}


}

