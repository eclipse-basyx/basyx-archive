package org.eclipse.basyx.aas.api.resources.connected;

import org.eclipse.basyx.aas.api.resources.basic.Operation;

public abstract class ConnectedOperation extends Operation {

	public abstract void call(Object[] parameters, int timeout, IOperationCall oc) throws Exception;
	public abstract Object call(Object[] parameters, int timeout) throws Exception;
}
