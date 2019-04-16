package org.eclipse.basyx.vab.backend.server.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.basyx.vab.backend.server.BaSysCommunicationInterface;
import org.eclipse.basyx.vab.backend.server.utils.JSONProvider;
import org.eclipse.basyx.vab.core.IModelProvider;
import org.eclipse.basyx.vab.core.tools.VABPathTools;


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
public class VABHTTPInterface<ModelProvider extends IModelProvider> extends BasysHTTPServlet implements BaSysCommunicationInterface {

	
	/**
	 * Version information to identify the version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * Reference to IModelProvider backend
	 */
	protected JSONProvider<ModelProvider> providerBackend = null;

	
	
	/**
	 * Constructor
	 */
	public VABHTTPInterface(ModelProvider provider) {
		// Store provider reference
		providerBackend = new JSONProvider<ModelProvider>(provider);
	}

	
	/**
	 * Access model provider
	 */
	public ModelProvider getModelProvider() {
		return providerBackend.getBackendReference();
	}

	
	/**
	 * Get JSON Provider from backend
	 */
	@Override
	public JSONProvider<ModelProvider> getProviderBackend() {
		return providerBackend;
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
		String path = extractPath(req);

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
		System.out.println("Put:");
		
		String path = extractPath(req);
		String serValue = extractSerializedValue(req);
		System.out.println("DoPut:"+serValue);
		
		providerBackend.processBaSysSet(path, serValue.toString(), resp.getWriter());
	}

	
	/**
	 * <pre>
	 * Handle HTTP POST operation. Creates a new Property, Operation, Event,
	 * Submodel or AAS or invokes an operation.
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("Post:");
		
		String path = extractPath(req);
		String serValue = extractSerializedValue(req);
		
		// Setup HTML response header
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");

		// Check if request is for property creation or operation invoke
		if (VABPathTools.isOperationPath(path)) {
			// Invoke BaSys VAB 'invoke' primitive
			providerBackend.processBaSysInvoke(path, serValue, resp.getWriter());
		} else {
			// Invoke the BaSys 'create' primitive
			providerBackend.processBaSysCreate(path, serValue, resp.getWriter());
		}
	}

	
	/**
	 * Handle a HTTP PATCH operation. Updates a map or collection
	 * 
	 */
	@Override
	protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("Patch:");
		
		String path = extractPath(req);
		String serValue = extractSerializedValue(req);
		providerBackend.processBaSysDelete(path, serValue, resp.getWriter());
	}

	
	/**
	 * Implement "Delete" operation. Deletes any resource under the given path.
	 */
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = extractPath(req);

		// No parameter to read! Provide serialized null
		String nullParam = "{\"basystype\":\"null\"}";

		providerBackend.processBaSysDelete(path, nullParam, resp.getWriter());
	}

	
	private String extractPath(HttpServletRequest req) throws UnsupportedEncodingException {
		// Extract path
		String uri = req.getRequestURI();
		String contextPath = req.getContextPath();
		String path = uri.substring(contextPath.length() + req.getServletPath().length() + 1);

		// Add leading "/" to path if necessary
		if (!path.startsWith("/")) {
			path = "/" + path;
		}

		// Decode URL
		path = java.net.URLDecoder.decode(path, "UTF-8");

		return path;
	}


	/**
	 * Read serialized value 
	 * @param req
	 * @return
	 * @throws IOException
	 */
	private String extractSerializedValue(HttpServletRequest req) throws IOException {
		// Read request body
		ServletInputStream is = req.getInputStream();		
		StringBuilder serValue = new StringBuilder();

		// This seems kind of slow...
		while (!is.isFinished()) {
			serValue.append(String.valueOf((char) (byte) is.read()));
		}
		return serValue.toString();
	}
}
