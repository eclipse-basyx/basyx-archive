package org.eclipse.basyx.aas.backend.modelprovider.opcua;

import org.eclipse.basyx.aas.api.services.IModelProvider;

/**
 * Provider class responsible for requests using OPC UA
 * @author damm, pschorn
 *
 * @param <T>
 */
public class OPCUAProvider <T extends IModelProvider>{

	
	/**
	 * Reference to IModelProvider backend
	 */
	protected T providerBackend = null;
	
	
	/**
	 * Constructor
	 */
	public OPCUAProvider(T modelProviderBackend) {
		
		this.providerBackend = modelProviderBackend;
	}
	
	
	/**
	 * Get backend reference
	 */
	public T getBackendReference() {
		return providerBackend;
	}

}
