package org.eclipse.basyx.examples.snippets.aas.registry;

import static org.junit.Assert.assertTrue;

import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.aas.registration.api.IAASRegistryService;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.components.servlet.aas.AASServlet;
import org.eclipse.basyx.examples.TestContext;
import org.eclipse.basyx.examples.deployment.BaSyxDeployment;
import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IProperty;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.vab.modelprovider.VABPathTools;
import org.eclipse.basyx.vab.protocol.http.connector.HTTPConnectorProvider;
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
		String smIdShort = "exampleSM";
		IIdentifier smId = new Identifier(IdentifierType.CUSTOM, "exampleSMId");
		// - Create AAS descriptor and sub model descriptor
		AASDescriptor aasDescriptor = new AASDescriptor(aasURN, aasSrvURL);
		String smEndpoint = VABPathTools.concatenatePaths(aasSrvURL, "submodels", smIdShort);
		SubmodelDescriptor submodelDescriptor = new SubmodelDescriptor(smIdShort, smId, smEndpoint);
		// - Add sub model descriptor to AAS descriptor
		aasDescriptor.addSubmodelDescriptor(submodelDescriptor);
		

		// Register AAS and sub model descriptors in directory (push AAS descriptor to server)
		// - Connect to AAS registry
		IAASRegistryService regProxy = new AASRegistryProxy(
				"http://localhost:8080/basys.examples/Components/Directory/SQL");
		// - Register AAS descriptor with AAS and sub model endpoints in registry
		regProxy.register(aasDescriptor);
		
		// Create sub model
		SubModel submodel = new SubModel();
		submodel.setIdShort(smIdShort);
		submodel.setIdentification(smId.getIdType(), smId.getId());

		// - Add example properties to sub model
		Property prop1 = new Property(7);
		prop1.setIdShort("prop1");
		submodel.addSubModelElement(prop1);

		Property prop2 = new Property("myStr");
		prop2.setIdShort("prop2");
		submodel.addSubModelElement(prop2);
		// - Transfer sub model to server
		//   - This creates the "exampleSM" element on the server, which is the server
		//     end point that will host the AAS sub model.
		connManager.createSubModel(aasURN, submodel);

	
		// Connect to sub model using BaSyx SDK
		ISubModel connSM = connManager.retrieveSubModel(aasURN, smId);

		
		// Read property values from sub model
		String smID     = connSM.getIdShort();
		String prop1Id = connSM.getProperties().get("prop1").getIdShort();
		int prop1Val = (int) ((IProperty) connSM.getProperties().get("prop1")).get();
		String prop2Id  = connSM.getProperties().get("prop2").getIdShort();
		String prop2Val = (String) ((IProperty) connSM.getProperties().get("prop2")).get();

		
		// Check property values
		assertTrue(smID.equals(smIdShort));
		assertTrue(prop1Id.equals("prop1"));
		assertTrue(prop1Val == 7);
		assertTrue(prop2Id.equals("prop2"));
		assertTrue(prop2Val.equals("myStr"));
	}
}

