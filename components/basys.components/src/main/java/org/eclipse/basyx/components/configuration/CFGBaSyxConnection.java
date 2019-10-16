package org.eclipse.basyx.components.configuration;

import org.eclipse.basyx.vab.directory.api.IVABDirectoryService;
import org.eclipse.basyx.vab.protocol.api.IConnectorProvider;




/**
 * Configure a server connection
 * 
 * @author kuhn
 *
 */
public class CFGBaSyxConnection {

	
	/**
	 * Protocol type
	 */
	protected CFGBaSyxProtocolType protocol = null;
	
	
	/**
	 * Directory type for this connection
	 */
	protected String directoryProviderName = null;
	
	
	
	
	/**
	 * Constructor
	 */
	public CFGBaSyxConnection() {
		// Do nothing
	}
	
	
	/**
	 * Set protocol type
	 * 
	 * @return CFGBaSyxConnection to support builder pattern
	 */
	public CFGBaSyxConnection setProtocol(CFGBaSyxProtocolType proto) {
		// Store protocol type
		protocol = proto;
		
		// Return 'this' instance
		return this;
	}
	
	
	/**
	 * Set directory
	 * 
	 * @return CFGBaSyxConnection to support builder pattern
	 */
	public CFGBaSyxConnection setDirectoryProvider(String providerName) {
		// Store protocol type
		directoryProviderName = providerName;
		
		// Return 'this' instance
		return this;		
	}
	
	
	
	/**
	 * Create protocol provider
	 */
	public IConnectorProvider createConnectorProvider() {
		// Create connector provider instance
		return protocol.createInstance();
	}
	
	
	/**
	 * Instantiate the directory class
	 */
	public IVABDirectoryService createDirectoryInstance() {
		// Try to create instance
		try {
			// Get Java class by name
			Class<?> clazz = Class.forName(directoryProviderName);
		
			// Instantiate class
			IVABDirectoryService directoryService = (IVABDirectoryService) clazz.newInstance();
			
			// Return directory service instance
			return directoryService;
		} catch (IllegalAccessException | ClassNotFoundException | InstantiationException e) {
			// this is more or less fatal, so just inform the user
			e.printStackTrace();
		}
		
		// Return null pointer
		return null;
	}
}

