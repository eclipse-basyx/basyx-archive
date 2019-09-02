package org.eclipse.basyx.vab.core;

import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
import org.eclipse.basyx.vab.core.tools.VABPathTools;



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
	protected IVABDirectoryService directoryService = null;

	
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
	public VABConnectionManager(IVABDirectoryService networkDirectoryService, IConnectorProvider providerProvider) {
		// Set directory service reference
		directoryService = networkDirectoryService;

		// Set connector reference
		this.providerProvider = providerProvider;
	}

	
	/**
	 * Connect to an VAB element
	 * 
	 * @param urn the URN that describes the element. 
	 */
	public VABElementProxy connectToVABElement(String urn) {

		// Get AAS from directory
		String addr = "";

		// Lookup address in directory server
		addr = directoryService.lookup(urn);

		// Return a new VABElementProxy
		return new VABElementProxy(VABPathTools.removeAddressEntry(addr), providerProvider.getConnector(addr));
	}

	/**
	 * Connect to an VAB element on an VAB server using a qualified path
	 * 
	 * @param path
	 *            the path that describes the element location.
	 */
	public VABElementProxy connectToVABElementByPath(String path) {

		// Return a new VABElementProxy
		// - Do not pass path here to provider as address, as the path parameter is
		// already absolute and contains the address.
		return new VABElementProxy(VABPathTools.removeAddressEntry(path), providerProvider.getConnector(path));
	}
}
