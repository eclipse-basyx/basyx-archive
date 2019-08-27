package org.eclipse.basyx.examples.snippets.aas.registry;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.eclipse.basyx.aas.api.modelurn.ModelUrn;
import org.eclipse.basyx.aas.api.registry.AASHTTPRegistryProxy;
import org.eclipse.basyx.aas.api.registry.IAASRegistryService;
import org.eclipse.basyx.aas.backend.connected.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.backend.connected.aas.ConnectedAssetAdministrationShell;
import org.eclipse.basyx.aas.backend.connector.http.HTTPConnectorProvider;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.identifier.IdentifierType;
import org.eclipse.basyx.components.servlet.submodel.DynamicModelProviderServlet;
import org.eclipse.basyx.examples.contexts.BaSyxExamplesContext_1MemoryAASServer_1SQLDirectory;
import org.eclipse.basyx.examples.deployment.BaSyxDeployment;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
import org.eclipse.basyx.vab.core.tools.VABPathTools;
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
			new AASHTTPRegistryProxy("http://localhost:8080/basys.examples/Components/Directory/SQL"),
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
				new BaSyxExamplesContext_1MemoryAASServer_1SQLDirectory().
					// Deploy example specific servlets to Tomcat server in this context
					addServletMapping("/Components/BaSys/1.0/aasServer/*", new DynamicModelProviderServlet())
			);

	
	
	
	/**
	 * Run code snippet. This code snippet illustrates the creation of an AASDescriptor, the dynamic creation and deployment of AAS, 
	 * the lookup of the AAS, and the access of the AAS. 
	 */
	@Test
	public void snippet() throws Exception {
		
		// Create AAS descriptor and sub model descriptors
		ModelUrn      aasURN         = new ModelUrn("urn:de.FHG:devices.es.iese:aas:1.0:3:x-509#001");
		String        aasSrvURL      = "http://localhost:8080/basys.examples/Components/BaSys/1.0/aasServer";
		// - Create AAS descriptor
		AASDescriptor aasDescriptor = new AASDescriptor(aasURN.getURN(), IdentifierType.URI, VABPathTools.concatenatePaths(aasSrvURL, aasURN.getEncodedURN()));


		// Register AAS and sub model descriptors in directory (push AAS descriptor to server)
		// - Connect to AAS registry
		IAASRegistryService regProxy = new AASHTTPRegistryProxy("http://localhost:8080/basys.examples/Components/Directory/SQL");
		// - Register AAS descriptor with AAS and sub model endpoints in registry
		regProxy.register(aasURN, aasDescriptor);

		// Connect to AAS using BaSyx connector
		// - Connect to VAB object by ID. The connection manager looks up this ID in
		// its directory
		ConnectedAssetAdministrationShell shell = this.connManager.retrieveAAS(aasURN);

		// Server connections
		// - Connect AAS
		VABElementProxy connSubModel1 = shell.getProxy();

		
		// Create AAS
		AssetAdministrationShell aas = new AssetAdministrationShell();
		// - Set AAS ID
		aas.setId(aasURN.toString());
		// - Transfer AAS to server
		//   - This creates the "urn:de.FHG:devices.es.iese:aas:1.0:3:x-509#001" element on the server, which is the server
		//     end point that will host the AAS.
		connSubModel1.createElement("", new HashMap<String, Object>());
		//   - This call transfers the AAS to urn:de.FHG:devices.es.iese:aas:1.0:3:x-509#001/aas on server
		connSubModel1.createElement("aas", aas);

		

		// Retrieve the AAS from the AAS server with SDK connector
		// - IAssetAdministrationShell is the interface for the local AAS proxy
		// - Retrieve AAS values and compare to expected values
		Object aasIDProperty = shell.getId();
		
		// Check property value
		assertTrue(aasIDProperty.equals(aasURN.toString()));
	}
}

