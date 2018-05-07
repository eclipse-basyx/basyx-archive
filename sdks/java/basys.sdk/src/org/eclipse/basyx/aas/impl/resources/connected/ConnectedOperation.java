package org.eclipse.basyx.aas.impl.resources.connected;

import org.eclipse.basyx.aas.api.resources.basic.IOperation;
import org.eclipse.basyx.aas.impl.resources.basic.Operation;



/**
 * Base class for connected operations
 * 
 * @author schoeffler, kuhn
 *
 */
public abstract class ConnectedOperation extends Operation implements IOperation {

	public abstract void call(Object[] parameters, int timeout, IOperationCall oc) throws Exception;
	public abstract Object call(Object[] parameters, int timeout) throws Exception;
	
	

	
	/**
	 * Invoke operation
	 * 
	 * Synchronous operation invocation. 
	 * 
	 * @param parameter Invocation parameter
	 * @return Return value
	 */
	public abstract Object invoke(Object... parameter);
}
