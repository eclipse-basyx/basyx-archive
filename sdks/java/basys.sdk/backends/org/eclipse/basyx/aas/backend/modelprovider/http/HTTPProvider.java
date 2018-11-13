package org.eclipse.basyx.aas.backend.modelprovider.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.basyx.aas.api.services.IModelProvider;
import org.eclipse.basyx.aas.backend.modelprovider.JSONProvider;





/**
 * Provider class that enables access to an IModelProvider via HTTP REST interface 
 * 
 * @author kuhn
 *
 */
public class HTTPProvider<T extends IModelProvider> extends BasysHTTPServelet {

	
	/**
	 * Version information to identify the version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * Reference to IModelProvider backend
	 */
	protected JSONProvider<T> providerBackend = null;
	
	
	
	
	
	/**
	 * Constructor
	 */
	public HTTPProvider(T modelProviderBackend) {
		// Store reference to backend
		providerBackend = new JSONProvider<T>(modelProviderBackend);
	}
	
	
	/**
	 * Get backend reference
	 */
	public T getBackendReference() {
		return providerBackend.getBackendReference();
	}
	
	
	
	
	/**
	 * Implement "Get" operation 
	 * 
	 * Process HTTP get request - get sub model property value
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// process request depending on the path
		String uri 			= req.getRequestURI();
		String contextPath  = req.getContextPath();
		String path 		= uri.substring(contextPath.length()+1); // plus 1 for "/"

		// Setup HTML response header
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");

		// Process get request
		providerBackend.processBaSysGet(path, resp.getWriter());
	}

	
	
	/**
	 * Implement "Set" operation
	 */
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Extract path
		String uri 			= req.getRequestURI();
		String contextPath  = req.getContextPath();
		String path 		= uri.substring(contextPath.length()+1); // plus 1 for /
				
	 	// Read request body
		InputStreamReader reader    = new InputStreamReader(req.getInputStream());
		BufferedReader    bufReader = new BufferedReader(reader);
		StringBuilder     serValue  = new StringBuilder(); 
		while (bufReader.ready()) serValue.append(bufReader.readLine());
		
		// Change property value
		providerBackend.processBaSysSet(path, serValue.toString(), resp.getWriter());
		
	}


	
	/**
	 * <pre>
	 * Handle HTTP POST operation. Creates a new Property, Operation, Event, Submodel or AAS or invokes an operation.
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// Extract path
		String uri 			= req.getRequestURI();
		String contextPath  = req.getContextPath();
		String path 		= uri.substring(contextPath.length()+1); // plus 1 for /
				
		// Read posted parameter
		InputStreamReader reader    = new InputStreamReader(req.getInputStream());
		BufferedReader    bufReader = new BufferedReader(reader);
		StringBuilder     serValue  = new StringBuilder(); 
		while (bufReader.ready()) {serValue.append(bufReader.readLine());}
		
		this.providerBackend.processBaSysPost(path, serValue.toString(), resp.getWriter());
	}


	 /**
	  * Handle a HTTP PATCH operation. Updates a map or collection respective to action string.
	  * 
	  */
	 protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	 
	 	// Extract path
		String uri 			= req.getRequestURI();
		String contextPath  = req.getContextPath();
		String path 		= uri.substring(contextPath.length()+1); // plus 1 for /
				
	 	// Read request body
		InputStreamReader reader    = new InputStreamReader(req.getInputStream());
		BufferedReader    bufReader = new BufferedReader(reader);
		StringBuilder     serValue  = new StringBuilder(); 
		while (bufReader.ready()) serValue.append(bufReader.readLine());
		
		// Extract action parameter
		String action = req.getParameter("action");
		Map<String, String[]> params = req.getParameterMap();
		
		// Process patch request
		this.providerBackend.processBaSysPatch(path, serValue.toString(), action, resp.getWriter());
	 }
	 
	 
	 /**
	 * Implement "Delete" operation.  Deletes any resource under the given path.
	 */
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// Extract path
		String uri 			= req.getRequestURI();
		String contextPath  = req.getContextPath();
		String path 		= uri.substring(contextPath.length()+1); // plus 1 for /
		
		// Read request body
		InputStreamReader reader    = new InputStreamReader(req.getInputStream());
		BufferedReader    bufReader = new BufferedReader(reader);
		StringBuilder     serValue  = new StringBuilder(); 
		while (bufReader.ready()) serValue.append(bufReader.readLine());
		
		this.providerBackend.processBaSysDelete(path, resp.getWriter());
	}
}

