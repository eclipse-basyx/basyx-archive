package org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation;

/**
 * Used to indicate that the execution of an Operation timed out
 * 
 * @author espen
 *
 */
public class OperationExecutionTimeoutException extends RuntimeException {

	
	/**
	 * Version information for serialized instances
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * Store message
	 */
	protected String message = null;
	
	
	/**
	 * Constructor
	 */
	public OperationExecutionTimeoutException(String msg) {
		// Store message
		message = msg;
	}
	
		
	public OperationExecutionTimeoutException(Exception e) {
		super(e);
	}


	public OperationExecutionTimeoutException(String message, Throwable cause) {
		super(cause);
		this.message = message;
	}


	/**
	 * Return detailed message
	 */
	@Override
	public String getMessage() {
		return message;
	}
}
