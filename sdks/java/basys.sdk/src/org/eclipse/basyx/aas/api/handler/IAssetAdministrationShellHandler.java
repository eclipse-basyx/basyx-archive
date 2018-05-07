package org.eclipse.basyx.aas.api.handler;

import java.lang.reflect.Method;

import org.eclipse.basyx.aas.impl.resources.connected.ConnectedOperation;


/**
 * Interface for asset administration shell handlers
 * 
 * @author schoeffler
 *
 */
public interface IAssetAdministrationShellHandler {
	
	
	public void handleOperation(ConnectedOperation cop, Object obj, Method m) throws Exception;
}
