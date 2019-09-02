package org.eclipse.basyx.examples.snippets.aas.submodels;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.eclipse.basyx.aas.api.modelurn.ModelUrn;
import org.eclipse.basyx.aas.api.resources.IContainerProperty;
import org.eclipse.basyx.aas.api.resources.ISingleProperty;
import org.eclipse.basyx.aas.api.resources.ISubModel;
import org.eclipse.basyx.aas.backend.connected.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.backend.connector.http.HTTPConnectorProvider;
import org.eclipse.basyx.aas.metamodel.factory.MetaModelElementFactory;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.SubModel;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.SubmodelElement;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property.Property;
import org.eclipse.basyx.components.servlet.submodel.SubmodelServlet;
import org.eclipse.basyx.examples.contexts.BaSyxExamplesContext_Empty;
import org.eclipse.basyx.examples.deployment.BaSyxDeployment;
import org.eclipse.basyx.examples.support.directory.ExampleAASRegistry;
import org.junit.ClassRule;
import org.junit.Test;



/**
 * Code snippet that illustrates the use of CRUD Virtual Automation Bus (VAB) operations
 * 
 * The snippet communicates with a VAB element that is deployed to a VABLambdaServlet on a
 * Apache Tomcat HTTP server instance. The VABLambdaServlet provides an empty container that
 * is able to host any VAB object.
 * 
 * @author kuhn
 *
 */
public class ConnectToAASSubModelSDK {

	
	/**
	 * Example sub model. This example sub model is created with the BaSyx SDK factory and defines the AAS meta model properties
	 */
	static class SampleSubModel extends SubModel {
		
		/**
		 * Version number of serialized instance
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Constructor - create sub model
		 * 
		 * This sub model contains static properties, i.e. properties that have a static value assigned.
		 */
		@SuppressWarnings("unchecked")
		public SampleSubModel() {
			// Set sub model ID
			setId("sm-001");

			// Create factory that helps with property creation
			// - This factory creates sub model properties and ensures presence of all meta data
			MetaModelElementFactory fac = new MetaModelElementFactory();

			// Add example properties
			// - Add simple property
			Property prop1 = new Property(234);
			prop1.setId("prop1");
			getProperties().put(prop1);

			Property prop11 = new Property(123);
			prop11.setId("prop11");
			// - Add container property that holds other properties
			List<SubmodelElement> containerProperties = fac.createList(prop11);
			// - Add container to property map
			getProperties().put(fac.createContainer(new SubmodelElementCollection(), containerProperties, fac.emptyList(), "prop2"));

			// Add another property manually to sub model container "properties"
			Property prop3 = new Property(17);
			prop3.setId("prop3");
			{
				((Map<String, Object>) this.get("dataElements")).put("prop3", prop3);
			}
		}
	}

	
	
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
					addServletMapping("/Testsuite/components/BaSys/1.0/SampleModel/*",       new SubmodelServlet(new SampleSubModel()))
			);

	
	/**
	 * Run code snippet. Access sub model, query data
	 */
	@Test
	public void accessSubModel() throws Exception {
		// Create the AAS registry
		ExampleAASRegistry registry = new ExampleAASRegistry();
		registry.addSubmodelMapping("", "sm-001", "http://localhost:8080/basys.examples/Testsuite/components/BaSys/1.0/SampleModel").addAASMapping("aas-001",
				"http://localhost:8080/basys.examples/Testsuite/components/BaSys/1.0/SampleModel");
		// Create manager using the directory stub and the HTTPConnectorProvider
		ConnectedAssetAdministrationShellManager manager = new ConnectedAssetAdministrationShellManager(registry,
				// We connect via HTTP
				new HTTPConnectorProvider());
		
		
		// Retrieve sub model (created by factory) with SDK connector
		{
			// Create and connect SDK connector
			ISubModel subModel = manager.retrieveSubModel(new ModelUrn(""), "sm-001");
			// - Retrieve sub model values and compare to expected values
			String smID     = subModel.getId();
			String prop1Id  = subModel.getProperties().get("prop1").getId();
			int    prop1Val = (int) ((ISingleProperty) subModel.getProperties().get("prop1")).get();
			int    prop3Val = (int) ((ISingleProperty) subModel.getProperties().get("prop3")).get();
			String prop2Id  = subModel.getProperties().get("prop2").getId();
			int    prop211  = (int) ((ISingleProperty) ((IContainerProperty) subModel.getProperties().get("prop2")).getProperties().get("prop11")).get();

			
			// Check results
			assertTrue(smID.equals("sm-001"));
			assertTrue(prop1Id.equals("prop1"));
			assertTrue(prop1Val == 234);
			assertTrue(prop3Val == 17);
			assertTrue(prop2Id.equals("prop2"));
			assertTrue(prop211 == 123);
		}
	}
}


