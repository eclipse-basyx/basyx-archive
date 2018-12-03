package org.eclipse.basyx.aas.backend.connector.basyx;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;

import org.eclipse.basyx.aas.api.exception.ServerException;
import org.eclipse.basyx.aas.backend.connector.IBaSyxConnector;
import org.eclipse.basyx.aas.backend.http.tools.JSONTools;
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
	public Object getModelPropertyValue(String address) {
		// Create call
		byte[] call = new byte[4 + 1 + 4 + address.length()];
		// - Encode size does not include leading four bytes
		CoderTools.setInt32(call, 0, call.length - 4);
		// - Encode operation GET
		CoderTools.setInt8(call, 4, VABBaSyxTCPInterface.BASYX_GET);
		// - Encode path length and path
		CoderTools.setInt32(call, 5, address.length());
		CoderTools.setString(call, 9, address);

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
		// Serialize value
		JSONObject jsonObject = JSONTools.Instance.serialize(newValue);

		// Create call
		byte[] call = new byte[4 + 1 + 4 + servicePath.length() + 4 + jsonObject.toString().length()];
		// - Encode size does not include leading four bytes
		CoderTools.setInt32(call, 0, call.length - 4);
		// - Encode operation SET
		CoderTools.setInt8(call, 4, VABBaSyxTCPInterface.BASYX_SET);
		// - Encode path
		CoderTools.setInt32(call, 5, servicePath.length());
		CoderTools.setString(call, 9, servicePath);
		// - Encode value
		CoderTools.setInt32(call, 9 + servicePath.length(), jsonObject.toString().length());
		CoderTools.setString(call, 9 + servicePath.length() + 4, jsonObject.toString());

		// Invoke BaSyx call and return result
		return invokeBaSyx(call);
	}

	@Override
	public Object createValue(String servicePath, JSONObject obj) throws ServerException {
		// TODO
		throw new RuntimeException("Action is not supported yet");
	}

	@Override
	public Object invokeOperation(String servicePath, JSONObject parameters) throws ServerException {

		// Create call
		byte[] call = new byte[4 + 1 + 4 + servicePath.length() + 4 + parameters.toString().length()];
		// - Encode size does not include leading four bytes
		CoderTools.setInt32(call, 0, call.length - 4);
		// - Encode operation SET
		CoderTools.setInt8(call, 4, VABBaSyxTCPInterface.BASYX_INVOKE);
		// - Encode path
		CoderTools.setInt32(call, 5, servicePath.length());
		CoderTools.setString(call, 9, servicePath);
		// - Encode value
		CoderTools.setInt32(call, 9 + servicePath.length(), parameters.toString().length());
		CoderTools.setString(call, 9 + servicePath.length() + 4, parameters.toString());

		// Invoke BaSyx call and return result
		return invokeBaSyx(call);

	}

	/**
	 * Invoke a Basys operation. Deletes any resource under the given path
	 * 
	 * @throws ServerException
	 *             that carries the Exceptions thrown on the server
	 */
	@Override
	public Object deleteValue(String servicePath) throws ServerException {
		// TODO
		throw new RuntimeException("Not Implemented yet");
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

		// Create call
		byte[] call = new byte[4 + 1 + 4 + servicePath.length() + 4 + jsonObject.toString().length()];
		// - Encode size does not include leading four bytes
		CoderTools.setInt32(call, 0, call.length - 4);
		// - Encode operation SET
		CoderTools.setInt8(call, 4, VABBaSyxTCPInterface.BASYX_DELETE);
		// - Encode path
		CoderTools.setInt32(call, 5, servicePath.length());
		CoderTools.setString(call, 9, servicePath);
		// - Encode value
		CoderTools.setInt32(call, 9 + servicePath.length(), jsonObject.toString().length());
		CoderTools.setString(call, 9 + servicePath.length() + 4, jsonObject.toString());

		// Invoke BaSyx call and return result
		return invokeBaSyx(call);
	}

	@Override
	public Object getContainedElements(String path) {
		throw new RuntimeException("Not implemented yet");
	}
}
