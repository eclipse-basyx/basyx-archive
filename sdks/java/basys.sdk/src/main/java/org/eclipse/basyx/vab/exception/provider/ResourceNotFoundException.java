package org.eclipse.basyx.vab.exception.provider;


/**
 * Exception that indicates that a requested resource (AAS, sub model, property) was not found
 * 
 * @author kuhn
 *
 */
public class ResourceNotFoundException extends ProviderException {

	
	/**
	 * Version information for serialized instances
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * Constructor
	 */
	public ResourceNotFoundException(String msg) {
		super(msg);
	}
	
	public ResourceNotFoundException(Exception e) {
		super(e);
	}
}
