package org.eclipse.basyx.vab.exception;

public class TypeMismatchException extends Exception {

	
	
	private static final long serialVersionUID = 1L;
	private String message;
	
	/**
	 * Constructor
	 */
	public TypeMismatchException(String name, String expectedType) {
		// Store message
		message = "The property '"+name+"' must be of type '"+expectedType+"'";
	}
	
	
	
	/**
	 * Return detailed message
	 */
	@Override
	public String getMessage() {
		return message;
	}
}
