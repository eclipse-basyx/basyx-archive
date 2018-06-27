package org.eclipse.basyx.aas.backend.connector.http;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.eclipse.basyx.aas.backend.connector.IBasysConnector;
import org.eclipse.basyx.aas.backend.http.tools.JSONTools;
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
		
		System.out.println("[HTTP BasysGet] "+ address+ ": " + servicePath);
		
		// Invoke service call via web services
		Client client = ClientBuilder.newClient();

		// Build web service URL
		Builder request = buildRequest(client, address, "path", servicePath);
		
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
		Builder request = buildRequest(client, address, "path", servicePath);
		
		// Perform request, return response
		return new JSONObject(request.get(String.class)); 
	}

	
	/**as
	 * Invoke a BaSys set operation via HTTP
	 */
	public void basysSet(String address, String servicePath, Object newValue) {
		
		System.out.println("[HTTP BasysSet] "+ address+ ": " + servicePath);
		
		// Invoke service call via web services
		Client client = ClientBuilder.newClient();

		// Create JSON value Object 
		JSONObject jsonObject = JSONTools.Instance.serialize(newValue);

		// Build web service URL
		Builder request = buildRequest(client, address, "path", servicePath);
		
		// Perform request
		request.put(Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON));
		
	}
	
	
	/**
	 * Invoke a BaSys post operation via HTTP
	 * @param action may be "invoke", "create" or "delete", "createProperty"
	 */
	public Object basysPost(String address, String servicePath, String action, Object... newValue) {
		
		System.out.println("[HTTP BasysPost] "+ address+ ": " + servicePath);
		
		// Invoke service call via web services
		Client client = ClientBuilder.newClient();

		// Create JSON value Object 
		JSONObject jsonObject = JSONTools.Instance.serialize(newValue);

		// Build web service URL
		Builder request = buildRequest(client, address, "path", servicePath, "op", action);
		
		// Perform request
		Response rsp = request.post(Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON));
		
		// Try to extract and return response if any
		
		try {
			return JSONTools.Instance.deserialize(new JSONObject(rsp.readEntity(String.class)));
		}
		catch (JSONException e){
		    
			return null;
		}
	}
	
	

	
	/**
	 * Invoke a BaSys invoke operation via HTTP
	 */
	public Object basysInvoke(String address, String servicePath, Object... newValue) {
		
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
	 */
	public String buildPath(String aasID, String aasSubmodelID, String path) {
		String servicePath = aasID;
		if (aasSubmodelID!=null) {
			servicePath = aasSubmodelID + "." + servicePath;
			
			if (path!=null) {
				servicePath = servicePath +  "/" + path;
			}
		}
		
		return servicePath;
	}
	
	
}

