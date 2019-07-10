package org.eclipse.basyx.vab.backend.server;

import org.eclipse.basyx.vab.backend.server.utils.JSONProvider;
import org.eclipse.basyx.vab.core.IModelProvider;


/**
 * BaSys communication interface
 * 
 * @author kuhn
 *
 */
public interface BaSysCommunicationInterface<ModelProvider extends IModelProvider> {

	
	/**
	 * Get JSON Provider from backend
	 */
	public JSONProvider<ModelProvider> getProviderBackend();
}
