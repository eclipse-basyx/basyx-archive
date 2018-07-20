package org.eclipse.basyx.aas.backend.modelprovider;

import java.io.PrintWriter;

import org.eclipse.basyx.aas.api.services.IModelProvider;
import org.eclipse.basyx.aas.backend.http.tools.JSONTools;
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
	public void sendJSONResponse(String path, PrintWriter outputStream, JSONObject jsonValue) {
		// Output result
		outputStream.write(jsonValue.toString()); //FIXME throws nullpointer exception if jsonValue is null
		outputStream.flush();
	}
	
	
	/**
	 * Process a BaSys get operation, return JSON serialized result
	 */
	public void processBaSysGet(String path, PrintWriter outputStream) {
		// Initialize JSON object
		JSONObject jsonObj = JSONTools.Instance.serializeProperty(path, providerBackend);
		
		// Send HTML response
		sendJSONResponse(path, outputStream, jsonObj);		
	}

	
	/**
	 * Process a BaSys set operation
	 */
	public void processBaSysSet(String path, String serializedJSONValue) {
		// Deserialize JSON serialized value
		Object newValue   = JSONTools.Instance.deserialize(new JSONObject(serializedJSONValue));

		// Increment Clock
		String submodelPath = path.substring(0, path.indexOf("/"));
		providerBackend.setModelPropertyValue(submodelPath +"/clock", (Integer) providerBackend.getModelPropertyValue(submodelPath +"/clock") + 1);
				
		// Update property value
		providerBackend.setModelPropertyValue(path, newValue);
	}

	
	/**
	 * Process a BaSys create operation
	 */
	public void processBaSysCreate(String path, String serializedJSONValue) {
		// Deserialize JSON serialized value
		Object newValue   = JSONTools.Instance.deserialize(new JSONObject(serializedJSONValue));

		// Increment Clock
		String submodelPath = path.substring(0, path.indexOf("/"));
		providerBackend.setModelPropertyValue(submodelPath +"/clock", (Integer) providerBackend.getModelPropertyValue(submodelPath +"/clock") + 1);
		
		// Update data
		providerBackend.createValue(path, newValue); 
	}

	
	/**
	 * Process a BaSys delete operation
	 */
	public void processBaSysDelete(String path, String serializedJSONValue) {
		// Deserialize JSON serialized value
		Object newValue   = JSONTools.Instance.deserialize(new JSONObject(serializedJSONValue));

		// Increment Clock
		String submodelPath = path.substring(0, path.indexOf("/"));
		providerBackend.setModelPropertyValue(submodelPath +"/clock", (Integer) providerBackend.getModelPropertyValue(submodelPath +"/clock") + 1);
		
		// Update data
		providerBackend.deleteValue(path, newValue); 
	}

	
	/**
	 * Process a BaSys invoke operation
	 */
	public void processBaSysInvoke(String path, String serializedJSONValue, PrintWriter outputStream) {
		// Deserialize JSON serialized value
		Object[] parameter  = (Object []) JSONTools.Instance.deserialize(new JSONObject(serializedJSONValue));

		// Invoke operation
		Object result = providerBackend.invokeOperation(path, parameter);

		// Serialize result
		JSONObject jsonResult = JSONTools.Instance.serialize(result);

		// Create and send response
		sendJSONResponse(path, outputStream, jsonResult);
	}
}


