package org.eclipse.basyx.vab.backend.server.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.basyx.vab.backend.server.JSONProvider;
import org.eclipse.basyx.vab.core.IModelProvider;

/**
 * VAB provider class that enables access to an IModelProvider via HTTP REST
 * interface<br />
 * <br />
 * REST http interface is as following: <br />
 * - GET /aas/submodels/{subModelId} Retrieves submodel with ID {subModelId}
 * <br />
 * - GET /aas/submodels/{subModelId}/properties/a Retrieve property a of
 * submodel {subModelId}<br />
 * - GET /aas/submodels/{subModelId}/properties/a/b Retrieve property a/b of
 * submodel {subModelId} <br />
 * - POST /aas/submodels/{subModelId}/operations/a Invoke operation a of
 * submodel {subModelId}<br />
 * - POST /aas/submodels/{subModelId}/operations/a/b Invoke operation a/b of
 * submodel {subModelId}
 * 
 * @author kuhn
 *
 */
public class VABHTTPInterface<T extends IModelProvider> extends BasysHTTPServelet {

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
	public VABHTTPInterface(T provider) {
		// Store provider reference
		providerBackend = new JSONProvider<T>(provider);
	}

	/**
	 * Access model provider
	 */
	public T getModelProvider() {
		return providerBackend.getBackendReference();
	}

	/**
	 * Send JSON encoded response
	 */
	protected void sendJSONResponse(String path, PrintWriter outputStream, Object jsonValue) {
		// Output result
		outputStream.write(jsonValue.toString()); // FIXME throws nullpointer exception if jsonValue is null
		outputStream.flush();
	}

	/**
	 * Implement "Get" operation
	 * 
	 * Process HTTP get request - get sub model property value
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// Process HTTP request
		String uri = req.getRequestURI();
		String contextPath = req.getContextPath();
		String path        = uri.substring(contextPath.length() + req.getServletPath().length() + 1);
		
		// Add leading "/" to path if necessary
		if (!path.startsWith("/")) path = "/"+path;
		
		// Decode URL 
		path = java.net.URLDecoder.decode(path, "UTF-8");

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
		String uri = req.getRequestURI();
		String contextPath = req.getContextPath();
		String path = uri.substring(contextPath.length() + req.getServletPath().length() + 1);
		
		// Add leading "/" to path if necessary
		if (!path.startsWith("/")) path = "/"+path;
		
		// Decode URL 
		path = java.net.URLDecoder.decode(path, "UTF-8");
				
		// Read request body
		InputStreamReader reader = new InputStreamReader(req.getInputStream());
		BufferedReader bufReader = new BufferedReader(reader);
		StringBuilder serValue = new StringBuilder();
		while (bufReader.ready())
			serValue.append(bufReader.readLine());
		

		// System.out.println("Parameters: " + req.getParameterMap().size()); - seems like parameters are dropped sometimes or get consumed after first read => null

		// Set value of BaSys VAB element
		providerBackend.processBaSysSet(path, serValue.toString(), resp.getWriter());

	}

	/**
	 * <pre>
	 * Handle HTTP POST operation. Creates a new Property, Operation, Event,
	 * Submodel or AAS or invokes an operation.
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// Extract path
		String uri = req.getRequestURI();
		String contextPath = req.getContextPath();
		String path = uri.substring(contextPath.length() + req.getServletPath().length() + 1);
		
		// Add leading "/" to path if necessary
		if (!path.startsWith("/")) path = "/"+path;
		
		// Decode URL 
		path = java.net.URLDecoder.decode(path, "UTF-8");

		// Read posted parameter
		InputStreamReader reader = new InputStreamReader(req.getInputStream());
		BufferedReader bufReader = new BufferedReader(reader);
		StringBuilder serValue = new StringBuilder();
		while (bufReader.ready()) {
			serValue.append(bufReader.readLine());
		}

		// Setup HTML response header
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		
		providerBackend.processBaSysPost(path, serValue.toString(), resp.getWriter());
	}

	/**
	 * Handle a HTTP PATCH operation. Updates a map or collection respective to
	 * action string.
	 * 
	 */
	@Override
	protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Extract path
		String uri = req.getRequestURI();
		String contextPath = req.getContextPath();
		String path = uri.substring(contextPath.length() + req.getServletPath().length() + 1);
		
		// Add leading "/" to path if necessary
		if (!path.startsWith("/")) path = "/"+path;
		
		// Decode URL 
		path = java.net.URLDecoder.decode(path, "UTF-8");
		
		// Read request body
		InputStreamReader reader = new InputStreamReader(req.getInputStream());
		BufferedReader bufReader = new BufferedReader(reader);
		StringBuilder serValue = new StringBuilder();
		while (bufReader.ready())
			serValue.append(bufReader.readLine());
				
		providerBackend.processBaSysPatch(path, serValue.toString(), req.getParameter("action"), resp.getWriter());
	}

	/**
	 * Implement "Delete" operation. Deletes any resource under the given path.
	 */
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Extract path
		String uri = req.getRequestURI();
		String contextPath = req.getContextPath();
		String path = uri.substring(contextPath.length() + req.getServletPath().length() + 1);
		
		// Add leading "/" to path if necessary
		if (!path.startsWith("/")) path = "/"+path;
		
		// Decode URL 
		path = java.net.URLDecoder.decode(path, "UTF-8");
		
		// Read request body
		InputStreamReader reader = new InputStreamReader(req.getInputStream());
		BufferedReader bufReader = new BufferedReader(reader);
		StringBuilder serValue = new StringBuilder();
		while (bufReader.ready())
			serValue.append(bufReader.readLine());
		
		System.out.println("Delete0:" + path);

		providerBackend.processBaSysDelete(path, serValue.toString(), resp.getWriter());
	}
}
