package org.eclipse.basyx.aas.backend.connector.http;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

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

	
	/**as
	 * Invoke a BaSys set operation via HTTP PATCH
	 * @throws ServerException 
	 */
	public void basysSet(String address, String servicePath, Object newValue) throws ServerException {
		
		System.out.println("[HTTP BasysSet] "+ address+ servicePath + "  "+ newValue);
		
		
		// Invoke service call via web services
		Client client = ClientBuilder.newClient();
		
		// Create JSON value Object 
		JSONObject jsonObject = JSONTools.Instance.serialize(newValue);
		
		// Create and invoke HTTP PATCH request
		Response rsp = client.target(address + servicePath)
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
	public Object basysPost(String address, String servicePath, String action, Object... newValue) throws ServerException {
		
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
	 * Invoke a BaSys invoke operation via HTTP
	 * @throws Exception 
	 */
	public Object basysInvoke(String address, String servicePath, Object... newValue) throws ServerException {
		
		return basysPost(address, servicePath, "invoke", newValue);
		
	 }




	/**
	 * Execute a web service, return JSON string
	 */
	protected Builder buildRequest(Client client, String wsURL, String... parameter) {
		// Called URL
		WebTarget resource = client.target(wsURL);
				
		// Add call parameter
		for (int i=0; i<parameter.length; i+=2) {
			resource = resource.queryParam(parameter[i], parameter[i+1]);
		}

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

