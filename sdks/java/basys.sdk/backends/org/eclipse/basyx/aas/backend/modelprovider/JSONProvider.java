package org.eclipse.basyx.aas.backend.modelprovider;

import java.io.PrintWriter;
import java.util.Arrays;

import org.eclipse.basyx.aas.api.services.IModelProvider;
import org.eclipse.basyx.aas.backend.http.tools.JSONTools;
import org.eclipse.basyx.aas.impl.tools.BaSysID;
import org.eclipse.basyx.aas.api.exception.ReadOnlyException;
import org.eclipse.basyx.aas.api.exception.ServerException;
import org.json.JSONException;
import org.json.JSONObject;




/**
 * Provider class that supports JSON serialized communication 
 * 
 * @author kuhn
 *
 */
public class JSONProvider<T extends IModelProvider> {

	
	/**
	 * Reference to IModelProvider backend
	 */
	protected T providerBackend = null;
	
	
	
	
	
	/**
	 * Constructor
	 */
	public JSONProvider(T modelProviderBackend) {
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
	 * Increments the clock property for the given submodel
	 * @param submodelPath
	 * @throws Exception
	 */
	private void incrementClock(String submodelPath) throws Exception {
		providerBackend.setModelPropertyValue(submodelPath +"/clock", (Integer) providerBackend.getModelPropertyValue(submodelPath +"/clock") + 1);
	}
	
	private boolean isFrozen(String submodelPath){
		return (boolean) providerBackend.getModelPropertyValue(submodelPath +"/frozen");
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
	 * Process a BaSys get operation, return JSON serialized result
	 */
	public void processBaSysGet(String path, PrintWriter outputStream) {
		
		System.out.println("-------------------------- DO GET " + path + "---------------------------------------------------------");

		// Initialize JSON object
		JSONObject jsonObj = JSONTools.Instance.serializeProperty(path, providerBackend);
		
		// Send response
		sendJSONResponse(path, outputStream, jsonObj);		
	}

	
	/**
	 * Process a BaSys set operation
	 * @param path
	 * @param serializedJSONValue
	 * @param outputStream
	 */
	public void processBaSysSet(String path, String serializedJSONValue, PrintWriter outputStream) {
		
		// Deserialize json body.  If parameter is null, an exception has been sent
		Object parameter = extractParameter(path, serializedJSONValue, outputStream); if (parameter == null) return; 
		
		System.out.println("-------------------------- DO PUT "+path+" => " + parameter +" ---------------------------------------------------------");
		
		// Make submodel path reference 
		String aasID = BaSysID.instance.getAASID(path);
		String submodelID = BaSysID.instance.getSubmodelID(path);
		String submodelPath = BaSysID.instance.buildPath(aasID, submodelID);
		
		// If not frozen change property
		// - Allow access to frozen attribute
		if (!isFrozen(submodelPath) || path.endsWith("/frozen")) {
			// Increment Clock
			try {
				incrementClock(submodelPath);
				
				// Set the value of the element
				providerBackend.setModelPropertyValue(path, parameter);
				
			} catch (Exception e) {
				sendException(e, path, outputStream);
			}
			
		} else {
			sendException(new ReadOnlyException(submodelPath), path, outputStream);
		}
    
			
	}
	
	/**
	 * 
	 * @param path
	 * @param serializedJSONValue
	 * @param outputStream
	 */
	public void processBaSysPost(String path, String serializedJSONValue, PrintWriter outputStream) {
		// Extract parameters
		Object[] parameter = null;
		try {
			JSONObject json = new JSONObject(serializedJSONValue); 	// causes irregular failures because serValue is empty "sometimes"
			parameter  = (Object []) JSONTools.Instance.deserialize(json); 
		} catch (JSONException e)   {
			// assume no parameters - pass empty array
			parameter = new Object[0];
			
		}
		
		// Determine action
		// - Checks if path indicates that an operation needs to be executed (the last '/' is important to distinguish from creating a new operation)
		String action;
		if (path.contains("/operations/")) { // FIXME: If a nested operation is called, there is no /operations/
			action = "INVOKE";
		} else {
			action = "CREATE";
		}
	
		System.out.println("-------------------------- DO POST " + path + " " + action + " " + serializedJSONValue.toString() + " ------------------");
		
		// Make submodel path reference 
		String aasID = BaSysID.instance.getAASID(path);
		String submodelID = BaSysID.instance.getSubmodelID(path);
		String submodelPath = BaSysID.instance.buildPath(aasID, submodelID);
		
		if (!isFrozen(submodelPath)) {
		
			// Perform operation
			switch (action) {
			
				/**
				 * Process "create" request: Creates a new Property, Operation, Event, Submodel or AAS
				 */
				case "CREATE": 
					processBasysCreate(path, parameter, outputStream);
					break;
							
				/**
				 * Process "invoke" request: Invoke a function with the given parameters
				 */
				case "INVOKE": {
					processBaSysInvoke(path, parameter, outputStream);
					break;
				}
				
				/**
				 * If action not recognized, respond with error message
				 */
				default:
					sendException(new IllegalArgumentException("Action not supported."), path, outputStream);
					
			}
		} else if (action.equals("INVOKE")) {
			/**
			 * Handle Case that the submodel is frozen: INVOKE is still allowed but not CREATE
			 */
			processBaSysInvoke(path, parameter, outputStream);
						
		} else {
			sendException(new ReadOnlyException(submodelPath), path, outputStream);
		}
	}

	
	/**
	 * Process a BaSys invoke operation
	 */
	public void processBaSysInvoke(String path, Object[] parameter, PrintWriter outputStream) {
		
		Object result = null;
		System.out.println("Invoking Service: "+path + " with arguments "+ Arrays.toString((Object[]) parameter));
		
		try {
			result = providerBackend.invokeOperation(path, parameter);
			System.out.println("Return Value: "+result);

		} catch (Exception e) {
			sendException(e, path, outputStream);
		}	
		
		// Send response
		sendJSONResponse(path, outputStream, JSONTools.Instance.serialize(result));
	}
	
	/**
	 * Process a patch request. Updates a map or collection.
	 * @param path
	 * @param serializedJSONValue
	 * @param action
	 * @param outputStream
	 */
	public void processBaSysPatch(String path, String serializedJSONValue, String action, PrintWriter outputStream) {
		
		// Extract parameters
		Object[] parameter = null;
		try {
			JSONObject json = new JSONObject(serializedJSONValue); 
			try {
				parameter  = (Object[]) JSONTools.Instance.deserialize(json); 
			} catch (ClassCastException e1) {
				parameter = new Object[1]; parameter[0] = JSONTools.Instance.deserialize(json);
			}
		} catch (JSONException e)   {
			sendException(new ServerException("Bad parameter", "HTTP Patch failed. Wrong JSON parameter body format"), path, outputStream);
		}

		System.out.println("-------------------------- DO PATCH "+path+" => "+ action + " " + parameter.toString() +" ---------------------------------------------------------");
		
		// Make submodel path reference 
		String aasID = BaSysID.instance.getAASID(path);
		String submodelID = BaSysID.instance.getSubmodelID(path);
		String submodelPath = BaSysID.instance.buildPath(aasID, submodelID);
		
		// If not frozen change property
		// - Allow access to frozen attribute
		if (!isFrozen(submodelPath)) {
			// Increment Clock
			try {
				incrementClock(submodelPath);
				
				switch (action) {
					/**
					 * Add an element to a collection / key-value pair to a map
					 */
					case "add":
						providerBackend.setModelPropertyValue(path, parameter);
						break;
						
					/**
					 * Remove an element from a collection by index / remove from map by key. We know parameter must only contain 1 element
					 */
					case "remove":
						providerBackend.deleteValue(path, parameter[0]);
						break;
						
					default:
						sendException(new ServerException("Unsupported", "Action not supported."), path, outputStream);
				}
				
			} catch (Exception e) {
				sendException(e, path, outputStream);
			}
			
		} else {
			sendException(new ReadOnlyException(submodelPath), path, outputStream);
		}
	}
	
	
	/**
	  Implement "Delete" operation.  Deletes any resource under the given path.
	 * @param path
	 * @param serializedJSONValue
	 * @param outputStream
	 */
	public void processBaSysDelete(String path, PrintWriter outputStream) {
		
		// Make submodel path reference 
		String aasID = BaSysID.instance.getAASID(path);
		String submodelID = BaSysID.instance.getSubmodelID(path);
		String submodelPath = BaSysID.instance.buildPath(aasID, submodelID);
		
		if (!isFrozen(submodelPath)) {
			
			try {
				// Increment Clock
				incrementClock(submodelPath);
			
				// Perform delete operation: Deletes any resource under the given path. 
				providerBackend.deleteValue(path);
					
			} catch (Exception e) {
				sendException(e, path, outputStream);
			}
					
		} else {
			sendException(new ReadOnlyException(submodelPath), path, outputStream);
		}
	}
	
	/**
	 * Creates a resource under the given path
	 * @param path
	 * @param parameter
	 * @param outputStream
	 */
	public void processBasysCreate(String path, Object parameter, PrintWriter outputStream) {
		try {
			providerBackend.createValue(path, parameter);
		} catch (Exception e) {
			sendException(e, path, outputStream);
		}
	}
	
}


