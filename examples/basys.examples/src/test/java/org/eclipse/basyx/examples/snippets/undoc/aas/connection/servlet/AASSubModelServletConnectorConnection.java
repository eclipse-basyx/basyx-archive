package org.eclipse.basyx.examples.snippets.undoc.aas.connection.servlet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.components.servlet.submodel.SubmodelServlet;
import org.eclipse.basyx.examples.contexts.BaSyxExamplesContext_Empty;
import org.eclipse.basyx.examples.deployment.BaSyxDeployment;
import org.eclipse.basyx.examples.support.directory.ExampleAASRegistry;
import org.eclipse.basyx.examples.support.directory.ExamplesPreconfiguredDirectory;
import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IProperty;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.vab.manager.VABConnectionManager;
import org.eclipse.basyx.vab.protocol.http.connector.HTTPConnectorProvider;
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
		 * Constructor - create sub model property
		 */
		@SuppressWarnings("unchecked")
		public SampleSubModel() {
			// Set sub model ID
			setIdShort("sm-001");

			// Add example properties
			// - Add simple property
			Property prop1 = new Property(234);
			prop1.setIdShort("prop1");
			addSubModelElement(prop1);

			Property prop11 = new Property(123);
			prop11.setIdShort("prop11");
			// - Add container property that holds other properties
			SubmodelElementCollection container = new SubmodelElementCollection();
			container.setIdShort("prop2");
			container.addElement(prop11);
			// - Add container to property map
			addSubModelElement(container);

			// Add another property manually to sub model container "properties"
			Property prop3 = new Property(17);
			prop3.setIdShort("prop3");
			{
				((Map<String, Object>) this.get("submodelElements")).put("prop3", prop3);
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
					.addAASMapping("aas-001", "") // No AAS is provided in this example
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
		ISubModel subModel = manager.retrieveSubModel(new ModelUrn("aas-001"), new ModelUrn("sm-001"));
		
		// - Retrieve sub model values and compare to expected values
		assertTrue(subModel.getIdShort().equals("sm-001"));
		assertTrue(subModel.getProperties().get("prop1").getIdShort().equals("prop1"));
		assertTrue((int) subModel.getProperties().get("prop1").get() == 234);
		assertTrue((int) subModel.getProperties().get("prop3").get() == 17);

		ISubmodelElementCollection prop2 = (ISubmodelElementCollection) subModel.getSubmodelElements().get("prop2");
		assertEquals("prop2", prop2.getIdShort());
		Map<String, IProperty> properties = prop2.getProperties();
		assertEquals(123, properties.get("prop11").get());
	}
}


