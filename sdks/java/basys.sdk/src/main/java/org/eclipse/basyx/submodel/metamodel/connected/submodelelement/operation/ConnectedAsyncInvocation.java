package org.eclipse.basyx.submodel.metamodel.connected.submodelelement.operation;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IAsyncInvocation;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.OperationExecutionErrorException;
import org.eclipse.basyx.submodel.restapi.OperationProvider;
import org.eclipse.basyx.submodel.restapi.operation.OperationResult;
import org.eclipse.basyx.vab.exception.provider.ProviderException;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
import org.eclipse.basyx.vab.modelprovider.VABPathTools;

/**
 * Connected variant of IAsyncInvocation
 * 
 * @author conradi
 *
 */
public class ConnectedAsyncInvocation implements IAsyncInvocation {

	private String operationId;
	private String requestId;
	
	private VABElementProxy proxy;
	
	private Object result = null;
	private boolean resultRetrieved = false;
	
	public ConnectedAsyncInvocation(VABElementProxy proxy, String operationId, Object... parameters) {
		this.proxy = proxy;
		this.operationId = operationId;
		requestId = (String) proxy.invokeOperation(Operation.INVOKE + OperationProvider.ASYNC, parameters);
	}
	
	@Override
	public Object getResult() {
		
		// Wait for Operation to finish
		while(!isFinished()) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}
		}
		
		if(!resultRetrieved) {
			// If the result was not already retrieved, do it now
			
			try {
				result = proxy.getModelPropertyValue(getListPath());
			} catch (Exception e) {
				// Save the Exception as result for later handling
				result = e;
			}
		}
		
		if(result instanceof Exception || result.equals(OperationResult.EXECUTION_ERROR.toString())) {
			throw new OperationExecutionErrorException("Exception while executing Invocation '"
					+ requestId + "' of Operation '" + operationId + "'");
		} else {
			return result;
		}
		
	}
	
	@Override
	public boolean isFinished() {
		
		if(resultRetrieved) {
			// If the result was already retrieved the Operation is done
			return true;
		}
		
		try {
			 result = proxy.getModelPropertyValue(getListPath());
		} catch (ProviderException e) {
			// As the Submodel-API does not specify a request to ask whether
			// the operation is finished, it has to be done via the retrieval of the value.
			// If the execution resulted in an Exception this Exception would be thrown here
			// -> if a ProviderException with a RuntimeException as cause is thrown,
			// the Operation is finished.
			if(e.getCause() instanceof RuntimeException) {
				resultRetrieved = true;
				result = e;
				return true;
			} else {
				// If it is something else -> rethrow it
				throw e;
			}
		}
		
		if(result.equals(OperationResult.EXECUTION_NOT_YET_FINISHED.toString())) {
			return false;
		}
		
		resultRetrieved = true;
		return true;
	}

	public String getRequestId() {
		return requestId;
	}

	public String getOperationId() {
		return operationId;
	}
	
	private String getListPath() {
		return VABPathTools.concatenatePaths(OperationProvider.INVOCATION_LIST, requestId);
	}

}
