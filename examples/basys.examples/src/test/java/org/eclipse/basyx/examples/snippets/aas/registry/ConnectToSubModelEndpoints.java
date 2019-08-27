package org.eclipse.basyx.examples.snippets.aas.registry;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.eclipse.basyx.aas.api.modelurn.ModelUrn;
import org.eclipse.basyx.aas.api.registry.AASHTTPRegistryProxy;
import org.eclipse.basyx.aas.api.registry.IAASRegistryService;
import org.eclipse.basyx.aas.api.resources.ISingleProperty;
import org.eclipse.basyx.aas.backend.connected.aas.ConnectedSubModel;
import org.eclipse.basyx.aas.backend.connector.http.HTTPConnectorProvider;
import org.eclipse.basyx.aas.metamodel.factory.MetaModelElementFactory;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.SubModel;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.identifier.IdentifierType;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property.Property;
import org.eclipse.basyx.components.servlet.submodel.DynamicModelProviderServlet;
import org.eclipse.basyx.examples.contexts.BaSyxExamplesContext_1MemoryAASServer_1SQLDirectory;
import org.eclipse.basyx.examples.deployment.BaSyxDeployment;
import org.eclipse.basyx.tools.aas.connManager.AASConnectionManager;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
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
	protected AASConnectionManager connManager = new AASConnectionManager(
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
	 * Run code snippet. This code snippet illustrates the creation of an AASDescriptor, the dynamic creation and deployment of an AAS sub model, 
	 * the lookup of the AAS sub model, and the access of the AAS sub model. 
	 */
	@Test
	public void snippet() throws Exception {
		
		// Create AAS descriptor and sub model descriptors
		ModelUrn      aasURN         = new ModelUrn("urn:de.FHG:devices.es.iese:aas:1.0:3:x-509#001");
		String        aasSrvURL      = "http://localhost:8080/basys.examples/Components/BaSys/1.0/aasServer";
		// - Sub model IS
		String        subModelId    = "exampleSM";
		// - Create AAS descriptor and sub model descriptor
		AASDescriptor aasDescriptor = new AASDescriptor(aasURN.getURN(), IdentifierType.URI, VABPathTools.concatenatePaths(aasSrvURL, aasURN.getEncodedURN()));
		SubmodelDescriptor submodelDescriptor = new SubmodelDescriptor(subModelId, IdentifierType.URI, VABPathTools.concatenatePaths(aasSrvURL, subModelId));
		// - Add sub model descriptor to AAS descriptor
		aasDescriptor.addSubmodelDescriptor(submodelDescriptor);
		

		// Register AAS and sub model descriptors in directory (push AAS descriptor to server)
		// - Connect to AAS registry
		IAASRegistryService regProxy = new AASHTTPRegistryProxy(
				"http://localhost:8080/basys.examples/Components/Directory/SQL");
		// - Register AAS descriptor with AAS and sub model endpoints in registry
		regProxy.register(aasURN, aasDescriptor);
		

		// Server connections
		// - Connect to sub model
		VABElementProxy connSubModel = this.connManager.connectToAASSubModel(aasURN, subModelId);

		
		// Create sub model
		// - This factory creates sub model properties and ensures presence of all meta data
		MetaModelElementFactory fac = new MetaModelElementFactory();

		// Create sub model
		SubModel submodel = new SubModel();
		// - Add example properties to sub model
		submodel.setId(subModelId);
		submodel.getProperties().put(fac.create(new Property(),       7, "prop1"));
		submodel.getProperties().put(fac.create(new Property(), "myStr", "prop2"));
		// - Transfer sub model to server
		//   - This creates the "exampleSM" element on the server, which is the server
		//     end point that will host the AAS sub model.
		//   - FIXME: This should actually be a urn:de.FHG:devices.es.iese:aas:1.0:3:x-509#001 element
		//            to guarantee a unique AAS end point
		connSubModel.createElement("", new HashMap<String, Object>());
		connSubModel.createElement("aas", new HashMap<String, Object>());
		connSubModel.createElement("aas/submodels", new HashMap<String, Object>());
		connSubModel.createElement("aas/submodels/exampleSM", submodel);
		
	
		// Connect to sub model using BaSyx SDK
		ConnectedSubModel connSM = new ConnectedSubModel("aas/submodels/exampleSM", connSubModel);

		
		// Read property values from sub model
		String smID     = connSM.getId();
		String prop1Id  = connSM.getProperties().get("prop1").getId();
		int    prop1Val = (int)    ((ISingleProperty) connSM.getProperties().get("prop1")).get();
		String prop2Id  = connSM.getProperties().get("prop2").getId();
		String prop2Val = (String) ((ISingleProperty) connSM.getProperties().get("prop2")).get();

		
		// Check property values
		assertTrue(smID.equals(subModelId));
		assertTrue(prop1Id.equals("prop1"));
		assertTrue(prop1Val == 7);
		assertTrue(prop2Id.equals("prop2"));
		assertTrue(prop2Val.equals("myStr"));
	}
}

