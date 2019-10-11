package org.eclipse.basyx.components.configuration.exception;


/**
 * indicate insufficient configuration data, i.e. important configuration properties have not been set when a configurable component is instantiated.
 * 
 * @author kuhn
 *
 */
public class InsufficientConfigurationDateException extends RuntimeException {

	
	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	
	
	
	/**
	 * Default constructor
	 */
	public InsufficientConfigurationDateException() {
		// Invoke base constructor
		super();
	}

	
	/**
	 * Constructor with additional message
	 */
	public InsufficientConfigurationDateException(String message) {
		// Invoke base constructor
		super(message);
	}
}

