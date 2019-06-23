package org.eclipse.basyx.examples.snippets.aas.submodels;

import static org.junit.Assert.assertTrue;

import org.eclipse.basyx.aas.api.resources.ISingleProperty;
import org.eclipse.basyx.aas.api.resources.ISubModel;
import org.eclipse.basyx.aas.backend.connected.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.backend.connector.http.HTTPConnectorProvider;
import org.eclipse.basyx.aas.metamodel.factory.MetaModelElementFactory;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.SubModel;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property.Property;
import org.eclipse.basyx.components.servlet.submodel.DynamicModelProviderServlet;
import org.eclipse.basyx.examples.contexts.BaSyxExamplesContext_1MemoryAASServer_1SQLDirectory;
import org.eclipse.basyx.examples.deployment.BaSyxDeployment;
import org.eclipse.basyx.examples.support.directory.ExamplesPreconfiguredDirectory;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
import org.junit.ClassRule;
import org.junit.Test;



/**
 * Code snippet that illustrates the creation of an AAS sub model
 * 
 * @author kuhn
 *
 */
public class DynamicSubModelDeployment {

	
	/**
	 * Create VAB connection manager backend
	 * 
	 * The connection manager uses a preconfigured directory for resolving IDs to 
	 * network addresses, and a HTTP connector to connect to VAB objects.
	 */
	protected VABConnectionManager connManager = new VABConnectionManager(
			new ExamplesPreconfiguredDirectory()
				// Add example specific mappings
			    .addMapping("de.FHG:devices.es.iese:statusSM:1.0:3:x-509:003",  "http://localhost:8080/basys.examples/Testsuite/components/BaSys/1.0/dynamicModelRepository"),
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
					// Deploy example specific servlets to Apache Tomcat server in this context
					addServletMapping("/Testsuite/components/BaSys/1.0/dynamicModelRepository/*", new DynamicModelProviderServlet())
			);

	
	
	
	/**
	 * Run code snippet. This code snippet illustrates the use of CRUD operations to 
	 * access Virtual Automation Bus objects
	 */
	@Test
	public void snippet() throws Exception {

		// Server connections
		// - Connect to sub model. Connecting to a sub model by its ID is discouraged, because a sub 
		//   model ID is not necessarily unique outside the scope of its AAS. If users want to connect
		//   directly to sub models, the registry needs to support this, and unique identifies (as here)
		//   must be used. For portability, users should connect to sub models instead via an AAS ID and 
		//   sub model ID tuple, as illustrated in the registry examples. 
		VABElementProxy connSubModel1 = this.connManager.connectToVABElement("de.FHG:devices.es.iese:statusSM:1.0:3:x-509:003");

		// Create factory that helps with property creation
		// - This factory creates sub model properties and ensures presence of all meta data
		MetaModelElementFactory fac = new MetaModelElementFactory();

		// Instantiate sub model
		SubModel submodel = new SubModel();
		// - Add example properties to sub model
		submodel.setId("de.FHG:devices.es.iese:statusSM:1.0:3:x-509:003");
		submodel.getProperties().put(fac.create(new Property(),       7, "prop1"));
		submodel.getProperties().put(fac.create(new Property(), "myStr", "prop2"));

		// Transfer sub model to server
		connSubModel1.createElement("aas/submodels/de.FHG:devices.es.iese:statusSM:1.0:3:x-509:003", submodel);

		
		// Retrieve sub model with SDK connector
		{
			// Create and connect SDK connector
			ConnectedAssetAdministrationShellManager manager = new ConnectedAssetAdministrationShellManager(connManager);
			// - Retrieve sub model
			ISubModel subModel = manager.retrieveSM("de.FHG:devices.es.iese:statusSM:1.0:3:x-509:003");

			// Read sub model properties
			String smId     = subModel.getId();
			String prop1Id  = subModel.getProperties().get("prop1").getId();
			int    prop1Val = (int) ((ISingleProperty) subModel.getProperties().get("prop1")).get();
			String prop2Id  = subModel.getProperties().get("prop2").getId();
			String prop2Val = (String) ((ISingleProperty) subModel.getProperties().get("prop2")).get();
			
			// Compare sub model property values
			assertTrue(smId.equals("de.FHG:devices.es.iese:statusSM:1.0:3:x-509:003"));
			assertTrue(prop1Id.equals("prop1"));
			assertTrue(prop1Val == 7);
			assertTrue(prop2Id.equals("prop2"));
			assertTrue(prop2Val.equals("myStr"));
		}
	}
}

