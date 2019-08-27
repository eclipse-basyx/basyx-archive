package org.eclipse.basyx.examples.snippets.undoc.aas.connection.servlet;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.eclipse.basyx.aas.api.modelurn.ModelUrn;
import org.eclipse.basyx.aas.api.resources.IAssetAdministrationShell;
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
import org.eclipse.basyx.examples.support.directory.ExamplesPreconfiguredDirectory;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.junit.ClassRule;
import org.junit.Test;



/**
 * Export sub model through a servlet and connect to that servlet
 * 
 * @author kuhn
 *
 */
public class AASSubModelServletConnectorConnection {

	
	/**
	 * Example sub model. This example sub model is created with the BaSyx SDK factory and defines the AAS meta model properties
	 */
	static class SampleSubModel extends SubModel {
		
		/**
		 * Version number of serialized instance
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Constructor - create sub model property
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
			getProperties().put(fac.create(new Property(), 234, "prop1"));

			// - Add container property that holds other properties
			List<SubmodelElement> containerProperties = fac.createList(
					fac.create(new Property(), 123, "prop11")
				);
			// - Add container to property map
			getProperties().put(fac.createContainer(new SubmodelElementCollection(), containerProperties, fac.emptyList(), "prop2"));

			// Add another property manually to sub model container "properties"
			{
				((Map<String, Object>) this.get(PROPERTIES)).put("prop3", fac.create(new Property(), 17, "prop3"));
			}
		}
	}
	
	/**
	 * Create manager using the directory stub an the HTTPConnectorProvider
	 */
	ConnectedAssetAdministrationShellManager manager = new ConnectedAssetAdministrationShellManager(
			// Add example specific mappings
			new ExampleAASRegistry()
			    // - SDK connectors encapsulate relative path to sub model (/aas/submodels/sm-001)
				.addAASMapping("aas-001",    "http://localhost:8080/basys.examples/Testsuite/components/BaSys/1.0/SampleModel")
					.addSubmodelMapping("aas-001", "sm-001", "http://localhost:8080/basys.examples/Testsuite/components/BaSys/1.0/SampleModel"),
			// We connect via HTTP
			new HTTPConnectorProvider());

	/**
	 * VAB connection manager backend
	 */
	protected VABConnectionManager connManager = new VABConnectionManager(
			// Add example specific mappings
			new ExamplesPreconfiguredDirectory()
			    // - VAB needs to know the relative path of the sub model that is defined by AAS meta model
			    .addMapping("sm-001VAB",  "http://localhost:8080/basys.examples/Testsuite/components/BaSys/1.0/SampleModel/aas/submodels/sm-001"),
			// We connect via HTTP
			new HTTPConnectorProvider());

	
	
	/**
	 * Instantiate and start context elements for this example. BaSyxDeployment contexts instantiate all
	 * components on the IP address of the host. Therefore, all components use the same IP address. 
	 */
	@ClassRule
	public static BaSyxDeployment context = new BaSyxDeployment(
				// Servlets for example snippet
				new BaSyxExamplesContext_Empty().
					// Deploy example specific servlets to Tomcat server in this context
					addServletMapping("/Testsuite/components/BaSys/1.0/SampleModel/*",       new SubmodelServlet(new SampleSubModel()))
			);

	
	/**
	 * Application code: Connect to sub model, query, and set property values
	 */
	@Test
	public void accessSubModel() throws Exception {
		
		// Create and connect SDK connector
		ISubModel subModel = manager.retrieveSM("sm-001", new ModelUrn("aas-001"));
		
		// - Retrieve sub model values and compare to expected values
		assertTrue(subModel.getId().equals("sm-001"));
		assertTrue(subModel.getProperties().get("prop1").getId().equals("prop1"));
		assertTrue((int) ((ISingleProperty) subModel.getProperties().get("prop1")).get() == 234);
		assertTrue((int) ((ISingleProperty) subModel.getProperties().get("prop3")).get() == 17);
		assertTrue(subModel.getProperties().get("prop2").getId().equals("prop2"));
		assertTrue((int) ((ISingleProperty) ((IContainerProperty) subModel.getProperties().get("prop2")).getProperties().get("prop11")).get() == 123);

		// Retrieve dummy AAS (created by factory) with SDK connector
		IAssetAdministrationShell shell = manager.retrieveAAS(new ModelUrn("aas-001"));
		// - Retrieve AAS values and compare to expected values
		assertTrue(shell.getId().equals("---"));
	}
}


