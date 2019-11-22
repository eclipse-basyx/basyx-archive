package org.eclipse.basyx.components.configuration.exception;


/**
 * indicate insufficient configuration data, i.e. important configuration properties have not been set when a configurable component is instantiated.
 * 
 * @author kuhn
 *
 */
public class InsufficientConfigurationDataException extends RuntimeException {

	
	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	
	
	
	/**
	 * Default constructor
	 */
	public InsufficientConfigurationDataException() {
		// Invoke base constructor
		super();
	}

	
	/**
	 * Constructor with additional message
	 */
	public InsufficientConfigurationDataException(String message) {
		// Invoke base constructor
		super(message);
	}
}

