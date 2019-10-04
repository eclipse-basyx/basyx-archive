package org.eclipse.basyx.vab.backend.connector.basyx;

import org.eclipse.basyx.vab.backend.connector.ConnectorProvider;
import org.eclipse.basyx.vab.backend.connector.JSONConnector;
import org.eclipse.basyx.vab.core.IModelProvider;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
import org.eclipse.basyx.vab.core.tools.VABPathTools;


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
		address = VABPathTools.getAddressEntry(address);
		address = address.replace("basyx://", "");
		String hostName = address.substring(0, address.indexOf(':'));
		String[] splitted = address.split("/");
		int hostPort = Integer.parseInt(splitted[0].substring(address.indexOf(':') + 1));

		// Create connector, connect
		IModelProvider provider = new JSONConnector(new BaSyxConnector(hostName, hostPort));
		
		// Create a proxy, if necessary
		String path = address.replace(hostName + ":" + hostPort, "");
		if (!path.isEmpty() && !path.equals("/")) {
			provider = new VABElementProxy(path, provider);
		}

		return provider;
	}

}
