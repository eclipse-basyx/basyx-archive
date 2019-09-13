package org.eclipse.basyx.vab.backend.connector;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

import org.eclipse.basyx.vab.backend.http.tools.GSONTools;
import org.eclipse.basyx.vab.backend.http.tools.factory.DefaultTypeFactory;
import org.eclipse.basyx.vab.backend.http.tools.factory.GSONToolsFactory;
import org.eclipse.basyx.vab.backend.server.utils.Message;
import org.eclipse.basyx.vab.backend.server.utils.Result;

public class MetaprotocolHandler implements IMetaProtocolHandler {

	/**
	 * Reference to serializer / deserializer
	 */
	protected GSONTools serializer = null;
	
	/**
	 * Constructor that create the serializer
	 * 
	 */
	public MetaprotocolHandler() {
		// Create GSON serializer
		serializer = new GSONTools(new DefaultTypeFactory());
	}
	
	/**
	 * Constructor that accepts specific factory for serializer
	 * @param factory
	 */
	public MetaprotocolHandler(GSONToolsFactory factory) {
		// Create GSON serializer
		serializer = new GSONTools(factory);
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public Object deserialize(String message) throws Exception {

		// First get the GSON object from the JSON string
		Object gsonObj = serializer.deserialize(message.toString());
		
		// Then interpret and verify the result object
		Object result = null;
		if (gsonObj instanceof Map) {
			Map<String, Object> responseMap = (Map<String, Object>) gsonObj;
				
			// Handle meta information and exceptions
			result = handleResult(responseMap);
		}
        return result;
	}
	
	
	/**
	 * Verify the Result and try to extract the entity if available. Process
	 * information of "success", "entityType" and "messages"
	 * 
	 * @param responseMap
	 *            - provide deserialized message
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Object handleResult(Map<String, Object> responseMap) throws Exception {
		// Retrieve messages if any
		Collection<Map<String, Object>> messages = (Collection<Map<String, Object>>) responseMap.get(Result.MESSAGES);
		if (messages == null) messages = new LinkedList<Map<String, Object>>();
		
		Object success = responseMap.get(Result.SUCCESS);
		Object isException = responseMap.get(Result.ISEXCEPTION);
		Object result = null;

		if (success instanceof Boolean && (boolean) success) {
			for (Map<String, Object> m : messages) {
				System.out
						.println(m.get(Message.MESSAGETYPE) + ", " + m.get(Message.CODE) + ", " + m.get(Message.TEXT));
			}
			result = responseMap.get(Result.ENTITY);
		} else if (isException instanceof Boolean && (boolean) isException) {
			Map<String, Object> first = messages.iterator().next(); // assumes an Exception always comes with a message
				
			String text = (String) first.get(Message.TEXT);
			throw new Exception("Server threw exception: " + text);
		} else {
			throw new Exception("Format Error: no success but isException not true or not found.");
		}

		return result;
	}
}
