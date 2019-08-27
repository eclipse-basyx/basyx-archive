package org.eclipse.basyx.components.service;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.aas.api.modelurn.ModelUrn;
import org.eclipse.basyx.aas.api.registry.IAASRegistryService;
import org.eclipse.basyx.aas.backend.connected.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.components.configuration.ConfigurableComponent;
import org.eclipse.basyx.components.configuration.builder.BaSyxServiceConfigurationBuilder;
import org.eclipse.basyx.sdk.api.service.BaSyxService;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;



/**
 * Abstract base class for BaSyx services. This class implements the necessary interface for BaSyx services and provides basic functionality.
 * 
 * @author kuhn
 *
 */
public abstract class BaseBaSyxService implements BaSyxService, ConfigurableComponent<BaSyxServiceConfigurationBuilder<?>> {

	
	/**
	 * Flag that indicates the request for ending the execution
	 */
	protected boolean endExecution = false;
	
	
	/**
	 * Store device name
	 */
	protected String name = null;
	
	
	/**
	 * Store URN IDs of objects and make them available via shortcuts
	 */
	protected Map<String, ModelUrn> objectIDs = new HashMap<>();
	
	
	/**
	 * VAB connection manager for this service
	 */
	protected VABConnectionManager connectionManager = null;
	
	/**
	 * AAS connected manager for this service
	 */
	protected ConnectedAssetAdministrationShellManager connectedAASManager = null;
	

	/**
	 * Registry proxy reference that will be used for registering sub models
	 */
	protected IAASRegistryService registryProxy = null;


	
	
	
	
	
	/**
	 * Configure this BaSyx component
	 */
	@Override @SuppressWarnings({ "rawtypes", "unchecked" })
	public BaSyxServiceConfigurationBuilder configure() {
		// Create and return BaSyx configuration builder, set configured component to this component
		return new BaSyxServiceConfigurationBuilder(this);
	}
	

	/**
	 * Configure this component
	 */
	@Override
	public void configureComponent(BaSyxServiceConfigurationBuilder<?> configuration) {
		// Set registry
		setRegistry(configuration.getRegistry());

		// Create BaSyx connection manager
		setConnectionManager(configuration.getConnectionManager());

		// Createm BaSyx AAS connected manger
		setConnectedAASManager(configuration.getConnetedAASManager());
	}



	
	
	/**
	 * Check end execution flag of this service
	 */
	@Override
	public boolean hasEnded() {
		// Return end execution flag
		return endExecution;
	}
	
	
	/**
	 * Run this service
	 */
	@Override
	public void start() {
		// Do nothing
	}
	
	
	/**
	 * Stop this service
	 */
	@Override
	public void stop() {
		// Change flag
		endExecution = true;
	}
	
	
	/**
	 * Change the runnable name
	 */
	@Override
	public BaSyxService setName(String newName) {
		// Set name
		name = newName;
		
		// Return 'this' reference to enable chaining
		return this;
	}
	
	
	/**
	 * Get runnable name
	 */
	@Override
	public String getName() {
		return name;
	}
	
	
	/**
	 * Wait for completion of all servers
	 */
	@Override
	public void waitFor() {
		// Do nothing
	}
	
	
	
	
	
	/**
	 * Helper function - check if string has prefix
	 */
	protected boolean hasPrefix(String str, String prefix) {
		return str.startsWith(prefix);
	}
	
	
	/**
	 * Helper function - remove prefix from string
	 */
	protected String removePrefix(String str, String prefix) {
		try {return str.substring(prefix.length()+1);} catch (Exception e) {e.printStackTrace(); return "";}
	}
	
	
	
	
	/**
	 * Add shortcut to object ID mapping
	 */
	protected void addShortcut(String shortcut, ModelUrn urn) {
		objectIDs.put(shortcut, urn);
	}
	
	
	/**
	 * Remove shortcut to object ID mapping
	 */
	protected void removeShortcut(String shortcut) {
		objectIDs.remove(shortcut);
	}
	
	
	/**
	 * Lookup object ID based on shortcut
	 */
	protected ModelUrn lookupURN(String shortcut) {
		return objectIDs.get(shortcut);
	}
	
	
	/**
	 * Clear managed object IDs
	 */
	protected void clearShortcuts() {
		objectIDs.clear();
	}
	
	
	
	
	/**
	 * Set connection manager for this service
	 */
	protected void setConnectionManager(VABConnectionManager connMngr) {
		connectionManager = connMngr;
	}
	
	/**
	 * Set connected AAS manager for this service
	 */
	public void setConnectedAASManager(ConnectedAssetAdministrationShellManager connectedAASManager) {
		this.connectedAASManager = connectedAASManager;
	}

	/**
	 * Get connection manager for this service
	 */
	protected VABConnectionManager getConnectionManager() {
		return connectionManager;
	}
	
	
	/**
	 * Create AAS server connection
	 */
	protected VABElementProxy createVABConnection(String vabElementID) {
		// Create VAB connection
		return getConnectionManager().connectToVABElement(vabElementID);
	}




	/**
	 * Set AAS registry proxy
	 */
	protected void setRegistry(IAASRegistryService regProxy) {
		registryProxy = regProxy;
	}


	/**
	 * Get AAS registry proxy reference
	 */
	protected IAASRegistryService getRegistry() {
		return registryProxy;
	}
}

