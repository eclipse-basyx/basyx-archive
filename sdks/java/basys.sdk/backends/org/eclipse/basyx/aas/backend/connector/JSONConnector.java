package org.eclipse.basyx.aas.backend.connector;

import java.util.Map;

import org.eclipse.basyx.aas.backend.http.tools.GSONTools;
import org.eclipse.basyx.vab.core.IModelProvider;
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.json.JSONObject;

/**
 * Connector Class that receives a hashmap from its provider containing a
 * message that was sent from the server. It removes the message header and
 * returns the entity.
 * 
 * @author pschorn
 *
 */
public class JSONConnector implements IModelProvider {

	/**
	 * Reference to Connector backend
	 */
	protected IBaSyxConnector provider = null;

	public JSONConnector(IBaSyxConnector provider) {

		this.provider = provider;
	}

	@Override
	public Object getModelPropertyValue(String path) { // shouldn't GET throw an exception too?

		// Get element from server
		Object message = provider.getModelPropertyValue(path);
		//First get the GSON object from the JSON string
		Object gsonObj =GSONTools.Instance.getObjFromJsonStr(message.toString());
		
		@SuppressWarnings("unchecked")
		Object result = GSONTools.Instance.deserialize((Map<String, Object>) gsonObj);
		
		//Object resultGson=GSONTools.Instance.deserialize(message);

		// Handle meta information and exceptions
		try {
			verifyResponse(result);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Return value
		return result;

	}

	@Override
	public void setModelPropertyValue(String path, Object newValue) throws Exception {

		
		Map<String, Object> gsonMap = GSONTools.Instance.serialize(newValue);
		String jsonString = GSONTools.Instance.getJsonString(gsonMap);

		Object message = provider.setModelPropertyValue(path, jsonString);

		// Deserialize
		//First get the GSON object from the JSON string
        Object gsonObj =GSONTools.Instance.getObjFromJsonStr(message.toString());
		
		@SuppressWarnings("unchecked")
		Object result = GSONTools.Instance.deserialize((Map<String, Object>) gsonObj);

		// Handle meta information and exceptions
		verifyResponse(result);

	}

	@Override
	public void createValue(String path, Object newEntity) throws Exception {

		// Serialize value Object
		Map<String, Object> gsonMap = GSONTools.Instance.serialize(newEntity);

		System.out.println("Parameter= " + newEntity + " => Serialized to " + gsonMap);
        String jsonString = GSONTools.Instance.getJsonString(gsonMap);
		Object message = provider.createValue(path, jsonString);
		//First get the GSON object from the JSON string
		Object gsonObj =GSONTools.Instance.getObjFromJsonStr(message.toString());	
		@SuppressWarnings("unchecked")
		Object result = GSONTools.Instance.deserialize((Map<String, Object>) gsonObj);

		// Handle meta information and exceptions
		verifyResponse(result);
	}

	@Override
	public void deleteValue(String path) throws Exception {

		Object message = provider.deleteValue(path);

		// Deserialize
		//First get the GSON object from the JSON string
		Object gsonObj =GSONTools.Instance.getObjFromJsonStr(message.toString());	
		@SuppressWarnings("unchecked")
		Object result = GSONTools.Instance.deserialize((Map<String, Object>) gsonObj);

		// Handle meta information and exceptions
		verifyResponse(result);
	}

	@Override
	public void deleteValue(String path, Object obj) throws Exception {

		// Serialize parameter
		Map<String, Object> gsonMap = GSONTools.Instance.serialize(obj);
		String jsonString = GSONTools.Instance.getJsonString(gsonMap);
		Object message = provider.deleteValue(path, jsonString);

		// Deserialize
		//First get the GSON object from the JSON string
		Object gsonObj =GSONTools.Instance.getObjFromJsonStr(message.toString());	
		@SuppressWarnings("unchecked")
		Object result = GSONTools.Instance.deserialize((Map<String, Object>) gsonObj);

		// Handle meta information and exceptions
		verifyResponse(result);
	}

	@Override
	public Object invokeOperation(String path, Object[] parameter) throws Exception {

		// Serialize parameter
		Map<String, Object> gsonMap = GSONTools.Instance.serialize(parameter);
		
		String jsonString = GSONTools.Instance.getJsonString(gsonMap);

		Object message = provider.invokeOperation(path, jsonString);

		// Deserialize
		//First get the GSON object from the JSON string
		Object gsonObj =GSONTools.Instance.getObjFromJsonStr(message.toString());	
		@SuppressWarnings("unchecked")
		Object result = GSONTools.Instance.deserialize((Map<String, Object>) gsonObj);
		// Handle meta information and exceptions
		verifyResponse(result);

		// Deserialize response
		return result;
	}

	/**
	 * Function to extract and verify the response header TODO process other message
	 * information like "success", "entityType", "messages"
	 * 
	 * @param message
	 *            - provide deserialized message
	 */
	private void verifyResponse(Object message) throws Exception {

		// Try to extract response if any
		try {

			// Deserialize response
			// Map<String, Object> messageMap = JSONTools.Instance.deserialize(new
			// JSONObject(message));

			// Handle meta information
			/**
			 * TODO process other message information like "success", "entityType",
			 * "messages"
			 */

			// if ((boolean) messageMap.get("isException")) {
			// Throw server exception
			// throw (ServerException) messageMap.get("entity");
			// }

			if (message instanceof Exception) {
				throw (Exception) message;
			}

		} catch (Exception e) {
			// There is no return value or deserialization failed
			throw e;
		}

	}

}