package org.eclipse.basyx.examples.snippets.undoc.aas.connection.servlet;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.eclipse.basyx.aas.factory.java.MetaModelElementFactory;
import org.eclipse.basyx.components.servlet.submodel.SubmodelServlet;
import org.eclipse.basyx.examples.contexts.BaSyxExamplesContext_Empty;
import org.eclipse.basyx.examples.deployment.BaSyxDeployment;
import org.eclipse.basyx.examples.support.directory.ExamplesPreconfiguredDirectory;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.property.ContainerProperty;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.property.SingleProperty;
import org.eclipse.basyx.vab.manager.VABConnectionManager;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
import org.eclipse.basyx.vab.protocol.http.connector.HTTPConnectorProvider;
import org.junit.ClassRule;
import org.junit.Test;



/**
 * Export sub model through a servlet and connect to that servlet
 * 
 * @author kuhn
 *
 */
public class AASSubModelServletVABConnection {

	
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
			setIdShort("sm-001");

			// Create factory that helps with property creation
			// - This factory creates sub model properties and ensures presence of all meta data
			MetaModelElementFactory fac = new MetaModelElementFactory();

			// Add example properties
			// - Add simple property
			SingleProperty prop1 = new SingleProperty(234);
			prop1.setIdShort("prop1");
			addSubModelElement(prop1);

			SingleProperty prop11 = new SingleProperty(123);
			prop11.setIdShort("prop11");
			// - Add container property that holds other properties
			List<SubmodelElement> containerProperties = fac.createList(
					prop11
				);
			// - Add container to property map
			addSubModelElement(fac.createContainer(new ContainerProperty(), containerProperties, fac.emptyList(), "prop2"));

			// Add another property manually to sub model container "properties"
			SingleProperty prop3 = new SingleProperty(17);
			prop3.setIdShort("prop3");
			{
				((Map<String, Object>) this.get("dataElements")).put("prop3", prop3);
			}
		}
	}
	
	
	
	/**
	 * VAB connection manager backend
	 */
	protected VABConnectionManager connManager = new VABConnectionManager(
			// Add example specific mappings
			new ExamplesPreconfiguredDirectory()
					// Entries map directly at the SubmodelServlet
				.addMapping("aas-001",    "http://localhost:8080/basys.examples/Testsuite/components/BaSys/1.0/SampleModel")
			    .addMapping("sm-001",     "http://localhost:8080/basys.examples/Testsuite/components/BaSys/1.0/SampleModel")
			    .addMapping("sm-001VAB",  "http://localhost:8080/basys.examples/Testsuite/components/BaSys/1.0/SampleModel"),
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
	 * Connect to sub model, query, and set property values
	 */
	@Test @SuppressWarnings("unchecked")
	public void accessSubModel() throws Exception {
		// Connect to sub model using lower-level VAB interface
		VABElementProxy connSubModel1 = this.connManager.connectToVABElement("sm-001VAB");
		// - Read property values and compare with expected values
		assertTrue((int) ((Map<String, Object>) connSubModel1.getModelPropertyValue("dataElements/prop1")).get("value") == 234);
		assertTrue((int) ((Map<String, Object>) connSubModel1.getModelPropertyValue("dataElements/prop3")).get("value") == 17);
		assertTrue(((Map<String, Object>) connSubModel1.getModelPropertyValue("dataElements/prop1")).get("idShort").equals("prop1"));
		assertTrue(((Map<String, Object>) connSubModel1.getModelPropertyValue("dataElements/prop2")).get("idShort").equals("prop2"));
		assertTrue((int) ((Map<String, Object>) connSubModel1.getModelPropertyValue("dataElements/prop2/dataElements/prop11/value")).get("value") == 123);
		// - Change property value using VAB primitive
		connSubModel1.setModelPropertyValue("dataElements/prop1/value", 456);
		// - Read value back using VAB primitive
		assertTrue((int) ((Map<String, Object>) connSubModel1.getModelPropertyValue("dataElements/prop1/value")).get("value") == 456);
	}
}


