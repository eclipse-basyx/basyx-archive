package org.eclipse.basyx.components.configuration;

import org.eclipse.basyx.vab.protocol.api.IConnectorProvider;
import org.eclipse.basyx.vab.protocol.basyx.connector.BaSyxConnectorProvider;
import org.eclipse.basyx.vab.protocol.http.connector.HTTPConnectorProvider;



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


