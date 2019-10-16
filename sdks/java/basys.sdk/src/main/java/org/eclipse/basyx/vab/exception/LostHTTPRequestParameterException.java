package org.eclipse.basyx.vab.exception;

public class LostHTTPRequestParameterException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String message;

	public LostHTTPRequestParameterException(String path) {
		message = "A request on " + path + "has been received without a valid json parameter (unresolved issue)";
	}
	
	@Override
	public String getMessage() {
		return message;
	}
}
