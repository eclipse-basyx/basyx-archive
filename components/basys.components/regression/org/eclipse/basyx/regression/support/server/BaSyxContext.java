package org.eclipse.basyx.regression.support.server;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;



/**
 * BaSyx context that contains an Industrie 4.0 Servlet infrastructure 
 * 
 * @author kuhn
 *
 */
public class BaSyxContext extends HashMap<String, HttpServlet> {

	
	/**
	 * Version of serialized instance
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * Requested server context path
	 */
	protected String contextPath;
	
	
	/**
	 * Requested server documents base path
	 */
	protected String docBasePath;
	
	
	/**
	 * Requested Tomcat apache port
	 */
	protected int port;
	
	
	/**
	 * Servlet parameter
	 */
	protected Map<String, Map<String, String>> servletParameter = new HashMap<>();


	public Object AASHTTPServerResource;

	
	
	
	/**
	 * Constructor with default port
	 */
	public BaSyxContext(String reqContextPath, String reqDocBasePath) {
		// Invoke constructor
		this(reqContextPath, reqDocBasePath, 8080);
	}

	
	/**
	 * Constructor
	 */
	public BaSyxContext(String reqContextPath, String reqDocBasePath, int reqPort) {
		// Store context path and doc base path
		contextPath = reqContextPath;
		docBasePath = reqDocBasePath;
		port        = reqPort;
	}
	
	
	
	/**
	 * Add a servlet mapping
	 */
	public BaSyxContext addServletMapping(String key, HttpServlet servlet) {
		// Add servlet mapping
		put(key, servlet);

		// Return 'this' reference to enable chaining of operations
		return this;
	}
	

	/**
	 * Add a servlet mapping with parameter
	 */
	public BaSyxContext addServletMapping(String key, HttpServlet servlet, Map<String, String> servletParameter) {
		// Add servlet mapping
		addServletMapping(key, servlet);
		
		// Add Servlet parameter
		addServletParameter(key, servletParameter);

		// Return 'this' reference to enable chaining of operations
		return this;
	}

	
	/**
	 * Add servlet parameter
	 */
	public BaSyxContext addServletParameter(String key, Map<String, String> parameter) {
		// Add servlet parameter
		servletParameter.put(key, parameter);
		
		// Return 'this' reference to enable chaining of operations
		return this;
	}
	
	
	
	/**
	 * Get servlet parameter
	 */
	public Map<String, String> getServletParameter(String key) {
		// Return servlet parameter iff parameter map contains requested key
		if (servletParameter.containsKey(key)) return servletParameter.get(key);
		
		// Return empty map
		return new HashMap<String, String>();
	}
	
	
	/**
	 * Return Tomcat server port
	 */
	public int getPort() {
		return port;
	}
}

