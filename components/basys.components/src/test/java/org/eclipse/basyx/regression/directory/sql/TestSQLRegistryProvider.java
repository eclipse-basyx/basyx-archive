package org.eclipse.basyx.regression.directory.sql;

import org.eclipse.basyx.components.servlets.SQLDirectoryServlet;
import org.eclipse.basyx.regression.directory.TestRegistryProvider;
import org.eclipse.basyx.vab.coder.json.connector.JSONConnector;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.basyx.vab.protocol.http.connector.HTTPConnector;
import org.eclipse.basyx.vab.protocol.http.server.AASHTTPServer;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxContext;
import org.junit.After;
import org.junit.Before;

/**
 * Test queries to SQL directory provider via SDK proxy
 * 
 * @author espen
 *
 */
public class TestSQLRegistryProvider extends TestRegistryProvider {
	/**
	 * The servlet for the tested SQL directory
	 */
	private AASHTTPServer sqlServer;

	/**
	 * Setting up SQL directory servlet
	 */
	@Override
	@Before
	public void setUp() {
		BaSyxContext context = new BaSyxContext("", "", "localhost", 4999);
		context.addServletMapping("/*", new SQLDirectoryServlet());
		sqlServer = new AASHTTPServer(context);
		sqlServer.start();

		super.setUp();
	}

	/**
	 * Shutting down the SQL directory servlet
	 */
	@After
	@Override
	public void tearDown() {
		super.tearDown();

		sqlServer.shutdown();
	}

	/**
	 * Returning the proxy that points to the previously set up servlet
	 */
	@Override
	protected IModelProvider getProxyProvider() {
		String registryUrl = "http://localhost:4999/";
		IModelProvider provider = new JSONConnector(new HTTPConnector(registryUrl));
		IModelProvider apiProxy = new VABElementProxy("/api/v1/registry", provider);
		return apiProxy;
	}
}
