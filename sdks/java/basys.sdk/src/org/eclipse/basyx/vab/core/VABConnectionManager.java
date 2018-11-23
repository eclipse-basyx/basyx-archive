package org.eclipse.basyx.vab.core;

import org.eclipse.basyx.vab.core.proxy.VABElementProxy;

/**
 * Allows access to elements provided by the VAB
 * 
 * @author kuhn, schnicke
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
	protected IConnectorProvider providerProvider;

	/**
	 * 
	 * @param networkDirectoryService
	 *            the directory used to map ids to addresses
	 * @param providerProvider
	 *            used to get the appropriate connector for the selected address
	 */
	public VABConnectionManager(IDirectoryService networkDirectoryService, IConnectorProvider providerProvider) {
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
		return new VABElementProxy(addr, providerProvider.getConnector(addr));
	}
}
