package org.eclipse.basyx.testsuite.regression.vab.gateway;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.aas.backend.connector.basyx.BaSyxConnectorProvider;
import org.eclipse.basyx.aas.backend.provider.VirtualPathModelProvider;
import org.eclipse.basyx.testsuite.support.vab.stub.DirectoryServiceStub;
import org.eclipse.basyx.vab.backend.gateway.ConnectorProviderMapper;
import org.eclipse.basyx.vab.backend.gateway.DelegatingModelProvider;
import org.eclipse.basyx.vab.backend.server.basyx.BaSyxTCPServer;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
import org.junit.Test;

/**
 * Tests if the integration of DelegatingModelProvider and
 * ConnectorProviderMapper work as expected
 * 
 * @author schnicke
 *
 */
public class TestGateway {
	/**
	 * Tests the following gateway scenario: <br />
	 * Creates VABElementProxy using gateway; gateway forwards call to serverA
	 * providing a VAB element <br />
	 * <b>Assumption:</b> The path has already been retrieved from a directory
	 * <br />
	 * <ul>
	 * <li>The following request is processed:
	 * <i>basyx://127.0.0.1:6999//basyx://127.0.0.1:6998//propertyA</i></li>
	 * <li>The local connector connects to gateway on <i>127.0.0.1:6999</i> via
	 * tcp</li>
	 * <li>Connector sends request <i>basyx://127.0.0.1:6998//propertyA</i> to
	 * gateway</li>
	 * <li>Gateway forwards the request <i>propertyA</i> to <i>127.0.0.1:6998</i>
	 * via tcp</li>
	 * <li>server handles the request <i>propertyA</i></li>
	 * </ul>
	 * 
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	@Test
	public void test() throws UnknownHostException, IOException {

		// Create VAB element
		Map<String, Object> vabElem = new HashMap<String, Object>();
		vabElem.put("propertyA", 10);

		// Provide it using VirtualPathModelProvider and a tcp server on port 6998
		BaSyxTCPServer<VirtualPathModelProvider> server = new BaSyxTCPServer<>(new VirtualPathModelProvider(vabElem),
				6998);

		// Create ConnectorProviderMapper and add mapping from "basyx" to
		// BaSyxConnectorProvider for gateway
		ConnectorProviderMapper gatewayMapper = new ConnectorProviderMapper();
		gatewayMapper.addConnectorProvider("basyx", new BaSyxConnectorProvider());

		// Create gateway using DelegatingModelProvider
		BaSyxTCPServer<DelegatingModelProvider> gateway = new BaSyxTCPServer<>(
				new DelegatingModelProvider(gatewayMapper), 6999);

		// Start element provider and gateway
		server.start();
		gateway.start();

		// Create Directory, here it is configured statically, of course a dynamic
		// request to e.g. a servlet is also possible
		DirectoryServiceStub directory = new DirectoryServiceStub();
		directory.addMapping("Elem", "basyx://127.0.0.1:6999//basyx://127.0.0.1:6998");

		// Create ConnectionProviderMapper for client
		ConnectorProviderMapper clientMapper = new ConnectorProviderMapper();
		clientMapper.addConnectorProvider("basyx", new BaSyxConnectorProvider());

		// Create VABConnectionManager
		VABConnectionManager manager = new VABConnectionManager(directory, clientMapper);

		// Retrieve VABElementProxy
		VABElementProxy proxy = manager.connectToVABElement("Elem");

		// Test if the value is retrieved correctly
		assertEquals(10, proxy.readElementValue("propertyA"));
	}
}
