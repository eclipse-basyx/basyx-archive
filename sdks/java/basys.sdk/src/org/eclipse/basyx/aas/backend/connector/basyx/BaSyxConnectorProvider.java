package org.eclipse.basyx.aas.backend.connector.basyx;

import org.eclipse.basyx.aas.backend.connector.ConnectorProvider;
import org.eclipse.basyx.aas.backend.connector.JSONConnector;
import org.eclipse.basyx.vab.core.IModelProvider;


/**
 * A connector provider for TCP/BaSyx protocol
 * 
 * @author schnicke, kuhn
 *
 */
public class BaSyxConnectorProvider extends ConnectorProvider {

	
	/**
	 * Create the provider
	 */
	@Override
	protected IModelProvider createProvider(String address) {
		// Create address
		address = address.replace("basyx://", "");
		String hostName = address.substring(0, address.indexOf(":"));
		String[] splitted = address.split("/");
		int hostPort = new Integer(splitted[0].substring(address.indexOf(":") + 1));
		
		// Create connector, connect, and return connection
		return new JSONConnector(new BaSyxConnector(hostName, hostPort));
	}

}
