package org.eclipse.basyx.aas.backend.connector.basyx;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;

import org.eclipse.basyx.aas.backend.connector.IBasysConnector;
import org.eclipse.basyx.aas.backend.http.tools.JSONTools;
import org.eclipse.basyx.aas.backend.modelprovider.basyx.CoderTools;
import org.json.JSONException;
import org.json.JSONObject;




/**
 * BaSyx connector class
 * 
 * @author kuhn
 *
 */
public class BaSyxConnector implements IBasysConnector {

	
	
	/**
	 * Invoke a BaSyx operation in a remote provider
	 */
	protected byte[] invokeBaSyx(byte[] call, Socket clientSocket) {
		// Catch exceptions
		try {
			// Input and output streams
			BufferedInputStream inputStream  = new BufferedInputStream(clientSocket.getInputStream());

			// Send BaSyx operation
			clientSocket.getOutputStream().write(call);

			// Wait for response
			// - Wait for leading 4 byte header that contains frame length
			while (inputStream.available() < 4) Thread.sleep(1);

			// Get frame size
			byte[] lengthBytes = new byte[4];
			inputStream.read(lengthBytes, 0, 4);
			int frameSize = CoderTools.getInt32(lengthBytes, 0);

			// Wait for frame to arrive
			while (inputStream.available() < frameSize) Thread.sleep(1);
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
	public JSONObject basysGetRaw(String address, String servicePath) {
		// Catch exceptions
		try {
			// Extract host name and port
			String hostName = address.substring(0,  address.indexOf(":"));
			int    hostPort = new Integer(address.substring(address.indexOf(":")+1));

			// Debug output
			System.out.println("basysGet "+servicePath);
			System.out.println("Addr:"+hostName+"  Port:"+hostPort+"  Path:"+servicePath);

			// Create TCP connection
			Socket clientSocket = new Socket(hostName, hostPort);

			// Create call
			byte[] call = new byte[4+1+4+servicePath.length()];
			// - Encode size does not include leading four bytes
			CoderTools.setInt32(call, 0, call.length-4);
			// - Encode operation GET
			CoderTools.setInt8(call, 4, 0x01);
			// - Encode path length and path
			CoderTools.setInt32(call, 5, servicePath.length());
			CoderTools.setString(call, 9, servicePath);

			// Invoke BaSyx call
			byte[] rxFrame = invokeBaSyx(call, clientSocket);

			// Extract response
			int    jsonResultLen = CoderTools.getInt32(rxFrame, 1);
			String jsonResult    = new String(rxFrame, 1+4, jsonResultLen);

			// Return JSON value
			return new JSONObject(jsonResult);
		} catch (IOException e) {
			// Print stack trace
			e.printStackTrace();
		}
		
		// Indicate error
		return null;
	}

	
	/**
	 * Invoke a BaSys get operation via native BaSyx protocol
	 */
	@Override
	public Object basysGet(String address, String servicePath) {
		// Get and deserialize property value
		Object res = JSONTools.Instance.deserialize(basysGetRaw(address, servicePath));

		// Return deserialized value
		return res;
	}

	

	
	/**
	 * Invoke a BaSys set operation via HTTP
	 */
	@Override
	public void basysSet(String address, String servicePath, Object newValue) {
		// Catch exceptions
		try {
	
			// Extract host name and port
			String hostName = address.substring(0,  address.indexOf(":"));
			int    hostPort = new Integer(address.substring(address.indexOf(":")+1));

			// Serialize value
			JSONObject jsonObject = JSONTools.Instance.serialize(newValue);


			// Debug output
			System.out.println("basysSet "+servicePath);
			System.out.println("Addr:"+hostName+"  Port:"+hostPort+"  Path:"+servicePath);

			// Create TCP connection
			Socket clientSocket = new Socket(hostName, hostPort);


			// Create call
			byte[] call = new byte[4+1+4+servicePath.length()+4+jsonObject.toString().length()];
			// - Encode size does not include leading four bytes
			CoderTools.setInt32(call, 0, call.length-4);
			// - Encode operation SET
			CoderTools.setInt8(call, 4, 0x02);
			// - Encode path
			CoderTools.setInt32(call, 5, servicePath.length());
			CoderTools.setString(call, 9, servicePath);
			// - Encode value
			CoderTools.setInt32(call, 9+servicePath.length(), jsonObject.toString().length());
			CoderTools.setString(call, 9+servicePath.length()+4, jsonObject.toString());

			// Invoke BaSyx call
			invokeBaSyx(call, clientSocket);
		
		} catch (IOException e) {
			// Print stack trace
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Invoke a BaSys post operation via HTTP
	 * @param action may be "invoke", "create" or "delete"
	 */
	@Override
	public Object basysPost(String address, String servicePath, String action, Object... newValue) {
		// Catch exceptions
		try {
			// Extract host name and port
			String hostName = address.substring(0,  address.indexOf(":"));
			int    hostPort = new Integer(address.substring(address.indexOf(":")+1));
			// Create JSON value Object 
			JSONObject jsonObject = JSONTools.Instance.serialize(newValue);
			// Create TCP connection
			Socket clientSocket = new Socket(hostName, hostPort);

			
			// Action "create"
			if (action.equals("create")) {
				// Create call
				byte[] call = new byte[4+1+4+servicePath.length()+4+jsonObject.toString().length()];
				// - Encode size does not include leading four bytes
				CoderTools.setInt32(call, 0, call.length-4);
				// - Encode operation SET
				CoderTools.setInt8(call, 4, 0x03);
				// - Encode path
				CoderTools.setInt32(call, 5, servicePath.length());
				CoderTools.setString(call, 9, servicePath);
				// - Encode value
				CoderTools.setInt32(call, 9+servicePath.length(), jsonObject.toString().length());
				CoderTools.setString(call, 9+servicePath.length()+4, jsonObject.toString());

				// Invoke BaSyx call
				byte[] result = invokeBaSyx(call, clientSocket);
				
				// Result check
				if ((result == null) || (result.length < 2)) return null;
				
				// Extract response
				int    jsonResultLen = CoderTools.getInt32(result, 1);
				String jsonResult    = new String(result, 1+4, jsonResultLen);

				// Deserialize return value if available, return null if nothing is deserialized (this is the case if no value is returned)
				try {Object res = JSONTools.Instance.deserialize(new JSONObject(jsonResult)); return res;} catch (JSONException e) {return null;} 
			}

			
			// Action "delete"
			if (action.equals("delete")) {
				// Create call
				byte[] call = new byte[4+1+4+servicePath.length()+4+jsonObject.toString().length()];
				// - Encode size does not include leading four bytes
				CoderTools.setInt32(call, 0, call.length-4);
				// - Encode operation SET
				CoderTools.setInt8(call, 4, 0x04);
				// - Encode path
				CoderTools.setInt32(call, 5, servicePath.length());
				CoderTools.setString(call, 9, servicePath);
				// - Encode value
				CoderTools.setInt32(call, 9+servicePath.length(), jsonObject.toString().length());
				CoderTools.setString(call, 9+servicePath.length()+4, jsonObject.toString());

				// Invoke BaSyx call
				byte[] result = invokeBaSyx(call, clientSocket);

				// Result check
				if ((result == null) || (result.length < 2)) return null;

				// Extract response
				int    jsonResultLen = CoderTools.getInt32(result, 1);
				String jsonResult    = new String(result, 1+4, jsonResultLen);

				// Deserialize return value if available, return null if nothing is deserialized (this is the case if no value is returned)
				try {Object res = JSONTools.Instance.deserialize(new JSONObject(jsonResult)); return res;} catch (JSONException e) {return null;} 
			}

			
			// Action "invoke"
			if (action.equals("invoke")) {
				// Create call
				byte[] call = new byte[4+1+4+servicePath.length()+4+jsonObject.toString().length()];
				// - Encode size does not include leading four bytes
				CoderTools.setInt32(call, 0, call.length-4);
				// - Encode operation SET
				CoderTools.setInt8(call, 4, 0x05);
				// - Encode path
				CoderTools.setInt32(call, 5, servicePath.length());
				CoderTools.setString(call, 9, servicePath);
				// - Encode value
				CoderTools.setInt32(call, 9+servicePath.length(), jsonObject.toString().length());
				CoderTools.setString(call, 9+servicePath.length()+4, jsonObject.toString());

				// Invoke BaSyx call
				byte[] result = invokeBaSyx(call, clientSocket);
				
				// Result check
				if ((result == null) || (result.length < 2)) return null;

				// Extract response
				int    jsonResultLen = CoderTools.getInt32(result, 1);
				String jsonResult    = new String(result, 1+4, jsonResultLen);

				// Deserialize return value if available, return null if nothing is deserialized (this is the case if no value is returned)
				try {System.out.println("#####1####### Result"+new JSONObject(jsonResult).toString()); Object res = JSONTools.Instance.deserialize(new JSONObject(jsonResult)); System.out.println("#####2####### Result"+new JSONObject(jsonResult).toString()); return res;} catch (JSONException e) {return null;} 
			}
			
			
			// Notify about unknown request
			throw new RuntimeException("BaSyx request not supported");

		} catch (IOException e) {
			// Print stack trace
			e.printStackTrace();
		}
		
		// Indicate error
		return null;
	}
	
	

	
	/**
	 * Invoke a BaSys invoke operation via HTTP
	 */
	@Override
	public Object basysInvoke(String address, String servicePath, Object... newValue) {
		// Invoke operation
		return basysPost(address, servicePath, "invoke", newValue);
	 }
}

