package org.eclipse.basyx.regression.support.tools;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.MediaType;



/**
 * Helper class that supports invocation of remote web services
 * 
 * @author kuhn
 *
 */
public class InvokeWebService {

	
	/**
	 * Execute a web service, return JSON string
	 */
	public static String invokeWebService(Client client, String wsURL, String... parameter) {
		// Called URL
		WebTarget resource = client.target(wsURL);
				
		// Add call parameter
		for (int i=0; i<parameter.length; i+=2) {
			resource = resource.queryParam(parameter[i], parameter[i+1]);
		}

		// Build request, set JSON encoding
		Builder request = resource.request();
		request.accept(MediaType.APPLICATION_JSON);

		// Perform request, return response
		return request.get(String.class);
	}
}
