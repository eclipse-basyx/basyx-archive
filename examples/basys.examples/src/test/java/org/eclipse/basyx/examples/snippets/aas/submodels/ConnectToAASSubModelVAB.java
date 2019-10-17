package org.eclipse.basyx.examples.snippets.aas.submodels;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.basyx.aas.factory.java.MetaModelElementFactory;
import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.components.servlet.submodel.SubmodelServlet;
import org.eclipse.basyx.examples.contexts.BaSyxExamplesContext_Empty;
import org.eclipse.basyx.examples.deployment.BaSyxDeployment;
import org.eclipse.basyx.examples.support.directory.ExampleAASRegistry;
import org.eclipse.basyx.examples.support.directory.ExamplesPreconfiguredDirectory;
import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
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
 * Code snippet that illustrates the use of CRUD Virtual Automation Bus (VAB) operations
 * 
 * The snippet communicates with a VAB element that is deployed to a VABLambdaServlet on a
 * Apache Tomcat HTTP server instance. The VABLambdaServlet provides an empty container that
 * is able to host any VAB object.
 * 
 * @author kuhn
 *
 */
public class ConnectToAASSubModelVAB {

	
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
			setIdShort("sm-001");

			// Create factory that helps with property creation
			// - This factory creates sub model properties and ensures presence of all meta
			// data
			MetaModelElementFactory fac = new MetaModelElementFactory();

			// Add example properties
			// - Add simple property
			SingleProperty prop1 = new SingleProperty(234);
			prop1.setIdShort("prop1");
			addSubModelElement(prop1);

			SingleProperty prop11 = new SingleProperty(123);
			prop11.setIdShort("prop11");
			// - Add container property that holds other properties
			List<SubmodelElement> containerProperties = fac.createList(prop11);
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
	 * Create VAB connection manager backend
	 * 
	 * The connection manager uses a preconfigured directory for resolving IDs to 
	 * network addresses, and a HTTP connector to connect to VAB objects.
	 */
	protected VABConnectionManager connManager = new VABConnectionManager(
			// Add example specific mappings
			new ExamplesPreconfiguredDirectory()
				.addMapping("aas-001",    "http://localhost:8080/basys.examples/Testsuite/components/BaSys/1.0/SampleModel/")
			    .addMapping("sm-001",     "http://localhost:8080/basys.examples/Testsuite/components/BaSys/1.0/SampleModel/")
			    .addMapping("sm-001VAB",  "http://localhost:8080/basys.examples/Testsuite/components/BaSys/1.0/SampleModel/"),
			// We connect via HTTP
			new HTTPConnectorProvider());

	protected ConnectedAssetAdministrationShellManager manager = new ConnectedAssetAdministrationShellManager(
			new ExampleAASRegistry()
					.addAASMapping("aas-001", "http://localhost:8080/basys.examples/Testsuite/components/BaSys/1.0/SampleModel/")
					.addSubmodelMapping("aas-001", "sm-001", "http://localhost:8080/basys.examples/Testsuite/components/BaSys/1.0/SampleModel/"),
			// We connect via HTTP
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
				// Servlets for example snippet
				new BaSyxExamplesContext_Empty().
					// Deploy example specific servlets to Tomcat server in this context
					addServletMapping("/Testsuite/components/BaSys/1.0/SampleModel/*",       new SubmodelServlet(new SampleSubModel()))
			);

	
	/**
	 * Run code snippet. Access sub model, query data
	 */
	@Test @SuppressWarnings("unchecked")
	public void accessSubModel() throws Exception {
		// Retrieve sub model (created by factory) with SDK connector
		{
			// Connect to sub model using lower-level VAB interface
			VABElementProxy connSubModel1 = this.connManager.connectToVABElement("sm-001VAB");

			// - Read properties
			String prop1Id = (String) ((Map<String, Object>) connSubModel1.getModelPropertyValue("dataElements/prop1")).get("idShort");
			int prop1Val = (int) ((HashMap<String, Object>) connSubModel1.getModelPropertyValue("dataElements/prop1/value")).get("value");
			int prop3Val = (int) ((HashMap<String, Object>) connSubModel1.getModelPropertyValue("dataElements/prop3/value")).get("value");
			String prop2Id = (String) ((Map<String, Object>) connSubModel1.getModelPropertyValue("dataElements/prop2")).get("idShort");
			int prop211 = (int) ((Map<String, Object>) connSubModel1.getModelPropertyValue("dataElements/prop2/dataElements/prop11/value")).get("value");
			// - Change property value using VAB primitive
			connSubModel1.setModelPropertyValue("dataElements/prop1/value", 456);
			// - Read value back using VAB primitive
			int changedProp = (int) ((Map<String, Object>) connSubModel1.getModelPropertyValue("dataElements/prop1/value")).get("value");

			
			// Create and connect SDK connector
			ISubModel subModel = manager.retrieveSubModel(new ModelUrn("aas-001"), "sm-001");
			// - Retrieve sub model values and compare to expected values
			String smID     = subModel.getIdShort();

			
			// Check results
			assertEquals("sm-001", smID);
			assertEquals("prop1", prop1Id);
			assertEquals(234, prop1Val);
			assertEquals(17, prop3Val);
			assertEquals("prop2", prop2Id);
			assertEquals(123, prop211);
			assertEquals(456, changedProp);
		}
	}
}


