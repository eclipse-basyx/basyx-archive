package org.eclipse.basyx.examples.snippets.aas.registry;

import static org.junit.Assert.assertEquals;

import org.basyx.components.AASServer.servlet.AASServerServlet;
import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.metamodel.connected.ConnectedAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.examples.TestContext;
import org.eclipse.basyx.examples.deployment.BaSyxDeployment;
import org.eclipse.basyx.vab.protocol.http.connector.HTTPConnectorProvider;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * Code snippet that registers an AAS descriptor with the AAS registry and connects to the registered AAS endpoint
 * 
 * The snippet communicates with a VAB element that is deployed to a VABLambdaServlet on a
 * Apache Tomcat HTTP server instance. The VABLambdaServlet provides an empty container that
 * is able to host any VAB object.
 * 
 * @author kuhn
 *
 */
public class ConnectToAASEndpoints {

	
	/**
	 * Create VAB connection manager backend
	 * 
	 * The connection manager uses a preconfigured directory for resolving IDs to 
	 * network addresses, and a HTTP connector to connect to VAB objects.
	 */
	protected ConnectedAssetAdministrationShellManager connManager = new ConnectedAssetAdministrationShellManager(
			new AASRegistryProxy("http://localhost:8080/basys.examples/Components/Directory/SQL"),
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
				// Simulated servlets
				// - BaSys topology with one AAS Server and one SQL directory
				TestContext.sqlContext.
					// Deploy example specific servlets to Tomcat server in this context
					addServletMapping("/Components/BaSys/1.0/aasServer/*", new AASServerServlet())
			);

	
	
	
	/**
	 * Run code snippet. This code snippet illustrates the creation of an AASDescriptor, the dynamic creation and deployment of AAS, 
	 * the lookup of the AAS, and the access of the AAS. 
	 */
	@Test
	public void snippet() throws Exception {
		
		// Create AAS descriptor and sub model descriptors
		ModelUrn      aasURN         = new ModelUrn("urn:de.FHG:devices.es.iese:aas:1.0:3:x-509#001");
		String aasSrvURL = "http://localhost:8080/basys.examples/Components/BaSys/1.0/aasServer";

		// Create AAS
		AssetAdministrationShell aas = new AssetAdministrationShell();

		// - Set AAS ID
		aas.setIdentification(aasURN);

		// - Transfer AAS to server
		//   - This creates the "urn:de.FHG:devices.es.iese:aas:1.0:3:x-509#001" element on the server, which is the server
		//     end point that will host the AAS.
		connManager.createAAS(aas, aasURN, aasSrvURL);

		// Server connections
		// - Connect AAS
		ConnectedAssetAdministrationShell shell = connManager.retrieveAAS(aasURN);

		// Retrieve the AAS from the AAS server with SDK connector
		// - IAssetAdministrationShell is the interface for the local AAS proxy
		// - Retrieve AAS values and compare to expected values
		Object aasIDProperty = shell.getIdentification().getId();
		
		// Check property value
		assertEquals(aasURN.getURN(), aasIDProperty);
	}
}

