package org.eclipse.basyx.aas.backend.connector;

import org.eclipse.basyx.aas.backend.http.tools.JSONTools;
import org.eclipse.basyx.vab.core.IModelProvider;
import org.json.JSONException;
import org.json.JSONObject;

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

		// Deserialize
		Object result = JSONTools.Instance.deserialize(new JSONObject(message.toString()));

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

		// Serialize value Object
		JSONObject jsonObject = JSONTools.Instance.serialize(newValue);

		Object message = provider.setModelPropertyValue(path, jsonObject);

		// Deserialize
		Object result = JSONTools.Instance.deserialize(new JSONObject(message.toString()));

		// Handle meta information and exceptions
		verifyResponse(result);

	}

	@Override
	public void createValue(String path, Object newEntity) throws Exception {

		// Serialize value Object
		JSONObject jsonObject = JSONTools.Instance.serialize(newEntity);

		System.out.println("Parameter= " + newEntity + " => Serialized to " + jsonObject);

		Object message = provider.createValue(path, jsonObject);

		// Deserialize (unwanted behavior?: exception is thrown when it is returned by
		// the deserialisation)
		Object result = JSONTools.Instance.deserialize(new JSONObject(message.toString()));

		// Handle meta information and exceptions
		verifyResponse(result);
	}

	@Override
	public void deleteValue(String path) throws Exception {

		Object message = provider.deleteValue(path);

		// Deserialize
		Object result = JSONTools.Instance.deserialize(new JSONObject(message.toString()));

		// Handle meta information and exceptions
		verifyResponse(result);
	}

	@Override
	public void deleteValue(String path, Object obj) throws Exception {

		// Serialize parameter
		JSONObject jsonObject = JSONTools.Instance.serialize(obj);

		Object message = provider.deleteValue(path, jsonObject);

		// Deserialize
		Object result = JSONTools.Instance.deserialize(new JSONObject(message.toString()));

		// Handle meta information and exceptions
		verifyResponse(result);
	}

	@Override
	public Object invokeOperation(String path, Object[] parameter) throws Exception {

		// Serialize parameter
		JSONObject jsonObject = JSONTools.Instance.serialize(parameter);

		Object message = provider.invokeOperation(path, jsonObject);

		// Deserialize
		Object result = JSONTools.Instance.deserialize(new JSONObject(message.toString()));

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

		} catch (JSONException e) {
			// There is no return value or deserialization failed
			throw e;
		}

	}

}