package org.eclipse.basyx.vab.coder.json.metaprotocol;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

import org.eclipse.basyx.vab.coder.json.serialization.DefaultTypeFactory;
import org.eclipse.basyx.vab.coder.json.serialization.GSONTools;
import org.eclipse.basyx.vab.coder.json.serialization.GSONToolsFactory;
import org.eclipse.basyx.vab.exception.provider.MalformedRequestException;
import org.eclipse.basyx.vab.exception.provider.ProviderException;
import org.eclipse.basyx.vab.exception.provider.ResourceAlreadyExistsException;
import org.eclipse.basyx.vab.exception.provider.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MetaprotocolHandler implements IMetaProtocolHandler {
	
	private static Logger logger = LoggerFactory.getLogger(MetaprotocolHandler.class);

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
	public Object deserialize(String message) throws ProviderException {

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
	private Object handleResult(Map<String, Object> responseMap) throws ProviderException {
		// Retrieve messages if any
		Collection<Map<String, Object>> messages = (Collection<Map<String, Object>>) responseMap.get(Result.MESSAGES);
		if (messages == null) messages = new LinkedList<Map<String, Object>>();
		
		Object success = responseMap.get(Result.SUCCESS);
		Object isException = responseMap.get(Result.ISEXCEPTION);
		Object result = null;

		if (success instanceof Boolean && (boolean) success) {
			for (Map<String, Object> m : messages) {
				logger.trace("{}, {}, {}", m.get(Message.MESSAGETYPE), m.get(Message.CODE), m.get(Message.TEXT));
			}
			result = responseMap.get(Result.ENTITY);
		} else if (isException instanceof Boolean && (boolean) isException) {
			Map<String, Object> first = messages.iterator().next(); // assumes an Exception always comes with a message

			String text = (String) first.get(Message.TEXT);
			
			throw getExceptionFromString(text);
			
		} else {
			throw new ProviderException("Format Error: no success but isException not true or not found.");
		}

		return result;
	}
	
	/**
	 * Creates a ProviderException from a String received form the Server</br>
	 * The String has to be formated e.g. "ResourceNotFoundException: Requested Item was not found"
	 * 
	 * @param exceptionString the String received from the server
	 * @return the matching ProviderException
	 */
	public static ProviderException getExceptionFromString(String exceptionString) {
		
		// Get the Message of the Exception (the String behind the ":")
		String message = exceptionString.substring(exceptionString.indexOf(":") + 2);
		
		// Get the correct ProviderException matching the name before the ":")
		if (exceptionString.contains(ResourceNotFoundException.class.getSimpleName())) {
			return new ResourceNotFoundException(message);
		} else if (exceptionString.contains(ResourceAlreadyExistsException.class.getSimpleName())) {
			return new ResourceAlreadyExistsException(message);
		} else if (exceptionString.contains(MalformedRequestException.class.getSimpleName())) {
			return new MalformedRequestException(message);
		} else {
			return new ProviderException("Server threw exception: " + exceptionString);
		}
	}
}
