package org.eclipse.basyx.aas.backend.connector.basyx;

import java.io.IOException;
import java.net.Socket;

import org.eclipse.basyx.aas.backend.connector.JSONConnector;
import org.eclipse.basyx.aas.backend.connector.ConnectorProvider;
import org.eclipse.basyx.vab.core.IModelProvider;

public class BaSyxConnectorProvider extends ConnectorProvider {

	@Override
	protected IModelProvider createProvider(String address) {
		String hostName = address.substring(0, address.indexOf(":"));
		int hostPort = new Integer(address.substring(address.indexOf(":") + 1));
		try {
			Socket s = new Socket(hostName, hostPort);
			
			// returns BaSysConnector wrapped with ConnectedHashmapProvider that handles message header information
			return new JSONConnector(new BaSyxConnector(s));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

}
