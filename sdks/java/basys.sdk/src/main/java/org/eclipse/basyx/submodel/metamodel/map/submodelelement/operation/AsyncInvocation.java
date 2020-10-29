package org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IAsyncInvocation;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;

/**
 * Local implementation of IAsyncInvocation
 * 
 * @author conradi
 *
 */
public class AsyncInvocation implements IAsyncInvocation {
	
	private static final String DEFAULT_REQUEST_ID = "0";

	private String operationId;
	private String requestId;
	
	private CompletableFuture<Object> future;
	
	
	@SuppressWarnings("unchecked")
	public AsyncInvocation(Operation operation, String requestId, Object... parameters) {
		operationId = operation.getIdShort();
		this.requestId = requestId;
		
		Function<Object[], Object> invokable = (Function<Object[], Object>) operation.get(Operation.INVOKABLE);
		future = CompletableFuture.supplyAsync(() -> invokable.apply(parameters));
	}
	
	public AsyncInvocation(Operation operation, Object... parameters) {
		this(operation, DEFAULT_REQUEST_ID, parameters);
	}
	
	public AsyncInvocation(IModelProvider provider, String operationId, String requestId, Object... parameters) {
		this.operationId = operationId;
		this.requestId = requestId;
		
		future = CompletableFuture.supplyAsync(() -> provider.invokeOperation("", parameters));
	}
	
	@Override
	public Object getResult() {
		try {
			return future.get();
		} catch (Exception e) {
			// Some RuntimeException occured in the executed function			
			throw new OperationExecutionErrorException("Exception while executing Invocation '"
					+ requestId + "' of Operation '" + operationId + "'", e.getCause());
		}
	}
	
	@Override
	public boolean isFinished() {
		return future.isDone();
	}

	public String getRequestId() {
		return requestId;
	}

	public String getOperationId() {
		return operationId;
	}
	
}
