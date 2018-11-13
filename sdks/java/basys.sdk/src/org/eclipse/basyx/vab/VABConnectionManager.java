package org.eclipse.basyx.vab;

import org.eclipse.basyx.aas.api.services.IDirectoryService;
import org.eclipse.basyx.aas.backend.connector.ConnectorProvider;
import org.eclipse.basyx.vab.proxy.VABElementProxy;




/**
 * Implement a AAS manager backend that communicates via HTTP/REST
 * 
 * @author kuhn
 *
 */
public class VABConnectionManager {

	
	/**
	 * Directory service reference
	 */
	protected IDirectoryService directoryService = null;
	
	
	/**
	 * Store connection providers
	 */
	private ConnectorProvider providerProvider;

	
	
	/**
	 * Constructor - create a new HTTP AAS manager
	 * 
	 * @param networkDirectoryService  The network directory service to use
	 */
	public VABConnectionManager(IDirectoryService networkDirectoryService, ConnectorProvider providerProvider) {
		// Set directory service reference
		directoryService = networkDirectoryService;

		// Set connector reference
		this.providerProvider = providerProvider;
	}


	/**
	 * Connect to an VAB element
	 */
	public VABElementProxy connectToVABElement(String urn) {
		
		// Get AAS from directory
		String addr = null;

		// Lookup address in directory server
		addr = directoryService.lookup(urn);

		// Return a new VABElementProxy
		return new VABElementProxy(addr, providerProvider.getProvider(addr), this);
	}	
}


