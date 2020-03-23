package org.eclipse.basyx.vab.exception.provider;

/**
 * Used to indicate by a ModelProvider,
 * that a given request was malformed. <br/>
 * e.g. an invalid path or a invalid JSON.
 * 
 * @author conradi
 *
 */
public class MalformedRequestException extends ProviderException {

	
	/**
	 * Version information for serialized instances
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * Constructor
	 */
	public MalformedRequestException(String msg) {
		super(msg);
	}
	
	public MalformedRequestException(Exception e) {
		super(e);
	}
}
