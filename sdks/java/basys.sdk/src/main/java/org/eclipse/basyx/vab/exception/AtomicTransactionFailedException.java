package org.eclipse.basyx.vab.exception;

public class AtomicTransactionFailedException extends Exception {

	
	
	private static final long serialVersionUID = 1L;
	private String message;
	
	/**
	 * Constructor
	 */
	public AtomicTransactionFailedException(String name) {
		// Store message
		message = "The atomic transaction on the submodel " + name + " failed since a subproperty has been changed on the server!";
	}
	
	
	
	/**
	 * Return detailed message
	 */
	@Override
	public String getMessage() {
		return message;
	}
}
