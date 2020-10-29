package org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation;

/**
 * Used to indicate that the execution of an Operation failed
 * 
 * @author conradi
 *
 */
public class OperationExecutionErrorException extends RuntimeException {

	
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
	public OperationExecutionErrorException(String msg) {
		// Store message
		message = msg;
	}
	
		
	public OperationExecutionErrorException(Exception e) {
		super(e);
	}


	public OperationExecutionErrorException(String message, Throwable cause) {
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
