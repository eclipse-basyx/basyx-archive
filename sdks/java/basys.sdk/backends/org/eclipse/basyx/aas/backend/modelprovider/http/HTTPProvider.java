package org.eclipse.basyx.aas.backend.modelprovider.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
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
public class HTTPProvider<T extends IModelProvider> extends HttpServlet {

	
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
				
		System.out.println("-------------------------- DO GET " + path + "---------------------------------------------------------");

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
		// Read posted parameter
		InputStreamReader reader    = new InputStreamReader(req.getInputStream());
		BufferedReader    bufReader = new BufferedReader(reader);
		StringBuilder     serValue  = new StringBuilder(); 
		
		// Read values
		while (bufReader.ready()) serValue.append(bufReader.readLine());
		
		// Access parameters from request header
		String path       = (String) req.getParameter("path"); 
		
		// Change property value
		providerBackend.processBaSysSet(path, serValue.toString());
	}


	
	/**
	 * Implement "Create" and "Invoke" operations
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Read posted parameter
		InputStreamReader reader    = new InputStreamReader(req.getInputStream());
		BufferedReader    bufReader = new BufferedReader(reader);
		StringBuilder     serValue  = new StringBuilder(); 
		// Read values
		while (bufReader.ready()) serValue.append(bufReader.readLine());
		
		// Access parameters from request header
		String path       = (String) req.getParameter("path"); 
		String operation  = (String) req.getParameter("op"); 
		

		// Perform operation
		switch (operation) {
			case "create": 
				// Process create request
				providerBackend.processBaSysCreate(path, serValue.toString());
				break;
				
			case "delete": 
				// Process delete request
				providerBackend.processBaSysDelete(path, serValue.toString());
				break;
			
			case "invoke": {
				// Setup HTML response header
				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");

				// Process invoke request
				providerBackend.processBaSysInvoke(path, serValue.toString(), resp.getWriter());
				break;
			}
			
			default:
				throw new IllegalArgumentException("Action not supported.");
		}
	}
}

