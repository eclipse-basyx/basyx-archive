package org.eclipse.basyx.sandbox.components.registry.exception;




/**
 * Indicate a problem with the AAS directory provider
 * 
 * @author kuhn
 *
 */
public class AASDirectoryProviderException extends RuntimeException {

	
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
	public AASDirectoryProviderException(String errorMsg) {
		errorMessage = errorMsg;
	}
}

