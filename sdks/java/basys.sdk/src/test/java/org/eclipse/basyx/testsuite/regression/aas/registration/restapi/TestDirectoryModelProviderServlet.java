package org.eclipse.basyx.testsuite.regression.aas.registration.restapi;

import org.eclipse.basyx.aas.registration.api.IAASRegistryService;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.aas.registration.restapi.DirectoryModelProvider;
import org.eclipse.basyx.testsuite.regression.aas.registration.proxy.TestRegistryProvider;
import org.eclipse.basyx.testsuite.regression.vab.protocol.http.AASHTTPServerResource;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
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

	/**
	 * Returns a model provider proxy for directly accessing the registry created by the http servlet
	 */
	@Override
	protected IModelProvider getProxyProvider() {
		// Create a registry proxy directly pointing to the servlet
		IAASRegistryService registryProxy = new AASRegistryProxy("http://localhost:8080/basys.sdk/Testsuite/directory");
		// Wrap the proxy in an IModelProvider
		DirectoryModelProvider provider = new DirectoryModelProvider(registryProxy);
		// Append the necessary registry API access to each request
		IModelProvider apiProxy = new VABElementProxy("/api/v1/registry", provider);
		return apiProxy;
	}

}
