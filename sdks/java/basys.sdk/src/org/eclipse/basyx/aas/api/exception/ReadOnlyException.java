package org.eclipse.basyx.aas.api.exception;

public class ReadOnlyException extends Exception {

	
	
	private static final long serialVersionUID = 1L;
	private String message;
	
	/**
	 * Constructor
	 */
	public ReadOnlyException(String name, String property) {
		// Store message
		message = "The SubModel "+name+" is frozen. Could not set property "+property+ ".";
	}
	
	
	
	/**
	 * Return detailed message
	 */
	@Override
	public String getMessage() {
		return message;
	}
}
