package org.eclipse.basyx.components.configuration.builder;

import org.eclipse.basyx.aas.api.registry.AASHTTPRegistryProxy;
import org.eclipse.basyx.aas.api.registry.IAASRegistryService;
import org.eclipse.basyx.aas.backend.connected.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.backend.connector.http.HTTPConnectorProvider;
import org.eclipse.basyx.components.configuration.CFGBaSyxProtocolType;
import org.eclipse.basyx.components.configuration.ConfigurableComponent;
import org.eclipse.basyx.vab.core.IVABDirectoryService;
import org.eclipse.basyx.vab.core.VABConnectionManager;



/**
 * Configuration builder for BaSyx services
 * 
 * @author kuhn
 *
 */
public class BaSyxServiceConfigurationBuilder<T extends BaSyxServiceConfigurationBuilder<T>> extends BaSyxConfigurationBuilder<Void> {

	
	/**
	 * BaSyx registry URL
	 */
	protected String registryURL = null;
	
	
	/**
	 * BaSyx connection manager type
	 */
	protected CFGBaSyxProtocolType protocoltype = null;
	
	protected IVABDirectoryService vabDirectory = null;
	
	
	/**
	 * Constructor
	 */
	public BaSyxServiceConfigurationBuilder(ConfigurableComponent<?> component) {
		// Set configured component
		this.setConfiguredComponent(component);
	}

	
	
	/**
	 * Set registry URL
	 */
	@SuppressWarnings("unchecked")
	public T registryURL(String url) {
		// Store registry URL
		registryURL = url;
		
		// Return 'this' reference
		return (T) this;
	}

	
	/**
	 * Create registry instance based on configuration
	 */
	public IAASRegistryService getRegistry() {
		// Create and return registry
		return new AASHTTPRegistryProxy(registryURL);
	}
	

	
	/**
	 * Set connection manager type
	 */
	@SuppressWarnings("unchecked")
	public T connectionManagerType(CFGBaSyxProtocolType protocol) {
		// Store protocol type
		protocoltype = protocol;
		
		// Return 'this' reference
		return (T) this;
	}

	/**
	 * Set VAB Directory
	 */
	@SuppressWarnings("unchecked")
	public T directoryService(IVABDirectoryService vabDirectory) {
		// Store VAB directory
		this.vabDirectory = vabDirectory;

		// Return 'this' Refence
		return (T) this;
	}

	/**
	 * Create connection manager based on configuration
	 */
	public VABConnectionManager getConnectionManager() {
		// Create and return VABConnectionManager
		return new VABConnectionManager(vabDirectory, new HTTPConnectorProvider());
	}

	/**
	 * Create connected AAS-manager based on configuration
	 */
	public ConnectedAssetAdministrationShellManager getConnetedAASManager() {
		// Create and return connected AAS-manager
		return new ConnectedAssetAdministrationShellManager(getRegistry(), new HTTPConnectorProvider());
	}
}

