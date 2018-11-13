package org.eclipse.basyx.aas.backend.connector.basyx;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;

import org.eclipse.basyx.aas.api.exception.ServerException;
import org.eclipse.basyx.aas.api.reference.IElementReference;
import org.eclipse.basyx.aas.backend.http.tools.JSONTools;
import org.eclipse.basyx.vab.backend.server.basyx.BaSyxTCPProvider;
import org.eclipse.basyx.vab.backend.server.basyx.CoderTools;
import org.eclipse.basyx.vab.core.IModelProvider;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * BaSyx connector class
 * 
 * @author kuhn, pschorn, schnicke
 *
 */
public class BaSyxConnector implements IModelProvider {

	Socket clientSocket;
	BufferedInputStream inputStream;
	

	public BaSyxConnector(Socket clientSocket) throws IOException {
		super();
		this.clientSocket = clientSocket;
		inputStream = new BufferedInputStream(clientSocket.getInputStream());
	}


	/**
	 * Invoke a BaSyx operation in a remote provider
	 */
	protected byte[] invokeBaSyx(byte[] call) {
		// Catch exceptions
		try {
			// Input and output streams
			

			// Send BaSyx operation
			clientSocket.getOutputStream().write(call);

			// Wait for response
			// - Wait for leading 4 byte header that contains frame length
			while (inputStream.available() < 4)
				Thread.sleep(1);

			// Get frame size
			byte[] lengthBytes = new byte[4];
			inputStream.read(lengthBytes, 0, 4);
			int frameSize = CoderTools.getInt32(lengthBytes, 0);

			// Wait for frame to arrive
			while (inputStream.available() < frameSize)
				Thread.sleep(1);
			// - Receive frame
			byte[] rxFrame = new byte[frameSize];
			inputStream.read(rxFrame, 0, frameSize);

			// Return received data
			return rxFrame;
		} catch (IOException | InterruptedException e) {
			// Print stack trace
			e.printStackTrace();
		}

		// Indicate error
		return null;
	}

	/**
	 * Invoke a BaSys get operation via HTTP
	 */
	@Override
	public Object getModelPropertyValue(String address) {
		// Create call
		byte[] call = new byte[4 + 1 + 4 + address.length()];
		// - Encode size does not include leading four bytes
		CoderTools.setInt32(call, 0, call.length - 4);
		// - Encode operation GET
		CoderTools.setInt8(call, 4, BaSyxTCPProvider.BASYX_GET);
		// - Encode path length and path
		CoderTools.setInt32(call, 5, address.length());
		CoderTools.setString(call, 9, address);

		// Invoke BaSyx call
		byte[] rxFrame = invokeBaSyx(call);

		// Extract response
		int jsonResultLen = CoderTools.getInt32(rxFrame, 1);
		String jsonResult = new String(rxFrame, 1 + 4, jsonResultLen);

		// Return JSON value
		return JSONTools.Instance.deserialize(new JSONObject(jsonResult));
	}

	/**
	 * Invoke a Basys Set operation. Sets or overrides existing property, operation
	 * or event.
	 * 
	 * @throws ServerException
	 *             that carries the Exceptions thrown on the server
	 */
	@Override
	public void setModelPropertyValue(String servicePath, Object newValue) {
		// Serialize value
		JSONObject jsonObject = JSONTools.Instance.serialize(newValue);

		// Create call
		byte[] call = new byte[4 + 1 + 4 + servicePath.length() + 4 + jsonObject.toString().length()];
		// - Encode size does not include leading four bytes
		CoderTools.setInt32(call, 0, call.length - 4);
		// - Encode operation SET
		CoderTools.setInt8(call, 4, BaSyxTCPProvider.BASYX_SET);
		// - Encode path
		CoderTools.setInt32(call, 5, servicePath.length());
		CoderTools.setString(call, 9, servicePath);
		// - Encode value
		CoderTools.setInt32(call, 9 + servicePath.length(), jsonObject.toString().length());
		CoderTools.setString(call, 9 + servicePath.length() + 4, jsonObject.toString());

		// Invoke BaSyx call
		invokeBaSyx(call);
	}

