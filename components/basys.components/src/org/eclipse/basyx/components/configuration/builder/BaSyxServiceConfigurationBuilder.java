package org.eclipse.basyx.components.configuration.builder;

import org.eclipse.basyx.aas.backend.connector.http.HTTPConnectorProvider;
import org.eclipse.basyx.components.configuration.CFGBaSyxProtocolType;
import org.eclipse.basyx.components.configuration.ConfigurableComponent;
import org.eclipse.basyx.components.proxy.registry.AASHTTPRegistryProxy;
import org.eclipse.basyx.components.proxy.registry.AASRegistryProxyIF;
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
	public AASRegistryProxyIF getRegistry() {
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
	 * Create connection manager based on configuration
	 */
	public VABConnectionManager getConnectionManager() {
		// Create and return VABConnectionManager
		return new VABConnectionManager(getRegistry(), new HTTPConnectorProvider());
	}
}

