package org.eclipse.basyx.aas.backend.modelprovider.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.basyx.aas.api.exception.ReadOnlyException;
import org.eclipse.basyx.aas.api.exception.ServerException;
import org.eclipse.basyx.aas.api.services.IModelProvider;
import org.eclipse.basyx.aas.backend.http.tools.JSONTools;
import org.json.JSONException;
import org.json.JSONObject;



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
	 * Implement "Get" operation 
	 * 
	 * Process HTTP get request - get sub model property value
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		System.out.println("-------------------------- DO GET ---------------------------------------------------------");
		
		
		// Access parameters
		String path       = (String) req.getParameter("path"); 
		
		// Initialize JSON object
		JSONObject jsonObj = JSONTools.Instance.serializeProperty(path, providerBackend);
		
		System.out.println("Respond: " +jsonObj);
		
		// Send HTML response
		sendJSONResponse(path, resp, jsonObj);	
		
		// We did process the request
		return;
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
		
		// Extract parameters
		Object[] newValue = null;
		try {
			JSONObject json = new JSONObject(serValue.toString()); 	// causes irregular failures because serValue is empty "sometimes"
			newValue  = (Object []) JSONTools.Instance.deserialize(json); 
		} catch (JSONException e)   {
			// assume no parameters - pass empty json
			newValue = (Object[]) JSONTools.Instance.deserialize(new JSONObject());
			
			e.printStackTrace();
		}
		//Object newValue   = JSONTools.Instance.deserialize(new JSONObject(serValue.toString()));
		
		System.out.println("Put: " + path + " " + newValue);
		System.out.println("-------------------------- DO PUT "+path+" => " + newValue +" ---------------------------------------------------------");
		
		
		// Check if submodel is frozen
		String submodelPath = path.substring(0, path.indexOf("/"));
		boolean frozen = (boolean) providerBackend.getModelPropertyValue(submodelPath +"/frozen");
		
		// if not frozen change property
		// - allow access to frozen attribute
		if (!frozen || path.endsWith("frozen")) {
			// Increment Clock
			try {
				providerBackend.setModelPropertyValue(submodelPath +"/clock", (Integer) providerBackend.getModelPropertyValue(submodelPath +"/clock") + 1);
				
				// Update property value
				providerBackend.setModelPropertyValue(path, newValue);
			} catch (ServerException e) {
				sendException(e, path, resp);
			}
			
		} else {
			sendException(new ReadOnlyException(submodelPath), path, resp);
		}
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
		while (bufReader.ready()) {
			serValue.append(bufReader.readLine());
		}
		
		// Access parameters from request header
		String path       = (String) req.getParameter("path"); 
		String operation  = (String) req.getParameter("op"); 
		System.out.println("-------------------------- DO POST "+path+" - op: "+operation+"  - par: "+ serValue.toString() + " ------------------");
		
		// Extract parameters
		Object[] parameter = null;
		try {
			JSONObject json = new JSONObject(serValue.toString()); 	// causes irregular failures because serValue is empty "sometimes"
			parameter  = (Object []) JSONTools.Instance.deserialize(json); 
		} catch (JSONException e)   {
			// assume no parameters - pass empty json
			parameter = (Object[]) JSONTools.Instance.deserialize(new JSONObject());
			
			e.printStackTrace();
		}
		
		// Extract path to working submodel; Handles Case that there is no property reference
		String submodelPath = path.substring(0, (path.indexOf("/")!=-1? path.indexOf("/") : path.length()));
		
		// Check if submodel is frozen.
		boolean frozen = (boolean) providerBackend.getModelPropertyValue(submodelPath +"/frozen");
		
		if (!frozen) {
		
			// Perform operation
			switch (operation) {
			
				/**
				 * Process "create" request that adds a value to the given map or collection
				 */
				case "create": 
					// Increment Clock
					try {
						providerBackend.setModelPropertyValue(submodelPath +"/clock", (Integer) providerBackend.getModelPropertyValue(submodelPath +"/clock") + 1);
					} catch (ServerException e1) {
						sendException(e1, path, resp);
					}
					
					// create value in map or collection
					try {
						providerBackend.createValue(path, parameter);
					} catch (Exception e) {
						sendException(e, path, resp);
					} 
					break;
					
				/**
				 * Process "createProperty" request that creates a new Property in the dynamic properties map of the given submodel
				 */
				case "createProperty": {
					try {
						providerBackend.createValue(submodelPath, parameter);
					} catch (Exception e) {
						sendException(e, path, resp);
					}
					break;
				}
					
				/**
				 * Process "delete" request that removes a value from the given map or collection
				 */
				case "delete": 
					// Increment Clock
					try {
						providerBackend.setModelPropertyValue(submodelPath +"/clock", (Integer) providerBackend.getModelPropertyValue(submodelPath +"/clock") + 1);
					} catch (ServerException e1) {
						sendException(e1, path, resp);
					}
					
					// Delete Value in map or collection
					try {
						providerBackend.deleteValue(path, parameter);
					} catch (Exception e) {
						sendException(e, path, resp);
					} 
					break;
				
				/**
				 * Process "invoke" request that invokes a function with the given parameters
				 */
				case "invoke": {
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
		} else {
			/**
			 * Handle Case that the submodel is frozen: Cloning and unfreezing is still allowed but not create and delete 
			 */
			switch (operation) {

				case "create": {
					sendException(new ReadOnlyException(submodelPath), path, resp);
					break;
				}
				
				case "createProperty": {
					sendException(new ReadOnlyException(submodelPath), path, resp);
					break;
				}
				
				case "delete": {
					sendException(new ReadOnlyException(submodelPath), path, resp);
					break;
				}
				
				/**
				 * Invoking Operations is still allowed
				 */
				case "invoke": {
					JSONObject result = invokeOperation(path, parameter);
					
					// Send HTML JSON response
					sendJSONResponse(path, resp, result);
					break;
				}
				default:
					sendException(new IllegalArgumentException("Action not supported."), path, resp);
				
			}
		}
			
	}
	
	

	private JSONObject invokeOperation(String path, Object[] parameter) {
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
	
	
	/**
	 * Implement "Delete" operations TODO remove this function since delete is already handled by post
	 */
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Read posted parameter
		InputStreamReader reader    = new InputStreamReader(req.getInputStream());
		BufferedReader    bufReader = new BufferedReader(reader);
		StringBuilder     serValue  = new StringBuilder(); 
		
		// Read values
		while (bufReader.ready()) serValue.append(bufReader.readLine());
		
		// Access parameters from request header
		String path       = (String) req.getParameter("path"); 
		Object parameter  = new JSONObject(serValue.toString()).get("value");
		
		String submodelPath = path.substring(0, path.indexOf("/"));
		
		// Check if submodel is frozen
		boolean frozen = (boolean) providerBackend.getModelPropertyValue(submodelPath +"/frozen");
		
		if (!frozen) {
			
			// Increment Clock
			try {
				providerBackend.setModelPropertyValue(submodelPath +"/clock", (Integer) providerBackend.getModelPropertyValue(submodelPath +"/clock") + 1);
			} catch (ServerException e1) {
				sendException(e1, path, resp);
			}
			
			// Perform delete operation
			try {
				providerBackend.deleteValue(path, parameter);
			} catch (Exception e) {
				sendException(e, path, resp);
			}
			
		} else {
			sendException(new ReadOnlyException(submodelPath), path, resp);
		}
	}
}

