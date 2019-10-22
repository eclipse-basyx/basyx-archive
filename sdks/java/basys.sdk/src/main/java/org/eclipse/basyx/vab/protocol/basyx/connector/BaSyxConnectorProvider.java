package org.eclipse.basyx.vab.protocol.basyx.connector;

import org.eclipse.basyx.vab.coder.json.connector.JSONConnector;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
import org.eclipse.basyx.vab.modelprovider.VABPathTools;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.basyx.vab.protocol.api.ConnectorProvider;


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
		address = VABPathTools.getFirstEndpoint(address);
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
