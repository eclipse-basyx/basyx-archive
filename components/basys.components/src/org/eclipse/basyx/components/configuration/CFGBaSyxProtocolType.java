package org.eclipse.basyx.components.configuration;

import org.eclipse.basyx.aas.backend.connector.basyx.BaSyxConnectorProvider;
import org.eclipse.basyx.aas.backend.connector.http.HTTPConnectorProvider;
import org.eclipse.basyx.vab.core.IConnectorProvider;



/**
 * Enumerate supported BaSyx protocol types
 * 
 * @author kuhn
 *
 */
public enum CFGBaSyxProtocolType {

	/**
	 * HTTP protocol
	 */
	HTTP(),
	
	/**
	 * BaSyx protocol
	 */
	BASYX();
	
	
	
	/**
	 * Return BaSyx protocol type by value
	 */
	public static CFGBaSyxProtocolType byValue(String cfgKey) {
		// Parse configuration key
		switch (cfgKey.toLowerCase()) {
			// Parse known protocols
			case "http":  return CFGBaSyxProtocolType.HTTP;
			case "basyx": return CFGBaSyxProtocolType.BASYX;
		
			// Unknown protocol
			default: return null;
		}
	}
	
	
	
	/**
	 * Constructor
	 */
	private CFGBaSyxProtocolType() {
		// Do nothing
	}
	
	
	
	/**
	 * Create protocol instance
	 */
	public IConnectorProvider createInstance() {
		// Create protocol instance
		if (this.equals(HTTP))  return new HTTPConnectorProvider();
		if (this.equals(BASYX)) return new BaSyxConnectorProvider();
		
		// Unknown protocol
		return null;
	}
}


