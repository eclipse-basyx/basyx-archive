package org.eclipse.basyx.aas.backend.connector.basyx;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;

import org.eclipse.basyx.aas.api.exception.ServerException;
import org.eclipse.basyx.aas.backend.connector.IBasysConnector;
import org.eclipse.basyx.aas.backend.http.tools.JSONTools;
import org.eclipse.basyx.aas.backend.modelprovider.basyx.CoderTools;
import org.json.JSONException;
import org.json.JSONObject;




/**
 * BaSyx connector class
 * 
 * @author kuhn, pschorn
 *
 */
public class BaSyxConnector implements IBasysConnector {

	
	/**
	 * BaSyx get command
	 */
	public static final byte BASYX_GET = 0x01;

	/**
	 * BaSyx set command
	 */
	public static final byte BASYX_SET = 0x02;
	
	/**
	 * BaSyx set command
	 */
	public static final byte BASYX_SET_CONTAINED = 0x03;

	/**
	 * BaSyx create command
	 */
	public static final byte BASYX_CREATE = 0x04;

	/**
	 * BaSyx delete command
	 */
	public static final byte BASYX_DELETE = 0x05;
	
	/**
	 * BaSyx delete command
	 */
	public static final byte BASYX_DELETE_CONTAINED = 0x06;

	/**
	 * BaSyx invoke command
	 */
	public static final byte BASYX_INVOKE = 0x07;

	
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
			
