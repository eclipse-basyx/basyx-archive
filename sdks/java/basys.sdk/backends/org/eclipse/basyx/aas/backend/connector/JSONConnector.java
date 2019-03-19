package org.eclipse.basyx.aas.backend.connector;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

import org.eclipse.basyx.aas.api.exception.ServerException;
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
		
		Object result = null;
		
		try {
			return verify(gsonObj);
		} catch (Exception e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void setModelPropertyValue(String path, Object newValue) throws Exception {

		
		Map<String, Object> gsonMap = GSONTools.Instance.serialize(newValue);
		String jsonString = GSONTools.Instance.getJsonString(gsonMap);

		Object message = provider.setModelPropertyValue(path, jsonString);

		// Deserialize
		//First get the GSON object from the JSON string
        Object gsonObj =GSONTools.Instance.getObjFromJsonStr(message.toString());
		
        verify(gsonObj);
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

		verify(gsonObj);
	}

	@Override
	public void deleteValue(String path) throws Exception {

		Object message = provider.deleteValue(path);

		// Deserialize
		//First get the GSON object from the JSON string
		Object gsonObj =GSONTools.Instance.getObjFromJsonStr(message.toString());	

        verify(gsonObj);
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

        verify(gsonObj);
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
		
		return verify(gsonObj);
	}
	
	
	public Object verify(Object gsonObj) throws Exception {
		
		Object result = null;

        if (gsonObj instanceof Map) {
			Object deserializedResult = GSONTools.Instance.deserialize((Map<String, Object>) gsonObj); // TODO: A response message should be de-serialized to IResult! Provides an interface for message verification.
			
			if (deserializedResult instanceof Map) {
				Map<String, Object> responseMap = (Map<String, Object>) deserializedResult;
				
				// Handle meta information and exceptions
				result = verifyResponse(responseMap);
				
			}
		}
        return result;
	}
	
	
	/**
	 * Verify the response header
	 * information like "success", "entityType", "messages"
	 * 
	 * @param responseMap
	 *            - provide deserialized message
	 * @return 
	 */
	private Object verifyResponse(Map<String, Object> responseMap) throws Exception {

		// Try to extract response if any
		System.out.println("Verify Response ...");
		
		// Retrieve messages if any
		Collection<Map<String, Object>> messages = (Collection<Map<String, Object>>) responseMap.get("messages");
		if (messages == null) messages = new LinkedList<Map<String, Object>>();
		
		boolean success =  (boolean) responseMap.get("success");
			
		// Return result if success
		if (success) {
			
			for (Map<String, Object> m : messages) {
				System.out.println(m.get("messageType")+ ", "+ m.get("code") + ", "+ m.get("text"));
			}
			
			Object result =  responseMap.get("entity");	
			if (result != null) return result;		
		}
		
		// Throw exception if no success
		else if (!success){
			if (responseMap.get("isException").equals(true)) {
				
				for (Map<String, Object> m : messages) {
					System.out.println(m.get("code") + ", "+ m.get("text"));
				}
			
				Map<String, Object> first = (Map<String, Object>) messages.iterator().next(); //assumes an Exception always comes with a message
				String text = (String) first.get("text");
				throw new Exception("Server threw exception: "+text); 
			} else {
				throw new Exception("Format Error: no success but isException not true or not found.");
			}
		} else {
			throw new Exception("Format Error: success not found.");
		}
		
		return null;

	}

}