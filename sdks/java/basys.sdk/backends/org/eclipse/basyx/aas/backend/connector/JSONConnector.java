package org.eclipse.basyx.aas.backend.connector;

import java.util.Map;

import org.eclipse.basyx.aas.api.exception.ServerException;
import org.eclipse.basyx.aas.api.reference.IElementReference;
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

		// Convert to json
		JSONObject json = new JSONObject(message.toString());

		// Handle meta information and return value
		// verifyResponse(message);

		// Deserialize response
		Map<String, Object> messageMap = JSONTools.Instance.deserialize(json);

		Object containedElement = messageMap.get("entity");

		return containedElement;
	}

	@Override
	public void setModelPropertyValue(String path, Object newValue) throws Exception {

		// Serialize value Object
		JSONObject jsonObject = JSONTools.Instance.serialize(newValue);

		Object message = provider.setModelPropertyValue(path, jsonObject);

		verifyResponse(message);
	}

	@Override
	public void createValue(String path, Object newEntity) throws Exception {

		// Serialize value Object
		JSONObject jsonObject = JSONTools.Instance.serialize(newEntity);

		Object message = provider.createValue(path, jsonObject);

		verifyResponse(message);
	}

	@Override
	public void deleteValue(String path) throws Exception {

		Object message = provider.deleteValue(path);

		verifyResponse(message);
	}

	@Override
	public void deleteValue(String path, Object obj) throws Exception {

		// Serialize parameter
		JSONObject jsonObject = JSONTools.Instance.serialize(obj);

		Object message = provider.deleteValue(path, jsonObject);

		verifyResponse(message);
	}

	@Override
	public Object invokeOperation(String path, Object[] parameter) throws Exception {

		// Serialize parameter
		JSONObject jsonObject = JSONTools.Instance.serialize(parameter);

		Object message = provider.invokeOperation(path, jsonObject);

		verifyResponse(message);

		// Deserialize response
		Map<String, Object> messageMap = JSONTools.Instance.deserialize(new JSONObject(message));

		return messageMap.get("entity");
	}

	@Override
	public Map<String, IElementReference> getContainedElements(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Function to extract and verify the response header TODO process other message
	 * information like "success", "entityType", "messages"
	 */
	private void verifyResponse(Object message) throws Exception {

		// Try to extract response if any
		try {

			// Deserialize response
			Map<String, Object> messageMap = JSONTools.Instance.deserialize(new JSONObject(message));

			// Handle meta information
			/**
			 * TODO process other message information like "success", "entityType",
			 * "messages"
			 */

			if ((boolean) messageMap.get("isException")) {
				// Throw server exception
				throw (ServerException) messageMap.get("entity");
			}

		} catch (JSONException e) {
			// There is no return value or deserialization failed
			throw e;
		}

	}

}