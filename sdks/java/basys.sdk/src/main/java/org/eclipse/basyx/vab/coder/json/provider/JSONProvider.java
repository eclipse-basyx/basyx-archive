package org.eclipse.basyx.vab.coder.json.provider;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;

import org.eclipse.basyx.vab.coder.json.metaprotocol.IMessage;
import org.eclipse.basyx.vab.coder.json.metaprotocol.IResult;
import org.eclipse.basyx.vab.coder.json.metaprotocol.Result;
import org.eclipse.basyx.vab.coder.json.serialization.DefaultTypeFactory;
import org.eclipse.basyx.vab.coder.json.serialization.GSONTools;
import org.eclipse.basyx.vab.coder.json.serialization.GSONToolsFactory;
import org.eclipse.basyx.vab.exception.LostHTTPRequestParameterException;
import org.eclipse.basyx.vab.exception.ServerException;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * Provider class that supports JSON serialized communication <br/>
 * Generic Caller is required since messages can be technology specific.
 * 
 * 
 * @author pschorn, schnicke, kuhn
 *
 */
public class JSONProvider<ModelProvider extends IModelProvider> {
	
	private static Logger logger = LoggerFactory.getLogger(JSONProvider.class);

	
	/**
	 * Reference to IModelProvider backend
	 */
	protected ModelProvider providerBackend = null;
	
	
	/**
	 * Reference to serializer / deserializer
	 */
	protected GSONTools serializer = null;
	

	
	/**
	 * Constructor
	 */
	public JSONProvider(ModelProvider modelProviderBackend) {
		// Store reference to backend
		providerBackend = modelProviderBackend;
		
		// Create GSON serializer
		serializer = new GSONTools(new DefaultTypeFactory());
	}


	/**
	 * Constructor
	 */
	public JSONProvider(ModelProvider modelProviderBackend, GSONToolsFactory factory) {
		// Store reference to backend
		providerBackend = modelProviderBackend;
		
		// Create GSON serializer
		serializer = new GSONTools(factory);
	}
	
	
	/**
	 * Get serializer reference
	 */
	public GSONTools getSerializerReference() {
		return serializer;
	}
	

	/**
	 * Get backend reference
	 */
	public ModelProvider getBackendReference() {
		return providerBackend;
	}
	
	
	/**
	 * Wrap object with meta-protocol and return serialized string 
	 */
	private String serialize(boolean success, Object entity, List<IMessage> messages) {
		
		// Wrap the entity in the meta-protocol
		IResult result = new Result(success, entity, messages);
		
		// Serialize the whole thing
		return serialize(result);
	}
	
	
	/**
	 * Acknowledges successful operation without entity body
	 * @param success
	 * @return
	 */
	private String serialize(boolean success) {
		
		// Create Ack
		IResult result = new Result(success);
		
		// Serialize the whole thing
		return serialize(result);
	}
	
	
	/**
	 * Marks success as false and delivers exception cause messages 
	 * @param e
	 * @return
	 */
	private String serialize(Exception e) {
		// Create Ack
		IResult result = new Result(e);
		
		// Serialize the whole thing
		return serialize(result);
	}
	
	
	/**
	 * Serialize IResult (HashMap)
	 * @param string
	 * @return
	 */
	private String serialize(IResult string) {
		// Serialize the whole thing
		return serializer.serialize(string);
	}
	

	/**
	 * Send JSON encoded response
	 */
	private void sendJSONResponse(PrintWriter outputStream, String jsonValue) {
		// Write result to output stream
		outputStream.write(jsonValue); 
		outputStream.flush();
	}
	

	/**
	 * Send Error
	 * @param e
	 * @param path
	 * @param resp
	 */
	private void sendException(PrintWriter resp, Exception e) {
		logger.error("Exception in sendException", e);
		
		// Serialize Exception
		String jsonString = serialize(e);

		// Send error response
		sendJSONResponse(resp, jsonString);
	}

	
	/**
	 * Extracts parameter from JSON and handles de-serialization errors
	 * 
	 * @param path
	 * @param serializedJSONValue
	 * @param outputStream
	 * @return
	 * @throws LostHTTPRequestParameterException 
	 * @throws ServerException
	 */
	private Object extractParameter(String path, String serializedJSONValue, PrintWriter outputStream) {
		// Return value
		Object result = null;

		// Deserialize json body
		result = serializer.deserialize(serializedJSONValue);
			
		return result;
	}
	

