package org.eclipse.basyx.tools.aas.active;

import java.io.Serializable;
import java.util.function.Supplier;

import org.eclipse.basyx.tools.webserviceclient.WebServiceRawClient;





/**
 * Implement a getter function that queries the value from a HTTP server. Expects a string response.
 * 
 * @author kuhn
 *
 */
public class HTTPGetter implements Supplier<Object>, Serializable {

	
	/**
	 * Version number of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * URL of server that provides the requested information
	 */
	protected String serverURL = null;
	
	

	/**
	 * Constructor
	 */
	public HTTPGetter(String url) {
		// Store URL
		serverURL = url;
	}
	
	
	
	/**
	 * Return value
	 */
	@Override
	public Object get() {
		// Create web service client
		WebServiceRawClient rawClient = new WebServiceRawClient();
		
		// Delegate call to WebService RAW client
		return rawClient.get(serverURL);
	}
}

