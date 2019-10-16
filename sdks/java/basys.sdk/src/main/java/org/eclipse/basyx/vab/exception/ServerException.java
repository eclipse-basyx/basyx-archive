package org.eclipse.basyx.vab.exception;


/**
 * Server exception
 * 
 * This exception indicates an exception that is thrown on a remote entity
 * 
 * @author pschorn, kuhn
 *
 */
public class ServerException extends RuntimeException {

	
	/**
	 * Version number for serialized instances
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * Exception type
	 */
	protected String type = null;
	
	/**
	 * Optional additional message
	 */
	private String message = null;
	
	
	
	
	/**
	 * Constructor
	 */
	public ServerException(ServerException e) {
		this.type    = e.getType();
		this.message = e.getMessage();
	}

	
	/**
	 * Constructor
	 */
	public ServerException(String type, String message) {
		this.type    = type;
		this.message = message;
	}
	
	
	
	
	
	/**
	 * Return exception type
	 */
	public String getType() {
		return type;
	}
	
	
	
	/**
	 * Return detailed message
	 */
	@Override
	public String getMessage() {
		return message;
	}
	
	
	
	/**
	 * Create readable exception
	 */
	@Override
	public String toString() {
		// Only return exception type if no message is set
		if (message == null) return type+":";
		
		// Return exception type and descriptive message
		return type+": "+message;
	}
}
