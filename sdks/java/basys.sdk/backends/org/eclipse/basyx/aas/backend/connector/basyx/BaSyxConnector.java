package org.eclipse.basyx.aas.backend.connector.basyx;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;

import org.eclipse.basyx.aas.api.exception.ServerException;
import org.eclipse.basyx.aas.backend.connector.IBaSyxConnector;
import org.eclipse.basyx.vab.backend.server.basyx.CoderTools;
import org.eclipse.basyx.vab.backend.server.basyx.VABBaSyxTCPInterface;
import org.json.JSONObject;

/**
 * BaSyx connector class
 * 
 * @author kuhn, pschorn, schnicke
 *
 */
public class BaSyxConnector implements IBaSyxConnector {

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
	protected String invokeBaSyx(byte[] call) {
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

			// Result check
			if ((rxFrame == null) || (rxFrame.length < 2))
				return null;

			// Extract response
			int jsonResultLen = CoderTools.getInt32(rxFrame, 1);
			String jsonResult = new String(rxFrame, 1 + 4, jsonResultLen);

			return jsonResult.toString();

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
	public Object getModelPropertyValue(String servicePath) {

		byte[] call = createCall(servicePath, VABBaSyxTCPInterface.BASYX_GET);

		// Invoke BaSyx call and return result
		return invokeBaSyx(call);
	}

	/**
	 * Invoke a Basys Set operation. Sets or overrides existing property, operation
	 * or event.
	 * 
	 * @throws ServerException
	 *             that carries the Exceptions thrown on the server
	 */
	@Override
	public Object setModelPropertyValue(String servicePath, JSONObject newValue) {

		byte[] call = createCall(servicePath, newValue, VABBaSyxTCPInterface.BASYX_SET);

		// Invoke BaSyx call and return result
		return invokeBaSyx(call);
	}

	/**
	 * Invoke a BaSys Create operation
	 */
	@Override
	public Object createValue(String servicePath, JSONObject newValue) throws ServerException {

		byte[] call = createCall(servicePath, newValue, VABBaSyxTCPInterface.BASYX_CREATE);

		// Invoke BaSyx call and return result
		return invokeBaSyx(call);
	}

	/**
	 * Invoke a Basys invoke operation.
	 */
	@Override
	public Object invokeOperation(String servicePath, JSONObject parameters) throws ServerException {

		byte[] call = createCall(servicePath, parameters, VABBaSyxTCPInterface.BASYX_INVOKE);

		// Invoke BaSyx call and return result
		return invokeBaSyx(call);

	}

	/**
	 * Invoke a Basys delete operation. Deletes any resource under the given path
	 * 
	 * @throws ServerException
	 *             that carries the Exceptions thrown on the server
	 */
	@Override
	public Object deleteValue(String servicePath) throws ServerException {

		byte[] call = createCall(servicePath, VABBaSyxTCPInterface.BASYX_DELETE);

		// Invoke BaSyx call and return result
		return invokeBaSyx(call);
	}

	/**
	 * Invoke a Basys delete operation. Deletes an entry from a map or collection by
	 * the given key
	 * 
	 * @throws ServerException
	 *             that carries the Exceptions thrown on the server
	 */
	@Override
	public Object deleteValue(String servicePath, JSONObject jsonObject) throws ServerException {

		byte[] call = createCall(servicePath, jsonObject, VABBaSyxTCPInterface.BASYX_DELETE);

		// Invoke BaSyx call and return result
		return invokeBaSyx(call);
	}

	/**
	 * Create non-parameterized call that can be used as an argument to the
	 * invokeBaSyx function
	 * 
	 * @param servicePath
	 * @param jsonObject
	 * @return
	 */
	private byte[] createCall(String servicePath, byte callType) {
		// Create call
		byte[] call = new byte[4 + 1 + 4 + servicePath.length()];
		// - Encode size does not include leading four bytes
		CoderTools.setInt32(call, 0, call.length - 4);
		// - Encode operation GET
		CoderTools.setInt8(call, 4, callType);
		// - Encode path length and path
		CoderTools.setInt32(call, 5, servicePath.length());
		CoderTools.setString(call, 9, servicePath);

		return call;
	}

	/**
	 * Create parameterized byte call that can be used as an argument to the
	 * invokeBaSyx function
	 * 
	 * @param servicePath
	 * @param jsonObject
	 * @param callType
	 * @return
	 */
	private byte[] createCall(String servicePath, JSONObject jsonObject, byte callType) {

		// Create call
		byte[] call = new byte[4 + 1 + 4 + servicePath.length() + 4 + jsonObject.toString().length()];
		// - Encode size does not include leading four bytes
		CoderTools.setInt32(call, 0, call.length - 4);
		// - Encode operation SET
		CoderTools.setInt8(call, 4, callType);
		// - Encode path
		CoderTools.setInt32(call, 5, servicePath.length());
		CoderTools.setString(call, 9, servicePath);
		// - Encode value
		CoderTools.setInt32(call, 9 + servicePath.length(), jsonObject.toString().length());
		CoderTools.setString(call, 9 + servicePath.length() + 4, jsonObject.toString());

		return call;
	}

	@Override
	public Object getContainedElements(String path) {
		throw new RuntimeException("Not implemented yet");
	}
}
