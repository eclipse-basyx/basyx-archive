package org.eclipse.basyx.sandbox.components.xmlxqueryprovider;

import org.eclipse.basyx.vab.exception.provider.ProviderException;

/**
 * Exception that indicates that a requested operation is not implemented
 * 
 * @author kuhn
 *
 */
public class OperationNotImplementedException extends ProviderException {

	
	public OperationNotImplementedException() {
		super("Operation not implemented");
	}

	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;
}
