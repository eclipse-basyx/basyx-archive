package org.eclipse.basyx.testsuite.regression.aas.registration.restapi;

import org.eclipse.basyx.aas.registration.api.IAASRegistryService;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.testsuite.regression.aas.registration.proxy.TestRegistryProvider;
import org.eclipse.basyx.testsuite.regression.vab.protocol.http.AASHTTPServerResource;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxContext;
import org.junit.Rule;

/**
 * Test
 * 
 * @author espen
 *
 */
public class TestDirectoryModelProviderServlet extends TestRegistryProvider {
	/**
	 * Makes sure Tomcat Server is started after before each test case
	 * Initializes a new directory provider servlet
	 */
	@Rule
	public AASHTTPServerResource res = new AASHTTPServerResource(
			new BaSyxContext("/basys.sdk", System.getProperty("java.io.tmpdir"))
					.addServletMapping("/Testsuite/directory/*", new DirectoryProviderServlet()));

	@Override
	protected IAASRegistryService getRegistryService() {
		return new AASRegistryProxy("http://localhost:8080/basys.sdk/Testsuite/directory");
	}

}
