package org.eclipse.basyx.vab.exception.provider;

/**
 * Used to indicate by a ModelProvider,
 * that invoke was called with a path to a non invokable resource.
 * 
 * @author conradi
 *
 */
public class NotAnInvokableException extends ProviderException {

	  
	/**
	 * Version information for serialized instances
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * Constructor
	 */
	public NotAnInvokableException(String msg) {
		super(msg);
	}
	
	public NotAnInvokableException(Exception e) {
		super(e);
	}
}