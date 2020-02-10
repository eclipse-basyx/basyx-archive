package org.eclipse.basyx.tools.sqlproxy.exception;




/**
 * Indicate an unknown element type
 * 
 * @author kuhn
 *
 */
public class UnknownElementTypeException extends RuntimeException {

	
	/**
	 * Version number support for serialized instances
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * Error message
	 */
	protected String errorMessage = null;
	
	
	
	
	/**
	 * Constructor
	 */
	public UnknownElementTypeException(String errorMsg) {
		errorMessage = errorMsg;
	}
}

