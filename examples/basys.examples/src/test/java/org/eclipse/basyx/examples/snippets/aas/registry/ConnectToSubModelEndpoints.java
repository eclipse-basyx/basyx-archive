package org.eclipse.basyx.examples.snippets.aas.registry;

import static org.junit.Assert.assertTrue;

import org.eclipse.basyx.aas.api.metamodel.aas.ISubModel;
import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.property.ISingleProperty;
import org.eclipse.basyx.aas.api.modelurn.ModelUrn;
import org.eclipse.basyx.aas.api.registry.AASHTTPRegistryProxy;
import org.eclipse.basyx.aas.api.registry.IAASRegistryService;
import org.eclipse.basyx.aas.backend.connected.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.AssetAdministrationShell;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.SubModel;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.identifier.IdentifierType;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.submodelelement.property.SingleProperty;
import org.eclipse.basyx.components.servlet.submodel.AASServlet;
import org.eclipse.basyx.examples.contexts.BaSyxExamplesContext_1MemoryAASServer_1SQLDirectory;
import org.eclipse.basyx.examples.deployment.BaSyxDeployment;
import org.eclipse.basyx.vab.backend.connector.http.HTTPConnectorProvider;
import org.eclipse.basyx.vab.core.tools.VABPathTools;
import org.junit.ClassRule;
import org.junit.Test;



/**
 * Code snippet that registers an AAS descriptor with the AAS registry and connects to a sub model of
 * the registered AAS endpoint
 * 
 * The snippet communicates with a VAB element that is deployed to a VABLambdaServlet on a
 * Apache Tomcat HTTP server instance. The VABLambdaServlet provides an empty container that
 * is able to host any VAB object.
 * 
 * @author kuhn
 *
 */
public class ConnectToSubModelEndpoints {

	
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
					addServletMapping("/Components/BaSys/1.0/aasServer/*",
							new AASServlet(new AssetAdministrationShell()))
			);

	
	
	
	/**
	 * Run code snippet. This code snippet illustrates the creation of an AASDescriptor, the dynamic creation and deployment of an AAS sub model, 
	 * the lookup of the AAS sub model, and the access of the AAS sub model. 
	 */
	@Test
	public void snippet() throws Exception {
		
		// Create AAS descriptor and sub model descriptors
		ModelUrn      aasURN         = new ModelUrn("urn:de.FHG:devices.es.iese:aas:1.0:3:x-509#001");
		String        aasSrvURL      = "http://localhost:8080/basys.examples/Components/BaSys/1.0/aasServer/aas";
		// - Sub model ID
		String        subModelId    = "exampleSM";
		// - Create AAS descriptor and sub model descriptor
		AASDescriptor aasDescriptor = new AASDescriptor(aasURN.getURN(), IdentifierType.URI, aasSrvURL);
		SubmodelDescriptor submodelDescriptor = new SubmodelDescriptor(subModelId, IdentifierType.URI,
				VABPathTools.concatenatePaths(aasSrvURL, "submodels", subModelId));
		// - Add sub model descriptor to AAS descriptor
		aasDescriptor.addSubmodelDescriptor(submodelDescriptor);
		

		// Register AAS and sub model descriptors in directory (push AAS descriptor to server)
		// - Connect to AAS registry
		IAASRegistryService regProxy = new AASHTTPRegistryProxy(
				"http://localhost:8080/basys.examples/Components/Directory/SQL");
		// - Register AAS descriptor with AAS and sub model endpoints in registry
		regProxy.register(aasURN, aasDescriptor);
		
		// Create sub model
		SubModel submodel = new SubModel();
		submodel.setId(subModelId);

		// - Add example properties to sub model
		SingleProperty prop1 = new SingleProperty(7);
		prop1.setId("prop1");
		submodel.addSubModelElement(prop1);

		SingleProperty prop2 = new SingleProperty("myStr");
		prop2.setId("prop2");
		submodel.addSubModelElement(prop2);
		// - Transfer sub model to server
		//   - This creates the "exampleSM" element on the server, which is the server
		//     end point that will host the AAS sub model.
		connManager.createSubModel(aasURN, submodel);

	
		// Connect to sub model using BaSyx SDK
		ISubModel connSM = connManager.retrieveSubModel(aasURN, subModelId);

		
		// Read property values from sub model
		String smID     = connSM.getId();
		String prop1Id = connSM.getDataElements().get("prop1").getId();
		int    prop1Val = (int)    ((ISingleProperty) connSM.getDataElements().get("prop1")).get();
		String prop2Id  = connSM.getDataElements().get("prop2").getId();
		String prop2Val = (String) ((ISingleProperty) connSM.getDataElements().get("prop2")).get();

		
		// Check property values
		assertTrue(smID.equals(subModelId));
		assertTrue(prop1Id.equals("prop1"));
		assertTrue(prop1Val == 7);
		assertTrue(prop2Id.equals("prop2"));
		assertTrue(prop2Val.equals("myStr"));
	}
}

