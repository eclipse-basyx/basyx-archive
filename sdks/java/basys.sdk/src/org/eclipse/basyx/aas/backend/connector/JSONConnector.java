package org.eclipse.basyx.aas.backend.connector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.basyx.aas.backend.http.tools.GSONTools;
import org.eclipse.basyx.aas.backend.http.tools.factory.DefaultTypeFactory;
import org.eclipse.basyx.aas.backend.http.tools.factory.GSONToolsFactory;
import org.eclipse.basyx.vab.core.IModelProvider;


/**
 * Connector Class responsible for serializing parameters and de-serializing
 * results. It verifies the results, removes the message header and returns the
 * requested entity.
 * 
 * @author pschorn
 *
 */
public class JSONConnector implements IModelProvider {

	
	/**
	 * Reference to Connector backend
	 */
	protected IBaSyxConnector provider = null;
	
	
	/**
	 * Reference to serializer / deserializer
	 */
	protected GSONTools serializer = null;
	
	
	
	
	/**
	 * Constructor
	 * 
	 * @param provider
	 */
	public JSONConnector(IBaSyxConnector provider) {
		// Store provider backend
		this.provider = provider;
		
		// Create GSON serializer
		serializer = new GSONTools(new DefaultTypeFactory());
	}

	
	/**
	 * Constructor that accepts specific factory for serializer
	 * 
	 * @param provider
	 */
	public JSONConnector(IBaSyxConnector provider, GSONToolsFactory factory) {
		// Store provider backend
		this.provider = provider;
		
		// Create GSON serializer
		serializer = new GSONTools(factory);
	}

	

	@Override
	public Object getModelPropertyValue(String path) { // shouldn't GET throw an exception too?

		// Get element from server
		String message = provider.getModelPropertyValue(path);

		try {
			// De-serialize and verify
			return verify(message);

		} catch (Exception e) {
			e.printStackTrace(); // TODO throw exception?
		}
		return null;
	}

	@Override
	public void setModelPropertyValue(String path, Object newValue) throws Exception {

		// Serialize value Object
		String jsonString = serializer.serialize(newValue);

		String message = provider.setModelPropertyValue(path, jsonString);

		// De-serialize and verify
		verify(message);
	}

	@Override
	public void createValue(String path, Object newEntity) throws Exception {

		// Serialize value Object
		String jsonString = serializer.serialize(newEntity);

		String message = provider.createValue(path, jsonString);

		// De-serialize and verify
		verify(message);
	}

	@Override
	public void deleteValue(String path) throws Exception {

		String message = provider.deleteValue(path);

		// De-serialize and verify
		verify(message);
	}

	@Override
	public void deleteValue(String path, Object obj) throws Exception {

		// Serialize parameter
		String jsonString = serializer.serialize(obj);

		String message = provider.deleteValue(path, jsonString);

		// De-serialize and verify
		verify(message);
	}

	@Override
	public Object invokeOperation(String path, Object[] parameter) throws Exception {

		// Serialize parameter
		List<Object> params = new ArrayList<>();
		for (Object o : parameter) {
			params.add(o);
		}

		String jsonString = serializer.serialize(params);

		String message = provider.invokeOperation(path, jsonString);

		// De-serialize and verify
		return verify(message);
	}
	
	

	@SuppressWarnings("unchecked")
	public Object verify(String message) throws Exception {

		// First get the GSON object from the JSON string
		Object gsonObj = serializer.deserialize(message.toString());
		
		Object result = null;

		if (gsonObj instanceof Map) {
			Map<String, Object> responseMap = (Map<String, Object>) gsonObj;
				
				// Handle meta information and exceptions
				result = verifyResponse(responseMap);
				
			}
        return result;
	}
	
	
	/**
	 * Verify the response header and try to extract response if any. Process
	 * information of "success", "entityType" and "messages"
	 * 
	 * @param responseMap
	 *            - provide deserialized message
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Object verifyResponse(Map<String, Object> responseMap) throws Exception {

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
				Map<String, Object> first = messages.iterator().next(); //assumes an Exception always comes with a message
				
				String text = (String) first.get("text");
				throw new Exception("Server threw exception: " + text);
			} else {
				throw new Exception("Format Error: no success but isException not true or not found.");
			}
		} else {
			throw new Exception("Format Error: success not found.");
		}
		
		return null;

	}

}