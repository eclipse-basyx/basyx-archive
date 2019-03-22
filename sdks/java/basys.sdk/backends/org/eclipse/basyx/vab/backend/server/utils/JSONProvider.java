package org.eclipse.basyx.vab.backend.server.utils;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import org.eclipse.basyx.aas.api.exception.LostHTTPRequestParameterException;
import org.eclipse.basyx.aas.api.exception.ServerException;
import org.eclipse.basyx.aas.backend.http.tools.GSONTools;
import org.eclipse.basyx.vab.core.IModelProvider;
import org.eclipse.basyx.vab.core.tools.VABPathTools;

/**
 * Provider class that supports JSON serialized communication <br/>
 * Generic Caller is required since messages can be technology specific.
 * 
 * 
 * @author pschorn, schnicke, kuhn
 *
 */
public class JSONProvider<ModelProvider extends IModelProvider> {
	
	private static String delimiter = "--------------------";
	

	/**
	 * Reference to IModelProvider backend
	 */
	protected ModelProvider providerBackend = null;

	/**
	 * Constructor
	 */
	public JSONProvider(ModelProvider modelProviderBackend) {
		// Store reference to backend
		providerBackend = modelProviderBackend;
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
	private String serialize(boolean success, Object entity,  Class<?> entityType, List<IMessage> messages) {
		
		// Wrap the entity in the meta-protocol
		IResult result = new Result(success, entity, entityType, messages);
		
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
		Map<String, Object> gsonObj = GSONTools.Instance.serialize(string);
		return GSONTools.Instance.getJsonString(gsonObj);
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
		e.printStackTrace();
		
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
	@SuppressWarnings("unchecked")
	private Object extractParameter(String path, String serializedJSONValue, PrintWriter outputStream) {
		// Return value
		Object result = null;

		// Deserialize json body
		Object gsonObj =GSONTools.Instance.getObjFromJsonStr(serializedJSONValue);	
		result = GSONTools.Instance.deserialize((Map<String, Object>) gsonObj);
			
		return result;
	}

	/**
	 * Process a BaSys get operation, return JSON serialized result
	 */
	public void processBaSysGet(String path, PrintWriter outputStream) {

		try {
			// Get requested value from provider backend
			Object value = providerBackend.getModelPropertyValue(path);

			// Serialize as json string
			String jsonString = serialize(true, value, (value == null ? null : value.getClass()), null); // any
																											// messages?

			// Send response
			sendJSONResponse(outputStream, jsonString);
			
		} catch (Exception e) {
			sendException(outputStream, e);		// FIXME: There is no exception thrown for GET requests on the client!!
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
	 * Process a BaSys invoke or create operation FIXME this is a HTTP specific function
	 * 
	 * @param path
	 * @param serializedJSONValue
	 * @param outputStream
	 */
	public void processBaSysPost(String path, String serializedJSONValue, PrintWriter outputStream) {

		// Invoke provider backend
		try {
			// Check if request is for property creation or operation invoke
			if (VABPathTools.isOperationPath(path)) {

				// Invoke BaSys VAB 'invoke' primitive
				processBaSysInvoke(path, serializedJSONValue, outputStream);

			} else {
				// Invoke the BaSys 'create' primitive
				processBaSysCreate(path, serializedJSONValue, outputStream);
			}
		} catch (Exception e) {
			sendException(outputStream, e);
		}

	}

	/**
	 * Process a BaSys invoke operation
	 */
	public void processBaSysInvoke(String path, String serializedJSONValue, PrintWriter outputStream) {

		try {
			
			// Deserialize json body. 
			Object parameter = extractParameter(path, serializedJSONValue, outputStream);
			
			// If only a single parameter has been sent, pack it into an array so it can be casted safely ------- FIXME Parameters should actually be a List of Hashmaps (see VWiD json)
			if (!(parameter instanceof Object[])) {
				Object[] parameterArray = new Object[1];
				Object tmp = parameter;
				parameterArray[0] = tmp;
				parameter = parameterArray;
			}
			
			Object result = providerBackend.invokeOperation(path, (Object[]) parameter);

			// Serialize result as json string
			String jsonString = serialize(true, result, result.getClass(), null); // any messages?
			
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