			inputStream.close();
			
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
			CoderTools.setInt8(call, 4, BASYX_GET);
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
	 * Invoke a BaSys get operation. Retrieves the AAS, Submodel, Property, Operation or value at the given path via the basyx native protocol.
	 * @return the de-serialized ElementRef
	 */
	@Override
	public Object basysGet(String address, String servicePath) {
		// Get and deserialize property value
		Object res = JSONTools.Instance.deserialize(basysGetRaw(address, servicePath));

		// Return deserialized value
		return res;
	}

	

	
	/**
	 * Invoke a Basys Set operation. Sets or overrides existing property, operation or event.
	 * @throws ServerException that carries the Exceptions thrown on the server
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
			CoderTools.setInt8(call, 4, BASYX_SET);
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
	 * Invokes a Basys Set operation. Adds an entry to a map or collection via the basyx native protocol
	 * @throws ServerException that carries the Exceptions thrown on the server
	 */
	@Override
	public void basysSet(String address, String servicePath, Object... newValue) throws ServerException {
		try {
			// Extract host name and port
			String hostName = address.substring(0,  address.indexOf(":"));
			int    hostPort = new Integer(address.substring(address.indexOf(":")+1));
			// Create JSON value Object 
			JSONObject jsonObject = JSONTools.Instance.serialize(newValue);
			// Create TCP connection
			Socket clientSocket = new Socket(hostName, hostPort);

			
			// Create call
			byte[] call = new byte[4+1+4+servicePath.length()+4+jsonObject.toString().length()];
			// - Encode size does not include leading four bytes
			CoderTools.setInt32(call, 0, call.length-4);
			// - Encode operation SET
			CoderTools.setInt8(call, 4, BASYX_SET_CONTAINED);
			// - Encode path
			CoderTools.setInt32(call, 5, servicePath.length());
			CoderTools.setString(call, 9, servicePath);
			// - Encode value
			CoderTools.setInt32(call, 9+servicePath.length(), jsonObject.toString().length());
			CoderTools.setString(call, 9+servicePath.length()+4, jsonObject.toString());

			// Invoke BaSyx call
			byte[] result = invokeBaSyx(call, clientSocket);
			
			// Result check
			if ((result == null) || (result.length < 2)) return;
			
			// Extract response
			int    jsonResultLen = CoderTools.getInt32(result, 1);
			String jsonResult    = new String(result, 1+4, jsonResultLen);

			// Deserialize return value if available, return null if nothing is deserialized (this is the case if no value is returned)
			try {
				Object res = JSONTools.Instance.deserialize(new JSONObject(jsonResult));
				
				if (res instanceof ServerException) {
					throw (ServerException) res;
				}
			} catch (JSONException e) {
				return;
			} 
		
		} catch (IOException e) {
			// Print stack trace
			e.printStackTrace();
		}

	}

	
	/**
	 * Invoke a Basys Post operation. Creates a new Property, Operation, Event, Submodel or AAS via basyx native protocol
	 * @throws ServerException that carries the Exceptions thrown on the server
	 */
	public Object basysCreate(String address, String servicePath, Object... parameters) throws ServerException {
		
		// TODO 
		System.out.println("Action is not supported yet");
		return null;
	}
	
	

	
	/**
	 * Invoke a Basys Invoke operation. Invokes an operation on the server via the basyx native protocol
	 * @throws ServerException that carries the Exceptions thrown on the server
	 */
	public Object basysInvoke(String address, String servicePath, Object... parameters) throws ServerException {
		try {
			// Extract host name and port
			String hostName = address.substring(0,  address.indexOf(":"));
			int    hostPort = new Integer(address.substring(address.indexOf(":")+1));
			// Create JSON value Object 
			JSONObject jsonObject = JSONTools.Instance.serialize(parameters);
			// Create TCP connection
			Socket clientSocket = new Socket(hostName, hostPort);
			
			// Create call
			byte[] call = new byte[4+1+4+servicePath.length()+4+jsonObject.toString().length()];
			// - Encode size does not include leading four bytes
			CoderTools.setInt32(call, 0, call.length-4);
			// - Encode operation SET
			CoderTools.setInt8(call, 4, BASYX_INVOKE);
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
			try {
				System.out.println("#####1####### Result"+new JSONObject(jsonResult).toString()); 
				Object res = JSONTools.Instance.deserialize(new JSONObject(jsonResult)); 
				
				System.out.println("#####2####### Result"+new JSONObject(jsonResult).toString()); 
				
				if (res instanceof ServerException) {
					
					// Throw server exception
					throw (ServerException) res;
				} else {
					
					// Return result
					return res;
				}
				
			} catch (JSONException e) {return null;} 
	
		}  catch (IOException e) {
			// Print stack trace
			e.printStackTrace();
			// Indicate error
			return null;
		}
	}
	
	
	/**
	 * Invoke a Basys operation. Deletes any resource under the given path via basyx native protocol
	 * @throws ServerException that carries the Exceptions thrown on the server
	 */
	public void basysDelete(String address, String servicePath) throws ServerException {
		
		// TODO
		System.out.println("Not Implemented yet");
	}
	
	
	/**
	 * Invoke a Basys oxperation. Deletes an entry from a map or collection by the given key via basyx native protocol
	 * @throws ServerException that carries the Exceptions thrown on the server
	 */
	public void basysDelete(String address, String servicePath, Object obj) throws ServerException {
		
		// Catch exceptions
		try {
			// Extract host name and port
			String hostName = address.substring(0,  address.indexOf(":"));
			int    hostPort = new Integer(address.substring(address.indexOf(":")+1));
			// Create JSON value Object 
			JSONObject jsonObject = JSONTools.Instance.serialize(obj);
			// Create TCP connection
			Socket clientSocket = new Socket(hostName, hostPort);
			
			// Create call
			byte[] call = new byte[4+1+4+servicePath.length()+4+jsonObject.toString().length()];
			// - Encode size does not include leading four bytes
			CoderTools.setInt32(call, 0, call.length-4);
			// - Encode operation SET
			CoderTools.setInt8(call, 4, BASYX_DELETE_CONTAINED);
			// - Encode path
			CoderTools.setInt32(call, 5, servicePath.length());
			CoderTools.setString(call, 9, servicePath);
			// - Encode value
			CoderTools.setInt32(call, 9+servicePath.length(), jsonObject.toString().length());
			CoderTools.setString(call, 9+servicePath.length()+4, jsonObject.toString());
	
			// Invoke BaSyx call
			byte[] result = invokeBaSyx(call, clientSocket);
	
			// Result check
			if ((result == null) || (result.length < 2)) return;
	
			// Extract response
			int    jsonResultLen = CoderTools.getInt32(result, 1);
			String jsonResult    = new String(result, 1+4, jsonResultLen);
	
			// Deserialize return value if available, return null if nothing is deserialized (this is the case if no value is returned)
			try {
				Object res = JSONTools.Instance.deserialize(new JSONObject(jsonResult)); 
				if (res instanceof ServerException) {
			
					// Throw server exception
					throw (ServerException) res;
				} 
			} catch (JSONException e) {return ;} 

		}  catch (IOException e) {
			// Print stack trace
			e.printStackTrace();
			// Indicate error
			return;
		}
	}
	
	/**
	 * Create servicepath depending on server technology - Note: Copied from HTTP Connector
	 * @param qualifier refers to a qualifier "properties", "operations" or "events"
	 * @param path can be null if a type qualifier and submodel is specified
	 */
	public String buildPath(String aasID, String aasSubmodelID, String path, String qualifier) {
		String servicePath = aasID;
		if (aasSubmodelID!=null) {
			servicePath = servicePath + "/aas/submodels/"+aasSubmodelID;
			
			if (qualifier!=null) {
				servicePath = servicePath +  "/" + qualifier;
				
			}
			if (path!=null) {
				servicePath = servicePath + "/" + path;
			}
		} 
		
		return servicePath;
	}
}

