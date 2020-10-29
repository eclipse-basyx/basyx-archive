package org.eclipse.basyx.testsuite.regression.submodel.metamodel.map.submodelelement.operation;

import java.util.function.Function;

import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;

/**
 * Helperclass for testing async invocations of Operations
 * 
 * @author conradi
 *
 */
public class AsyncOperationHelper {
	
	public static final String ASYNC_OP_ID = "asyncOperation";
	public static final String ASYNC_EXCEPTION_OP_ID = "asyncExceptionOperation";

	private Object waitObject = new Object();
	private boolean shouldWait = true;
	
	private final Function<Object[], Object> ASYNC_FUNC = (Function<Object[], Object>) v -> {
		int result = (int)v[0] + (int)v[1];
		synchronized (waitObject) {
			while (shouldWait) {
				try {
					waitObject.wait();
				} catch (InterruptedException e) {
				}
			}
		}
		return result;
	};
	
	private final Function<Object[], Object> ASYNC_EXCEPTION_FUNC = (Function<Object[], Object>) v -> {
		NullPointerException ex = new NullPointerException();
		synchronized (waitObject) {
			while (shouldWait) {
				try {
					waitObject.wait();
				} catch (InterruptedException e) {
				}
			}
		}
		throw ex;
	};
	
	public Operation getAsyncOperation() {
		shouldWait = true;
		Operation op = new Operation(ASYNC_FUNC);
		op.setIdShort(ASYNC_OP_ID);
		return op;
	}
	
	public Operation getAsyncExceptionOperation() {
		shouldWait = true;
		Operation op = new Operation(ASYNC_EXCEPTION_FUNC);
		op.setIdShort(ASYNC_EXCEPTION_OP_ID);
		return op;
	}
	
	public void releaseWaitingOperation() {
		shouldWait = false;
		synchronized (waitObject) {
			waitObject.notifyAll();
		}
		
		// Give the Operation a bit of time to finish
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
		}
	}
	
}
