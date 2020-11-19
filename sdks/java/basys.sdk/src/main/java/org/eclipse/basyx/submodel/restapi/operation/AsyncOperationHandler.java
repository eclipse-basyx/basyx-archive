package org.eclipse.basyx.submodel.restapi.operation;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.AsyncInvocation;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.OperationExecutionErrorException;
import org.eclipse.basyx.vab.exception.provider.ResourceNotFoundException;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;

/**
 * Helperclass used to keep the asynchronously operations
 * This class is used in a static way. As each request is handled by new Providers
 * 
 * @author conradi
 *
 */
public class AsyncOperationHandler {

	private static int count = 0;
	
	private static Map<String, AsyncInvocation> map = new HashMap<>();
	
	/**
	 * Invokes an Operation and returns its requestId
	 * 
	 * @param operation the Operation to be invoked
	 * @param parameters to be given to the operation
	 * @return the requestId
	 */
	public static String invokeAsync(IModelProvider provider, String operationId, Object... parameters) {
		String requestId = getRequestId();
		AsyncInvocation invocation = new AsyncInvocation(provider, operationId, requestId, parameters);
		synchronized (map) {
			map.put(requestId, invocation);
		}
		return requestId;
	}
	
	/**
	 * Gets the result of an invocation
	 * 
	 * @param operationIdShort the id of the requested Operation
	 * @param requestId the id of the request
	 * @return the result of the Operation or a Message that it is not yet finished
	 */
	public static Object retrieveResult(String operationIdShort, String requestId) {
		if(!map.containsKey(requestId)) {
			throw new ResourceNotFoundException("RequestId '" + requestId + "' not found.");
		}
		
		AsyncInvocation invocation = map.get(requestId);
		
		if(!(invocation.getOperationId().equals(operationIdShort) || invocation.getOperationId().isEmpty())) {
			throw new ResourceNotFoundException(
					"RequestId '" + requestId + "' does not belong to Operation '" + operationIdShort + "'");
		}
		
		if(!invocation.isFinished()) {
			return OperationResult.EXECUTION_NOT_YET_FINISHED.toString();
		}
		
		// Remove the Invocation if it is finished and its result was retrieved
		synchronized (map) {
			map.remove(requestId);
		}
		
		
		try {
			return invocation.getResult();
		} catch (OperationExecutionErrorException e) {
			return OperationResult.EXECUTION_ERROR.toString();
		}
	}
	
	/**
	 * Checks if a given requestId exists
	 * 
	 * @param requestId the id to be checked
	 * @return if the id exists
	 */
	public static synchronized boolean hasRequestId(String requestId) {
		return map.containsKey(requestId);
	}
	
	/**
	 * Generates a unique requestId
	 * 
	 * @return a unique requestId
	 */
	private static synchronized String getRequestId() {
		return Integer.toString(count++);
	}	
}
