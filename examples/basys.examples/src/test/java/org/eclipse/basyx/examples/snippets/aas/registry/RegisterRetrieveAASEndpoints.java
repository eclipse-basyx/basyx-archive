package org.eclipse.basyx.examples.snippets.aas.registry;

import static org.junit.Assert.assertTrue;

import org.eclipse.basyx.aas.backend.connector.http.HTTPConnectorProvider;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.identifier.IdentifierType;
import org.eclipse.basyx.components.proxy.registry.AASHTTPRegistryProxy;
import org.eclipse.basyx.components.proxy.registry.AASRegistryProxyIF;
import org.eclipse.basyx.examples.contexts.BaSyxExamplesContext_1MemoryAASServer_1SQLDirectory;
import org.eclipse.basyx.examples.deployment.BaSyxDeployment;
import org.eclipse.basyx.examples.support.directory.ExamplesPreconfiguredDirectory;
import org.eclipse.basyx.tools.aasdescriptor.AASDescriptor;
import org.eclipse.basyx.tools.aasdescriptor.SubmodelDescriptor;
import org.eclipse.basyx.tools.modelurn.ModelUrn;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.eclipse.basyx.vab.core.tools.VABPathTools;
import org.junit.ClassRule;
import org.junit.Test;



/**
 * Code snippet that registers an AAS descriptor with the AAS registry and accesses the registry using HTTP calls
 * 
 * The snippet communicates with a VAB element that is deployed to a VABLambdaServlet on a
 * Apache Tomcat HTTP server instance. The VABLambdaServlet provides an empty container that
 * is able to host any VAB object.
 * 
 * @author kuhn
 *
 */
public class RegisterRetrieveAASEndpoints {

	
	/**
	 * Create VAB connection manager backend
	 * 
	 * The connection manager uses a preconfigured directory for resolving IDs to 
	 * network addresses, and a HTTP connector to connect to VAB objects.
	 */
	protected VABConnectionManager connManager = new VABConnectionManager(
			new ExamplesPreconfiguredDirectory(),
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
				new BaSyxExamplesContext_1MemoryAASServer_1SQLDirectory()
			);

	
	
	
	/**
	 * Run code snippet. This code snippet illustrates the creation of an AASDescriptor, its registration, and 
	 * the lookup of the AAS using HTTP REST calls
	 */
	@Test
	public void snippet() throws Exception {
		
		// Create AAS descriptor and sub model descriptors
		ModelUrn      aasURN         = new ModelUrn("urn:de.FHG:devices.es.iese:aas:1.0:3:x-509#001");
		ModelUrn      subModelURN    = new ModelUrn("urn:de.FHG:devices.es.iese:exampleSM:1.0:3:x-509#001");
		String        aasSrvURL      = "http://localhost:8080/basys.examples/Components/BaSys/1.0/aasServer";
		String        aasSrvPrefix   = "/aas/submodels/aasRepository/sampleAAS";
		// - Create AAS descriptor
		AASDescriptor aasDescriptor = new AASDescriptor(aasURN.getURN(), IdentifierType.URI, VABPathTools.concatenatePaths(aasSrvURL, aasSrvPrefix, aasURN.getEncodedURN()));
		// - Add sub model descriptor URI
		SubmodelDescriptor submodelDescriptor = new SubmodelDescriptor(subModelURN.getURN(), IdentifierType.URI, VABPathTools.concatenatePaths(aasSrvURL, aasSrvPrefix, subModelURN.getEncodedURN()));
		aasDescriptor.addSubmodelDescriptor(submodelDescriptor);


		// Register AAS and sub model descriptors in directory (push AAS descriptor to server)
		// - Connect to AAS registry
		AASRegistryProxyIF regProxy = new AASHTTPRegistryProxy("http://localhost:8080/basys.examples/Components/Directory/SQL");
		// - Register AAS descriptor with AAS and sub model endpoints in registry
		regProxy.register(aasURN, aasDescriptor);

		
		// Lookup AAS descriptor
		AASDescriptor aasDesc = regProxy.lookupAAS(aasURN);
		// - AAS end sub model end points
		String aasEndpointURL = aasDesc.getFirstEndpoint();
		String smEndpointURL  = aasDesc.getSubModelDescriptor(subModelURN).getFirstEndpoint();
		
		
		// Check results
		assertTrue(aasEndpointURL.equals("http://localhost:8080/basys.examples/Components/BaSys/1.0/aasServer/aas/submodels/aasRepository/sampleAAS/urn%3Ade.FHG%3Adevices.es.iese%3Aaas%3A1.0%3A3%3Ax-509%23001"));
		assertTrue(smEndpointURL.equals("http://localhost:8080/basys.examples/Components/BaSys/1.0/aasServer/aas/submodels/aasRepository/sampleAAS/urn%3Ade.FHG%3Adevices.es.iese%3AexampleSM%3A1.0%3A3%3Ax-509%23001"));
	}
}
