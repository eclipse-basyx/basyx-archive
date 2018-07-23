package org.eclipse.basyx.aas.backend.modelprovider.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.basyx.aas.api.exception.ReadOnlyException;
import org.eclipse.basyx.aas.api.exception.ServerException;
import org.eclipse.basyx.aas.api.services.IModelProvider;
import org.eclipse.basyx.aas.backend.http.tools.JSONTools;
import org.eclipse.basyx.aas.impl.tools.BaSysID;
import org.json.JSONException;
import org.json.JSONObject;



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
	protected T providerBackend = null;
	
	
	/**
	 * Constructor
	 */
	public HTTPProvider(T modelProviderBackend) {
		// Store reference to backend
		providerBackend = modelProviderBackend;
	}
	
	
	/**
	 * Get backend reference
	 */
	public T getBackendReference() {
		return providerBackend;
	}




	/**
	 * Send JSON encoded response
	 * 
	 * TODO remove this function because it is unused
	 */
	protected void sendJSONResponse(String path, HttpServletResponse resp, Object value) {
		try {
			
			// Setup HTML response header
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			// - Create JSON result Object 
			JSONObject json = new JSONObject();
			// - Put value pairs into the JSON object. For now we return our return value in field "value"
			json.put("value", value);
			// - Output result
			PrintWriter out = resp.getWriter();
			out.write(json.toString());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	
	/**
	 * Send JSON encoded response
	 */
	protected void sendJSONResponse(String path, HttpServletResponse resp, JSONObject jsonValue) {
		try {
			// Setup HTML response header
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");

			// Output result
			PrintWriter out = resp.getWriter();
			out.write(jsonValue.toString()); //FIXME throws nullpointer exception if jsonValue is null
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	
	/**
	 * Handle "Get" operation. Depending on the request, there are multiple semantics:
	 * - <aasID> -> Return the AAS
	 * - <aasID>/submodels -> Return all Submodels of this AAS
	 * - <aasID>/submodels/<submodelID> -> Return Submodel of the AAS
	 * - <aasID>/submodels/<submodelID>/properties -> Return all properties of this Submodel
	 * - <aasID>/submodels/<submodelID>/operations -> Return all operations of this Submodel
	 * - <aasID>/submodels/<submodelID>/events -> Return all events of this Submodel
	 * - <aasID>/submodels/<submodelID>/properties/<propertyID> -> Returns the property
	 * - <aasID>/submodels/<submodelID>/operations/<operationID> -> Returns the operation
	 * - <aasID>/submodels/<submodelID>/events/<eventID> -> Returns the event
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		// process request depending on the path
		String uri 			= req.getRequestURI();
		String contextPath  = req.getContextPath();
		String path 		= uri.substring(contextPath.length()+1); // plus 1 for /
		
		System.out.println("-------------------------- DO GET " + path + "---------------------------------------------------------");

		// Initialize JSON object
		JSONObject jsonObj = JSONTools.Instance.serializeProperty(path, providerBackend);
		
		System.out.println("Respond: " +jsonObj);
		
		// Send HTML response
		sendJSONResponse(path, resp, jsonObj);	
		
		// We did process the request
		return;
			
		
		
		
	}
	
	 /**
	  * Handle a HTTP PATCH operation. Updates a map or collection respective to action string.
	  * - <aasID>/submodels/<submodelID>/properties/<propertyID> -> Change property
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
		
		// Extract parameters
		Object[] parameter = null;
		try {
			JSONObject json = new JSONObject(serValue.toString()); 	// causes irregular failures because serValue has wrong format!
			parameter  = (Object[]) JSONTools.Instance.deserialize(json); 
		} catch (JSONException e)   {
			sendException(new ServerException("Wrong JSON body format"), path, resp);
		}
		
		// Extract action parameter
		String action = req.getParameter("action");
		
		System.out.println("-------------------------- DO PATCH "+path+" => "+ action + " " + parameter.toString() +" ---------------------------------------------------------");
		
		// Check if submodel is frozen
		String aasID = BaSysID.instance.getAASID(path);
		String submodelPath = aasID + "/submodels/" + BaSysID.instance.getSubmodelID(path);
		
		// If not frozen change property
		// - Allow access to frozen attribute
		if (!isFrozen(submodelPath) || path.endsWith("/frozen")) {
			// Increment Clock
			try {
				incrementClock(submodelPath);
				
				switch (action) {
					/**
					 * Add an element to a collection / key-value pair to a map
					 */
					case "add":
						providerBackend.setContainedValue(path, parameter);
						break;
						
					/**
					 * Remove an element from a collection by index / remove from map by key
					 */
					case "remove":
						providerBackend.deleteContainedValue(path, parameter);
						break;
						
					default:
						sendException(new IllegalArgumentException("Action not supported."), path, resp);
				}
				
			} catch (Exception e) {
				sendException(e, path, resp);
			}
			
		} else {
			sendException(new ReadOnlyException(submodelPath), path, resp);
		}
    }
	

	
	
	/**
	 * Handle HTTP PUT operation. Overrides existing property, operation or event.
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
		
		// Deserialize json body
		Object newValue = null;
		try {
			JSONObject json = new JSONObject(serValue.toString()); 	// FIXME causes irregular failures because serValue is empty "sometimes"
			newValue  = JSONTools.Instance.deserialize(json); 
		} catch (JSONException e)   {
			sendException(new IllegalArgumentException("Invalid PUT paramater"), path, resp);
		}
		
		System.out.println("-------------------------- DO PUT "+path+" => " + newValue +" ---------------------------------------------------------");
		
		// Check if submodel is frozen
		String aasID = BaSysID.instance.getAASID(path);
		String submodelPath = aasID + "/submodels/" + BaSysID.instance.getSubmodelID(path);
		
		// If not frozen change property
		// - Allow access to frozen attribute
		if (!isFrozen(submodelPath) || path.endsWith("/frozen")) {
			// Increment Clock
			try {
				incrementClock(submodelPath);
				
				// Set the value of the element
				providerBackend.setModelPropertyValue(path, newValue);
				
			} catch (Exception e) {
				sendException(e, path, resp);
			}
			
		} else {
			sendException(new ReadOnlyException(submodelPath), path, resp);
		}
    
	}


	
	/**
	 * Handle HTTP POST operation. Creates a new Property, Operation, Event, Submodel or AAS or invokes an operation.
	 * - <aasID>/submodels -> Add new Submodel to existing AAS 									TODO add Submodel
	 * - <aasID>/submodels/<submodelID>/properties -> Add new property to existing Submodel		
	 * - <aasID>/submodels/<submodelID>/operations -> Add new operation to existing Submodel	TODO add Operation
	 * - <aasID>/submodels/<submodelID>/events -> Add new event to existing Submodel			TODO add Event
	 * - <aasID>/submodels/<submodelID>/operations/<operationID> -> Invokes a specific operation from the Submodel with a list of input params
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
		
		// Extract parameters
		Object[] parameter = null;
		try {
			JSONObject json = new JSONObject(serValue.toString()); 	// causes irregular failures because serValue is empty "sometimes"
			parameter  = (Object []) JSONTools.Instance.deserialize(json); 
		} catch (JSONException e)   {
			// assume no parameters - pass empty json
			parameter = new Object[0];
		}
		
		// Determine action
		// - Checks if path indicates that an operation needs to be executed (the last '/' is important)
		String action;
		if (path.contains("/operations/")) {
			action = "INVOKE";
		} else {
			action = "CREATE";
		}
	
		System.out.println("-------------------------- DO POST " + path + " " + action + " " + serValue.toString() + " ------------------");
		
		// Check if submodel is frozen.
		String aasID = BaSysID.instance.getAASID(path);
		String submodelPath = aasID + "/submodels/" + BaSysID.instance.getSubmodelID(path);
		
		if (!isFrozen(submodelPath)) {
		
			// Perform operation
			switch (action) {
			
				/**
				 * Process "create" request: Creates a new Property, Operation, Event, Submodel or AAS
				 */
				case "CREATE": 
					
					try {
						providerBackend.createValue(path, parameter);
					} catch (Exception e) {
						sendException(e, path, resp);
					}
					break;
							
				/**
				 * Process "invoke" request: Invoke a function with the given parameters
				 */
				case "INVOKE": {
					JSONObject result = invokeOperation(path, parameter);
					
					// Send HTML JSON response
					sendJSONResponse(path, resp, result);
					break;
				}
				
				/**
				 * If action not recognized, respond with error message
				 */
				default:
					sendException(new IllegalArgumentException("Action not supported."), path, resp);
					
			}
		} else if (action.equals("INVOKE")) {
			/**
			 * Handle Case that the submodel is frozen: INVOKE is still allowed but not CREATE
			 */
			JSONObject result = invokeOperation(path, parameter);
			
			// Send HTML JSON response
			sendJSONResponse(path, resp, result);
			
		} else {
			sendException(new ReadOnlyException(submodelPath), path, resp);
			
		}
			
	}
	
	
	
	/**
	 * Implement "Delete" operation.  Deletes any resource under the given path. Operation has multiple semantics depending on the request URL:
	 * - <aasID>/submodels/<submodelID> -> Delete Submodel from AAS
	 * - <aasID>/submodels/<submodelID>/properties/<propertyID> -> Delete property from Submodel
	 * - <aasID>/submodels/<submodelID>/operations/<operationID> -> Delete operation from Submodel
	 * - <aasID>/submodels/<submodelID>/events/<eventID> -> Delete event from Submodel
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
		
		// Extract parameters - if any
		Object[] parameter = null;
		try {
			JSONObject json = new JSONObject(serValue.toString()); 	// causes irregular failures because serValue is empty "sometimes"
			parameter  = (Object []) JSONTools.Instance.deserialize(json); 
		} catch (JSONException e)   {
			// assume no parameters - pass empty json
			parameter = (Object[]) JSONTools.Instance.deserialize(new JSONObject());
		}
		
		// Check if submodel is frozen
		String aasID = BaSysID.instance.getAASID(path);
		String submodelPath = aasID + "/" + BaSysID.instance.getSubmodelID(path);
		
		if (!isFrozen(submodelPath)) {
			
			try {
				// Increment Clock
				incrementClock(submodelPath);
			
				// Perform delete operation: Deletes any resource under the given path. 
				providerBackend.deleteValue(path);
			
			} catch (Exception e) {
				sendException(e, path, resp);
			}
			
		} else {
			sendException(new ReadOnlyException(submodelPath), path, resp);
		}
	}
	
	/**
	 * Invokes operations under the given path
	 * @param path
	 * @param parameter
	 * @return
	 */
	public JSONObject invokeOperation(String path, Object[] parameter) {
		Object result = null;
		
		System.out.println("Invoking Service: "+path + " with arguments "+ Arrays.toString((Object[]) parameter));
		
		try {
			result = providerBackend.invokeOperation(path, parameter);
			System.out.println("Return Value: "+result);

			return JSONTools.Instance.serialize(result);
			
		} catch (Exception e) {
			return JSONTools.Instance.serialize(e);
		}	
	}
	
	
	private void sendException(Exception e, String path, HttpServletResponse resp) {
		JSONObject error = JSONTools.Instance.serialize(e);
		
		// Send HTML JSON error response
		sendJSONResponse(path, resp, error);
	}
	
	private void incrementClock(String submodelPath) throws Exception {
		providerBackend.setModelPropertyValue(submodelPath +"/clock", (Integer) providerBackend.getModelPropertyValue(submodelPath +"/clock") + 1);
	}
	
	private boolean isFrozen(String submodelPath){
		return (boolean) providerBackend.getModelPropertyValue(submodelPath +"/frozen");
	}
}

