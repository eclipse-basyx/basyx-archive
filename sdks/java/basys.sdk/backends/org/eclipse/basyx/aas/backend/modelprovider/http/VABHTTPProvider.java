package org.eclipse.basyx.aas.backend.modelprovider.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.basyx.aas.api.exception.ServerException;
import org.eclipse.basyx.aas.api.services.IModelProvider;
import org.eclipse.basyx.aas.backend.http.tools.JSONTools;
import org.eclipse.basyx.aas.backend.modelprovider.SimpleJSONProvider;
import org.json.JSONException;
import org.json.JSONObject;




/**
 * VAB provider class that enables access to an IModelProvider via HTTP REST interface 
 * 
 * @author kuhn
 *
 */
public class VABHTTPProvider<T extends IModelProvider> extends BasysHTTPServelet {

	
	/**
	 * Version information to identify the version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * Reference to IModelProvider backend
	 * 
	 * FIXME: Create generic interface class for providers
	 */
	protected SimpleJSONProvider<T> providerBackend = null;
	
	
	
	
	
	/**
	 * Constructor
	 */
	public VABHTTPProvider(T modelProviderBackend) {
		// Store reference to backend
		providerBackend = new SimpleJSONProvider<T>(modelProviderBackend);
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
		String path 		= uri.substring(contextPath.length()+req.getServletPath().length()+1); // plus 1 for "/"
		
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
		String path 		= uri.substring(contextPath.length()+req.getServletPath().length()+1); // plus 1 for "/"
				
	 	// Read request body
		InputStreamReader reader    = new InputStreamReader(req.getInputStream());
		BufferedReader    bufReader = new BufferedReader(reader);
		StringBuilder     serValue  = new StringBuilder(); 
		while (bufReader.ready()) serValue.append(bufReader.readLine());
		
		// Change property value
		providerBackend.processBaSysSet(path, serValue.toString(), resp.getWriter());		
	}

	
	
	/**
	 * Check if the path to an VAB elements leads to an operation. In this case, the
	 * element just before the operation ID is /operations/ 
	 */
	protected boolean isOperationPath(String path) {
		// Check if path contains /operations as last element before actual operation name
		try {
			// Perform check and return true if operations is found at beginning
			if ((path.lastIndexOf("/")-10 == 0) && (path.substring(0, path.lastIndexOf("/")).equals("operations"))) return true;
			
			// Perform check and return true if /operations is found at correct position
			if (path.substring(path.lastIndexOf("/")-11, path.lastIndexOf("/")).equals("/operations")) return true;
		} catch (StringIndexOutOfBoundsException e) {
			// Do nothing - the path just does not lead to an operation
		}
		
		// Path does not lead to operation
		return false;
	}

	
	/**
	 * Extracts parameter from JSON and handles de-serialization errors
	 * @param path
	 * @param serializedJSONValue
	 * @param outputStream
	 * @return
	 * @throws ServerException
	 */
	private Object extractParameter(String path, String serializedJSONValue, PrintWriter outputStream) {
		// Return value
		Object result = null;
		
		// Deserialize json body
		try {
			JSONObject json = new JSONObject(serializedJSONValue.toString()); 
			result = JSONTools.Instance.deserialize(json); 
			
		} catch (JSONException e)   {
			sendException(new IllegalArgumentException("Invalid PUT paramater"), path, outputStream);
		}
		return result;
	}

	
	/**
	 * Send JSON encoded response
	 */
	private void sendJSONResponse(String path, PrintWriter outputStream, JSONObject jsonValue) {
		// Output result
		outputStream.write(jsonValue.toString()); //FIXME throws nullpointer exception if jsonValue is null
		outputStream.flush();
	}

	
	/**
	 * Send Error
	 * @param e
	 * @param path
	 * @param resp
	 */
	private void sendException(Exception e, String path, PrintWriter resp){
		System.out.println("Sending exception...");
		JSONObject error = JSONTools.Instance.serialize(e);
		
		// Send error response
		sendJSONResponse(path, resp, error);
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
		String path 		= uri.substring(contextPath.length()+req.getServletPath().length()+1); // plus 1 for /
				
		// Read posted parameter
		InputStreamReader reader    = new InputStreamReader(req.getInputStream());
		BufferedReader    bufReader = new BufferedReader(reader);
		StringBuilder     serValue  = new StringBuilder(); 
		while (bufReader.ready()) {serValue.append(bufReader.readLine());}
		
		// Check if request is for property creation or operation invoke
		if (isOperationPath(path)) {
			// Deserialize json body.  If parameter is null, an exception has been sent
			Object parameter = extractParameter(path, serValue.toString(), resp.getWriter()); 
	
			// Invoke BaSys VAB 'invoke' primitive
			this.providerBackend.processBaSysInvoke(path, (Object[]) parameter, resp.getWriter());
		} else {
			// Invoke the BaSys create primitive
			this.providerBackend.processBaSysCreate(path, serValue.toString(), resp.getWriter());
		}
	}


	 /**
	  * Handle a HTTP PATCH operation. Updates a map or collection respective to action string.
	  * 
	  */
	 protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	 
	 	// Extract path
		String uri 			= req.getRequestURI();
		String contextPath  = req.getContextPath();
		String path 		= uri.substring(contextPath.length()+req.getServletPath().length()+1); // plus 1 for /
				
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
		String path 		= uri.substring(contextPath.length()+req.getServletPath().length()+1); // plus 1 for /
		
		// Read request body
		InputStreamReader reader    = new InputStreamReader(req.getInputStream());
		BufferedReader    bufReader = new BufferedReader(reader);
		StringBuilder     serValue  = new StringBuilder(); 
		while (bufReader.ready()) serValue.append(bufReader.readLine());
		
		// Process delete request
		this.providerBackend.processBaSysDelete(path, resp.getWriter());
	}
}

