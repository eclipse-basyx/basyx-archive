package org.eclipse.basyx.vab.manager;

import org.eclipse.basyx.vab.directory.api.IVABDirectoryService;
import org.eclipse.basyx.vab.factory.java.ModelProxyFactory;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
import org.eclipse.basyx.vab.protocol.api.IConnectorProvider;



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
	protected IConnectorProvider connectorProvider = null;
	
	/**
	 * Factory for creating proxies for addresses with multiple endpoints
	 */
	private ModelProxyFactory proxyFactory = null;

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
		this.connectorProvider = providerProvider;
		
		// Set proxy factory
		this.proxyFactory = new ModelProxyFactory(providerProvider);
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
		
		return connectToVABElementByPath(addr);
	}

	/**
	 * Connect to an VAB element on an VAB server using a qualified path
	 * 
	 * @param path
	 *            the path that describes the element location.
	 */
	public VABElementProxy connectToVABElementByPath(String path) {
		return proxyFactory.createProxy(path);	
	}
}
