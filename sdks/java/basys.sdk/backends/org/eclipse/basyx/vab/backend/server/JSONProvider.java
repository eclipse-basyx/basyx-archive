package org.eclipse.basyx.vab.backend.server;

import java.io.PrintWriter;
import java.util.Arrays;

import org.eclipse.basyx.aas.api.exception.ServerException;
import org.eclipse.basyx.aas.backend.http.tools.JSONTools;
import org.eclipse.basyx.vab.core.IModelProvider;
import org.eclipse.basyx.vab.core.tools.VABPathTools;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Provider class that supports JSON serialized communication
 * 
 * 
 * @author pschorn, schnicke, kuhn
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
	private void sendJSONResponse(PrintWriter outputStream, JSONObject jsonValue) {
		// Output result
		outputStream.write(jsonValue.toString()); // FIXME throws nullpointer exception if jsonValue is null
		outputStream.flush();
	}

	/**
	 * Send Error
	 * 
	 * @param e
	 * @param path
	 * @param resp
	 */
	private void sendException(PrintWriter resp, Exception e) {
		System.out.println("Sending exception...");
		JSONObject error = JSONTools.Instance.serialize(e);

		// Send error response
		sendJSONResponse(resp, error);
	}

	/**
	 * Extracts parameter from JSON and handles de-serialization errors
	 * 
	 * @param path
	 * @param serializedJSONValue
	 * @param outputStream
	 * @return
	 * @throws ServerException
	 */
	private Object extractParameter(String path, String serializedJSONValue, PrintWriter outputStream) {

		System.out.println("Extracting Parameter: " + serializedJSONValue);

		// Return value
		Object result = null;

		// Deserialize json body
		try {
			JSONObject json = new JSONObject(serializedJSONValue.toString());
			result = JSONTools.Instance.deserialize(json);

		} catch (JSONException e) {
			e.printStackTrace();
			sendException(outputStream, new IllegalArgumentException("Invalid paramater: " + serializedJSONValue));
		}
		return result;
	}

	/**
	 * Process a BaSys get operation, return JSON serialized result
	 */
	public void processBaSysGet(String path, PrintWriter outputStream) {

		System.out.println("-------------------------- DO GET " + path + "---------------------------------------------------------");

		Object value = providerBackend.getModelPropertyValue(path);

		// Initialize JSON object
		JSONObject jsonObj = JSONTools.Instance.serialize(value);

		// Send response
		sendJSONResponse(outputStream, jsonObj);
	}

	/**
	 * Process a BaSys set operation
	 * 
	 * @param path
	 * @param serializedJSONValue
	 * @param outputStream
	 */
	public void processBaSysSet(String path, String serializedJSONValue, PrintWriter outputStream) {
		// Deserialize json body. If parameter is null, an exception has been sent
		Object parameter = extractParameter(path, serializedJSONValue, outputStream);
		if (parameter == null)
			return;

		System.out.println("-------------------------- DO PUT " + path + " => " + parameter + " ---------------------------------------------------------");

		// Try to set value of BaSys VAB element
		try {
			// Set the value of the element
			providerBackend.setModelPropertyValue(path, parameter);

			// Send positive JSON response
			JSONObject jsonObj = JSONTools.Instance.serialize(true); // TODO provide message meta information here
			sendJSONResponse(outputStream, jsonObj);

		} catch (Exception e) {
			sendException(outputStream, e);
		}
	}

	/**
	 * Process a BaSys invoke or create operation
	 * 
	 * @param path
	 * @param serializedJSONValue
	 * @param outputStream
	 */
	public void processBaSysPost(String path, String serializedJSONValue, PrintWriter outputStream) {

		// Invoke provider backend
		try {
			// Check if request is for property creation or operation invoke
			if (VABPathTools.isOperationPath(path)) {

				// Invoke BaSys VAB 'invoke' primitive
				processBaSysInvoke(path, serializedJSONValue, outputStream);

			} else {
				// Invoke the BaSys 'create' primitive
				processBaSysCreate(path, serializedJSONValue, outputStream);
			}
		} catch (Exception e) {
			sendException(outputStream, e);
		}

	}

	/**
	 * Process a BaSys invoke operation
	 */
	public void processBaSysInvoke(String path, String serializedJSONValue, PrintWriter outputStream) {

		// Deserialize json body. If parameter is null, an exception has been sent
		Object parameter = extractParameter(path, serializedJSONValue, outputStream);

		JSONObject returnValue = null;

		try {
			System.out.println("Invoking Service: " + path + " with arguments " + Arrays.toString((Object[]) parameter));

			Object result = providerBackend.invokeOperation(path, (Object[]) parameter);
			System.out.println("Return Value: " + result);

			returnValue = JSONTools.Instance.serialize(result);
			System.out.println(returnValue);

		} catch (Exception e) {
			sendException(outputStream, e);
		}

		// Send response
		sendJSONResponse(outputStream, returnValue);
	}

	/**
	 * Process a patch request. Updates a map or collection.
	 * 
	 * @param path
	 * @param serializedJSONValue
	 * @param action
	 * @param outputStream
	 */
	public void processBaSysPatch(String path, String serializedJSONValue, String action, PrintWriter outputStream) {

		try {
			// Deserialize json body. If parameter is null, an exception has been sent
			Object parameter = extractParameter(path, serializedJSONValue, outputStream);

			switch (action.toLowerCase()) {
			/**
			 * Add an element to a collection / key-value pair to a map
			 */
			case "add":
				providerBackend.setModelPropertyValue(path, parameter);
				break;

			/**
			 * Remove an element from a collection by index / remove from map by key. We
			 * know parameter must only contain 1 element
			 */
			case "remove":
				providerBackend.deleteValue(path, parameter);
				break;

			default:
				sendException(outputStream, new ServerException("Unsupported", "Action not supported."));
			}

			// Send positive JSON response
			JSONObject jsonObj = JSONTools.Instance.serialize(true); // TODO provide message meta information here
			sendJSONResponse(outputStream, jsonObj);

		} catch (Exception e) {
			sendException(outputStream, e);
		}
	}

	/**
	 * Implement "Delete" operation. Deletes any resource under the given path.
	 * 
	 * @param path
	 * @param serializedJSONValue
	 * @param outputStream
	 */
	public void processBaSysDelete(String path, String serializedValue, PrintWriter outputStream) {
		// Deserialize json body. If parameter is null, an exception has been sent
		Object parameter = null;
		if (!serializedValue.isEmpty()) {
			parameter = extractParameter(path, serializedValue, outputStream);
		}

		System.out.println("Delete1:" + parameter);
		// Invoke provider backend
		try {
			// Process delete request with or without argument
			if (parameter == null) {
				this.providerBackend.deleteValue(path);
			} else {
				System.out.println("Delete:" + parameter);
				this.providerBackend.deleteValue(path, parameter);
			}

			// Send positive JSON response
			JSONObject jsonObj = JSONTools.Instance.serialize(true); // TODO provide message meta information here
			sendJSONResponse(outputStream, jsonObj);

		} catch (Exception e) {
			e.printStackTrace();

			sendException(outputStream, e);
		}
	}

	/**
	 * Creates a resource under the given path
	 * 
	 * @param path
	 * @param parameter
	 * @param outputStream
	 */
	public void processBaSysCreate(String path, String serializedJSONValue, PrintWriter outputStream) {

		try {
			// Deserialize json body. If parameter is null, an exception has been sent
			Object parameter = extractParameter(path, serializedJSONValue, outputStream);

			providerBackend.createValue(path, parameter);

			// Send positive JSON response
			JSONObject jsonObj = JSONTools.Instance.serialize(true); // TODO provide message meta information here
			sendJSONResponse(outputStream, jsonObj);

		} catch (Exception e) {
			sendException(outputStream, e);
		}
	}

}
