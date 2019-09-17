package org.eclipse.basyx.examples.snippets.aas.submodels;

import static org.junit.Assert.assertTrue;

import org.eclipse.basyx.aas.api.metamodel.aas.ISubModel;
import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.property.ISingleProperty;
import org.eclipse.basyx.aas.api.modelurn.ModelUrn;
import org.eclipse.basyx.aas.backend.connected.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.SubModel;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.submodelelement.property.Property;
import org.eclipse.basyx.components.servlet.submodel.DynamicModelProviderServlet;
import org.eclipse.basyx.examples.contexts.BaSyxExamplesContext_1MemoryAASServer_1SQLDirectory;
import org.eclipse.basyx.examples.deployment.BaSyxDeployment;
import org.eclipse.basyx.examples.support.directory.ExampleAASRegistry;
import org.eclipse.basyx.vab.backend.connector.http.HTTPConnectorProvider;
import org.junit.ClassRule;
import org.junit.Test;



/**
 * Code snippet that illustrates the creation of an AAS sub model
 * 
 * @author kuhn
 *
 */
public class DynamicSubModelDeployment {

	private static final String STATUS_SM = "de.FHG:devices.es.iese:statusSM:1.0:3:x-509#003";

	protected ConnectedAssetAdministrationShellManager aasManager = new ConnectedAssetAdministrationShellManager(
			new ExampleAASRegistry()
			// Ass Example specific mappings
					.addSubmodelMapping("", STATUS_SM, "http://localhost:8080/basys.examples/Testsuite/components/BaSys/1.0/dynamicModelRepository"),
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
		// Instantiate sub model
		SubModel submodel = new SubModel();
		// - Add example properties to sub model
		submodel.setId(STATUS_SM);
		Property prop1 = new Property(7);
		prop1.setId("prop1");
		submodel.addSubModelElement(prop1);

		Property prop2 = new Property("myStr");
		prop2.setId("prop2");
		submodel.addSubModelElement(prop2);

		
		
		// Transfer sub model to server
		aasManager.createSubModel(new ModelUrn(""), STATUS_SM, submodel);

		
		// Retrieve sub model with SDK connector
		{

			// Server connections
			// - Connect to sub model. Connecting to a sub model by its ID is discouraged, because a sub 
			//   model ID is not necessarily unique outside the scope of its AAS. If users want to connect
			//   directly to sub models, the registry needs to support this, and unique identifies (as here)
			//   must be used. For portability, users should connect to sub models instead via an AAS ID and 
			//   sub model ID tuple, as illustrated in the registry examples. 
			ISubModel subModel = aasManager.retrieveSubModel(new ModelUrn(""), STATUS_SM);

			// Read sub model properties
			String smId     = subModel.getId();
			String prop1Id  = subModel.getDataElements().get("prop1").getId();
			int    prop1Val = (int) ((ISingleProperty) subModel.getDataElements().get("prop1")).get();
			String prop2Id  = subModel.getDataElements().get("prop2").getId();
			String prop2Val = (String) ((ISingleProperty) subModel.getDataElements().get("prop2")).get();
			
			// Compare sub model property values
			assertTrue(smId.equals(STATUS_SM));
			assertTrue(prop1Id.equals("prop1"));
			assertTrue(prop1Val == 7);
			assertTrue(prop2Id.equals("prop2"));
			assertTrue(prop2Val.equals("myStr"));
		}
	}
}

