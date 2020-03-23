package org.eclipse.basyx.vab.exception.provider;

/**
 * Used to indicate by a ModelProvider,
 * that a resource to be created already exists
 * 
 * @author conradi
 *
 */
public class ResourceAlreadyExistsException extends ProviderException {

	
	/**
	 * Version information for serialized instances
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * Constructor
	 */
	public ResourceAlreadyExistsException(String msg) {
		super(msg);
	}
	
	public ResourceAlreadyExistsException(Exception e) {
		super(e);
	}
}