	@Override
	public void createValue(String servicePath, Object obj) throws ServerException {
		// TODO
		throw new RuntimeException("Action is not supported yet");
	}

	@Override
	public Object invokeOperation(String servicePath, Object[] parameters) throws ServerException {
		// Create JSON value Object
		JSONObject jsonObject = JSONTools.Instance.serialize(parameters);

		// Create call
		byte[] call = new byte[4 + 1 + 4 + servicePath.length() + 4 + jsonObject.toString().length()];
		// - Encode size does not include leading four bytes
		CoderTools.setInt32(call, 0, call.length - 4);
		// - Encode operation SET
		CoderTools.setInt8(call, 4, BaSyxTCPProvider.BASYX_INVOKE);
		// - Encode path
		CoderTools.setInt32(call, 5, servicePath.length());
		CoderTools.setString(call, 9, servicePath);
		// - Encode value
		CoderTools.setInt32(call, 9 + servicePath.length(), jsonObject.toString().length());
		CoderTools.setString(call, 9 + servicePath.length() + 4, jsonObject.toString());

		// Invoke BaSyx call
		byte[] result = invokeBaSyx(call);

		// Result check
		if ((result == null) || (result.length < 2))
			return null;

		// Extract response
		int jsonResultLen = CoderTools.getInt32(result, 1);
		String jsonResult = new String(result, 1 + 4, jsonResultLen);

		// Deserialize return value if available, return null if nothing is deserialized
		// (this is the case if no value is returned)
		try {
			System.out.println("#####1####### Result" + new JSONObject(jsonResult).toString());
			Object res = JSONTools.Instance.deserialize(new JSONObject(jsonResult));

			System.out.println("#####2####### Result" + new JSONObject(jsonResult).toString());

			if (res instanceof ServerException) {

				// Throw server exception
				throw (ServerException) res;
			} else {

				// Return result
				return res;
			}

		} catch (JSONException e) {
			return null;
		}
	}

	/**
	 * Invoke a Basys operation. Deletes any resource under the given path via basyx
	 * native protocol
	 * 
	 * @throws ServerException
	 *             that carries the Exceptions thrown on the server
	 */
	@Override
	public void deleteValue(String servicePath) throws ServerException {
		// TODO
		throw new RuntimeException("Not Implemented yet");
	}

	/**
	 * Invoke a Basys oxperation. Deletes an entry from a map or collection by the
	 * given key via basyx native protocol
	 * 
	 * @throws ServerException
	 *             that carries the Exceptions thrown on the server
	 */
	@Override
	public void deleteValue(String servicePath, Object obj) throws ServerException {
		// Create JSON value Object
		JSONObject jsonObject = JSONTools.Instance.serialize(obj);

		// Create call
		byte[] call = new byte[4 + 1 + 4 + servicePath.length() + 4 + jsonObject.toString().length()];
		// - Encode size does not include leading four bytes
		CoderTools.setInt32(call, 0, call.length - 4);
		// - Encode operation SET
		CoderTools.setInt8(call, 4, BaSyxTCPProvider.BASYX_DELETE);
		// - Encode path
		CoderTools.setInt32(call, 5, servicePath.length());
		CoderTools.setString(call, 9, servicePath);
		// - Encode value
		CoderTools.setInt32(call, 9 + servicePath.length(), jsonObject.toString().length());
		CoderTools.setString(call, 9 + servicePath.length() + 4, jsonObject.toString());

		// Invoke BaSyx call
		byte[] result = invokeBaSyx(call);

		// Result check
		if ((result == null) || (result.length < 2))
			return;

		// Extract response
		int jsonResultLen = CoderTools.getInt32(result, 1);
		String jsonResult = new String(result, 1 + 4, jsonResultLen);

		// Deserialize return value if available, return null if nothing is deserialized
		// (this is the case if no value is returned)
		try {
			Object res = JSONTools.Instance.deserialize(new JSONObject(jsonResult));
			if (res instanceof ServerException) {

				// Throw server exception
				throw (ServerException) res;
			}
		} catch (JSONException e) {
			return;
		}
	}

	@Override
	public Map<String, IElementReference> getContainedElements(String path) {
		throw new RuntimeException("Not implemented yet");
	}
}
