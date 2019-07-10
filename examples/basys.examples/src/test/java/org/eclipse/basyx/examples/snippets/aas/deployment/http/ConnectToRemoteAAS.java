package org.eclipse.basyx.examples.snippets.aas.deployment.http;

import static org.junit.Assert.assertTrue;

import org.eclipse.basyx.aas.api.resources.IAssetAdministrationShell;
import org.eclipse.basyx.aas.backend.connected.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.backend.connector.http.HTTPConnectorProvider;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.AssetAdministrationShell;
import org.eclipse.basyx.components.servlet.submodel.AASServlet;
import org.eclipse.basyx.examples.contexts.BaSyxExamplesContext_Empty;
import org.eclipse.basyx.examples.deployment.BaSyxDeployment;
import org.eclipse.basyx.examples.support.directory.ExamplesPreconfiguredDirectory;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.junit.ClassRule;
import org.junit.Test;



/**
 * Code snippet that illustrates the creation of an Asset Administration Shell (AAS) by extending an SDK class
 * 
 * The AAS is deployed to an AASServlet instance running on a Apache Tomcat HTTP server. The AASServlet exports
 * an Asset Administration Shell via the defined BaSyx REST interface. 
 * 
 * @author kuhn
 *
 */
public class ConnectToRemoteAAS {

	
	/**
	 * Example Asset Administration Shell
	 */
	static class ExampleAssetAdministrationShell extends AssetAdministrationShell {
		
		/**
		 * Version number of serialized instance
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Constructor
		 */
		public ExampleAssetAdministrationShell() {
			// Set Asset Administration Shell ID
			setId("aas-001");
		}
	}

	
	
	/**
	 * Create VAB connection manager backend
	 * 
	 * The connection manager uses a preconfigured directory for resolving IDs to 
	 * network addresses, and a HTTP connector to connect to VAB objects.
	 */
	protected VABConnectionManager connManager = new VABConnectionManager(
			// Add example specific mappings
			new ExamplesPreconfiguredDirectory()
			    // - SDK connectors encapsulate relative path Asset Administration Shell
				.addMapping("aas-001",    "http://localhost:8080/basys.examples/Testsuite/components/BaSys/1.0/SampleAAS"),
			// We connect via HTTP
			new HTTPConnectorProvider());

	
	
	/**
	 * The BaSyx Deployment instantiates and starts context elements for this example. 
	 * 
	 * This example instantiates the BaSyxExamplesContext_1MemoryAASServer_1SQLDirectory
	 * example context that creates one AAS server, and one SQL based AAS registry.
	 * 
	 * BaSyxDeployment contexts instantiate all components on the IP address of the host. 
	 * Therefore, all components use the same IP address. 
	 */
	@ClassRule
	public static BaSyxDeployment context = new BaSyxDeployment(
				// Servlets for example snippet
				new BaSyxExamplesContext_Empty().
					// Deploy example specific servlets to Tomcat server in this context
					addServletMapping("/Testsuite/components/BaSys/1.0/SampleAAS/*",         new AASServlet(new ExampleAssetAdministrationShell()))
			);

	
	
	/**
	 * Run code snippet. Connect to AAS on server, access AAS properties. 
	 */
	@Test
	public void connectToAAS() throws Exception {
		// Create manager using the directory stub an the HTTPConnectorProvider
		// - Connect to VAB object by ID. The connection manager looks up this ID in
		//   its directory
		ConnectedAssetAdministrationShellManager manager = new ConnectedAssetAdministrationShellManager(connManager);
		
		// Retrieve the AAS from the AAS server with SDK connector
		// - IAssetAdministrationShell is the interface for the local AAS proxy
		IAssetAdministrationShell shell = manager.retrieveAAS("aas-001");
		// - Retrieve AAS values and compare to expected values
		Object propertyId = shell.getId();
		
		
		// Check result
		assertTrue(propertyId.equals("aas-001"));
	}
}


