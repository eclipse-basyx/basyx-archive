package org.eclipse.basyx.vab.exception;


/**
 * Exception that indicates that a requested resource (AAS, sub model, property) was not found
 * 
 * @author kuhn
 *
 */
public class ResourceNotFoundException extends RuntimeException {

	
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
	public ResourceNotFoundException(String msg) {
		// Store message
		message = msg;
	}
	
	
	
	/**
	 * Return detailed message
	 */
	@Override
	public String getMessage() {
		return message;
	}
}
