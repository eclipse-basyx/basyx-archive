package org.eclipse.basyx.regression.support.server;

import java.util.HashMap;

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
	public void addServletMapping(String key, HttpServlet servlet) {
		put(key, servlet);
	}
}
