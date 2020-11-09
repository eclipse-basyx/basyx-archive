package org.eclipse.basyx.examples.snippets.aas.registry;

import static org.junit.Assert.assertEquals;

import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.aas.registration.api.IAASRegistryService;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.examples.contexts.BaSyxExamplesContext;
import org.eclipse.basyx.examples.deployment.BaSyxDeployment;
import org.eclipse.basyx.examples.support.directory.ExamplesPreconfiguredDirectory;
import org.eclipse.basyx.vab.manager.VABConnectionManager;
import org.eclipse.basyx.vab.modelprovider.VABPathTools;
import org.eclipse.basyx.vab.protocol.http.connector.HTTPConnectorProvider;
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
	protected VABConnectionManager connManager = new VABConnectionManager(new ExamplesPreconfiguredDirectory(), new HTTPConnectorProvider());

	
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
			new BaSyxExamplesContext()
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
		String aasSrvURL = "http://localhost:8080" + BaSyxExamplesContext.AASSERVERURL;
		String aasPath = VABPathTools.concatenatePaths(aasSrvURL, aasURN.getEncodedURN(), "/aas");
		// - Create AAS descriptor
		AASDescriptor aasDescriptor = new AASDescriptor(aasURN, aasPath);
		// - Add sub model descriptor URI

		String smPath = VABPathTools.concatenatePaths(aasPath, "/submodels/" + subModelURN.getEncodedURN() + "/submodel");
		SubmodelDescriptor submodelDescriptor = new SubmodelDescriptor("smIdShort", subModelURN, smPath);
		aasDescriptor.addSubmodelDescriptor(submodelDescriptor);


		// Register AAS and sub model descriptors in directory (push AAS descriptor to server)
		// - Connect to AAS registry
		IAASRegistryService regProxy = new AASRegistryProxy(
				"http://localhost:8080/" + BaSyxExamplesContext.REGISTRYURL);
		// - Register AAS descriptor with AAS and sub model endpoints in registry
		regProxy.register(aasDescriptor);

		
		// Lookup AAS descriptor
		AASDescriptor aasDesc = regProxy.lookupAAS(aasURN);
		// - AAS end sub model end points
		String aasEndpointURL = aasDesc.getFirstEndpoint();
		String smEndpointURL  = aasDesc.getSubModelDescriptor(subModelURN).getFirstEndpoint();
		
		
		// Check results
		assertEquals(aasPath, aasEndpointURL);
		assertEquals(smPath, smEndpointURL);
	}
}