	/**
	 * Process a BaSys get operation, return JSON serialized result
	 */
	public void processBaSysGet(String path, PrintWriter outputStream) {

		try {
			// Get requested value from provider backend
			Object value = providerBackend.getModelPropertyValue(path);

			// Serialize as json string - any messages?
			String jsonString = serialize(true, value, null);

			// Send response
			sendJSONResponse(outputStream, jsonString);
		} catch (Exception e) {
			sendException(outputStream, e);
		}
	}

	
	/**
	 * Process a BaSys set operation
	 * 
	 * @param path
	 * @param serializedJSONValue
	 * @param outputStream
	 */
	public void processBaSysSet(String path, String serializedJSONValue, PrintWriter outputStream) {
		
		// Try to set value of BaSys VAB element
		try {
			
			// Deserialize json body. If parameter is not ex
			Object parameter = extractParameter(path, serializedJSONValue, outputStream);

			// Set the value of the element
			providerBackend.setModelPropertyValue(path, parameter);

			// Create serialized acknowledgement 
			String jsonString = serialize(true);
			
			// Send response
			sendJSONResponse(outputStream, jsonString);

		} catch (Exception e) {
			sendException(outputStream, e);
		}
	}

	
	/**
	 * Process a BaSys invoke operation
	 */
	@SuppressWarnings("unchecked")
	public void processBaSysInvoke(String path, String serializedJSONValue, PrintWriter outputStream) {

		try {
			
			// Deserialize json body. 
			Object parameter = extractParameter(path, serializedJSONValue, outputStream);
			
			// If only a single parameter has been sent, pack it into an array so it can be
			// casted safely
			if (parameter instanceof Collection<?>) {
				Collection<Object> list = (Collection<Object>) parameter;
				Object[] parameterArray = new Object[list.size()];
				int i = 0;
				for (Object o : list) {
					parameterArray[i] = o;
					i++;
				}
				parameter = parameterArray;
			}
			
			if (!(parameter instanceof Object[])) {
				Object[] parameterArray = new Object[1];
				Object tmp = parameter;
				parameterArray[0] = tmp;
				parameter = parameterArray;
			}

			Object result = providerBackend.invokeOperation(path, (Object[]) parameter);

			// Serialize result as json string - are there any messages?
			String jsonString = serialize(true, result, null);
			
			// Send response
			sendJSONResponse(outputStream, jsonString);

		} catch (Exception e) {
			sendException(outputStream, e);
		}
	}

	
	/**
	 * Implement "Delete" operation. Deletes any resource under the given path.
	 *
	 * @param path
	 * @param serializedJSONValue If this parameter is not null (basystype),we remove an element from a collection by index / remove from map by key. We assume that the parameter only contains 1 element
	 * @param outputStream
	 */
	public void processBaSysDelete(String path, String serializedJSONValue, PrintWriter outputStream) {
		
		try {

			// Deserialize json body. If parameter is not ex
			Object parameter = extractParameter(path, serializedJSONValue, outputStream);
			
			// Process delete request with or without argument
			if (parameter == null) {
				this.providerBackend.deleteValue(path);
			} else {
				this.providerBackend.deleteValue(path, parameter);
			}

			// Create serialized acknowledgement 
			String jsonString = serialize(true);
			
			// Send response
			sendJSONResponse(outputStream, jsonString);

		} catch (Exception e) {
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
			// Deserialize json body. 
			Object parameter = extractParameter(path, serializedJSONValue, outputStream);
						

			providerBackend.createValue(path, parameter);

			// Create serialized acknowledgement 
			String jsonString = serialize(true);
			
			// Send response
			sendJSONResponse(outputStream, jsonString);

		} catch (Exception e) {
			sendException(outputStream, e);
		}
	}
}
