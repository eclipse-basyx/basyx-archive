package org.eclipse.basyx.vab.exception;

public class ReadOnlyException extends Exception {

	
	
	private static final long serialVersionUID = 1L;
	private String message;
	
	/**
	 * Constructor
	 */
	public ReadOnlyException(String name) {
		// Store message
		message = "The SubModel "+name+" is frozen.";
	}
	
	
	
	/**
	 * Return detailed message
	 */
	@Override
	public String getMessage() {
		return message;
	}
}
