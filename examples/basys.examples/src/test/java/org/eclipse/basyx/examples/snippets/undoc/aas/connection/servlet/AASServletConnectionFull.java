package org.eclipse.basyx.examples.snippets.undoc.aas.connection.servlet;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.eclipse.basyx.aas.api.metamodel.aas.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.metamodel.aas.ISubModel;
import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.property.IContainerProperty;
import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.property.ISingleProperty;
import org.eclipse.basyx.aas.api.modelurn.ModelUrn;
import org.eclipse.basyx.aas.backend.connected.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.impl.metamodel.factory.MetaModelElementFactory;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.SubModel;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.submodelelement.SubmodelElement;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.submodelelement.property.SingleProperty;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.submodelelement.property.valuetypedef.PropertyValueTypeDefHelper;
import org.eclipse.basyx.components.servlet.submodel.SubmodelServlet;
import org.eclipse.basyx.examples.contexts.BaSyxExamplesContext_Empty;
import org.eclipse.basyx.examples.deployment.BaSyxDeployment;
import org.eclipse.basyx.examples.support.directory.ExampleAASRegistry;
import org.eclipse.basyx.examples.support.directory.ExamplesPreconfiguredDirectory;
import org.eclipse.basyx.vab.backend.connector.http.HTTPConnectorProvider;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
import org.junit.ClassRule;
import org.junit.Test;



/**
 * Export sub model through a servlet and connect to that servlet
 * 
 * @author kuhn
 *
 */
public class AASServletConnectionFull {

	
	/**
	 * Example sub model. This example sub model is created with the BaSyx SDK factory and defines the AAS meta model properties
	 */
	static class SampleSubModelFactory extends SubModel {
		
		/**
		 * Version number of serialized instance
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Constructor - create sub model property
		 */
		@SuppressWarnings("unchecked")
		public SampleSubModelFactory() {
			// Set sub model ID
			setId("sm-001");

			// Create factory that helps with property creation
			// - This factory creates sub model properties and ensures presence of all meta data
			MetaModelElementFactory fac = new MetaModelElementFactory();

			// Add example properties
			// - Add simple property
			SingleProperty prop1 = new SingleProperty(234);
			prop1.setId("prop1");
			addSubModelElement(prop1);

			SingleProperty prop11 = new SingleProperty(123);
			prop11.setId("prop11");
			// - Add container property that holds other properties
			List<SubmodelElement> containerProperties = fac.createList(
					prop11
				);
			// - Add container to property map
			addSubModelElement(fac.createContainer(new SubmodelElementCollection(), containerProperties, fac.emptyList(), "prop2"));

			// Add another property manually to sub model container "properties"
			SingleProperty prop3 = new SingleProperty(17);
			prop3.setId("prop3");
			{
				((Map<String, Object>) this.get("dataElements")).put("prop3", prop3);
			}
		}
	}

	
	
	/**
	 * Example sub model. This example sub model is created and resembles parts of the AAS meta model
	 */
	static class SampleSubModelManual extends SubModel {
		
		/**
		 * Version number of serialized instance
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Constructor - create sub model property
		 */
		@SuppressWarnings("unchecked")
		public SampleSubModelManual() {
			// Set sub model ID
			setId("sm-001M");

			// Add example properties
			// - Add simple property with value and idShort meta elements
			this.putPath("dataElements/prop1/value", 234);
			this.putPath("dataElements/prop1/valueType", PropertyValueTypeDefHelper.fromObject(234).toString());
			this.putPath("dataElements/prop1/idShort", "prop1");

			// Add container property that holds other properties
			this.putPath("dataElements/prop2/idShort", "prop2");
			// - Add contained property
			this.putPath("dataElements/prop2/dataElements/prop11/value", 123);
			this.putPath("dataElements/prop2/dataElements/prop11/valueType", PropertyValueTypeDefHelper.fromObject(123).toString());
			this.putPath("dataElements/prop2/dataElements/prop11/idShort", "prop11");
			
			// Add another property manually to sub model container "properties"
			// - Using the Property class ensures presence of all meta properties
			SingleProperty addedProperty = new SingleProperty(); 
			addedProperty.set(17);
			addedProperty.setId("prop3");
			// - Add property to sub model container "properties"
			{
				((Map<String, Object>) this.get(SubModel.PROPERTIES)).put("prop3", addedProperty);
			}
		}
	}

	/**
	 * VAB connection manager backend
	 */
	protected VABConnectionManager connManager = new VABConnectionManager(
			// Add example specific mappings
			new ExamplesPreconfiguredDirectory()
					// - VAB needs to know the relative path of the sub model that is defined by AAS
					// meta model
					.addMapping("sm-001VAB", "http://localhost:8080/basys.examples/Testsuite/components/BaSys/1.0/SampleModel/aas/submodels/sm-001")
					// - VAB needs to know the relative path of the sub model that is defined by AAS
					// meta model
					.addMapping("sm-001MVAB", "http://localhost:8080/basys.examples/Testsuite/components/BaSys/1.0/SampleModelManual/aas/submodels/sm-001M"),
			// We connect via HTTP
			new HTTPConnectorProvider());
	
	
	
	/**
	 * VAB connection manager backend
	 */
	protected ConnectedAssetAdministrationShellManager manager = new ConnectedAssetAdministrationShellManager(
			// Add example specific mappings
			new ExampleAASRegistry()
					.addAASMapping("aas-001", "http://localhost:8080/basys.examples/Testsuite/components/BaSys/1.0/SampleModel/aas")
					.addSubmodelMapping("aas-001", "sm-001", "http://localhost:8080/basys.examples/Testsuite/components/BaSys/1.0/SampleModel/aas/submodels/sm-001")
					.addAASMapping("aas-001M", "http://localhost:8080/basys.examples/Testsuite/components/BaSys/1.0/SampleModelManual/aas")
					.addSubmodelMapping("aas-001M", "sm-001M", "http://localhost:8080/basys.examples/Testsuite/components/BaSys/1.0/SampleModelManual/aas/submodels/sm-001M"),
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
					addServletMapping("/Testsuite/components/BaSys/1.0/SampleModel/*",       new SubmodelServlet(new SampleSubModelFactory())).
					addServletMapping("/Testsuite/components/BaSys/1.0/SampleModelManual/*", new SubmodelServlet(new SampleSubModelManual()))
			);

	
	/**
	 * Connect to sub model, query, and set property values
	 */
	@Test @SuppressWarnings("unchecked")
	public void accessSubModel() throws Exception {
		// First example - Retrieve sub model (created by factory) with SDK connector
		{
			// Create and connect SDK connector
			ISubModel subModel = manager
					.retrieveSubModel(new ModelUrn("aas-001"), "sm-001");
			// - Retrieve sub model values and compare to expected values
			assertTrue(subModel.getId().equals("sm-001"));
			assertTrue(subModel.getDataElements().get("prop1").getId().equals("prop1"));
			assertTrue((int) ((ISingleProperty) subModel.getDataElements().get("prop1")).get() == 234);
			assertTrue((int) ((ISingleProperty) subModel.getDataElements().get("prop3")).get() == 17);
			assertTrue(subModel.getDataElements().get("prop2").getId().equals("prop2"));
			assertTrue((int) ((ISingleProperty) ((IContainerProperty) subModel.getDataElements().get("prop2")).getDataElements().get("prop11")).get() == 123);

			// Retrieve dummy AAS (created by factory) with SDK connector
			IAssetAdministrationShell shell = manager.retrieveAAS(new ModelUrn("aas-001"));
			// - Retrieve AAS values and compare to expected values
			assertTrue(shell.getId().equals("---"));


			// Connect to sub model using lower-level VAB interface
			VABElementProxy connSubModel1 = this.connManager.connectToVABElement("sm-001VAB");
			// - Read property values and compare with expected values
			assertTrue((int) connSubModel1.getModelPropertyValue("dataElements/prop1/value") == 234);
			assertTrue((int) connSubModel1.getModelPropertyValue("dataElements/prop3/value") == 17);
			assertTrue(((Map<String, Object>) connSubModel1.getModelPropertyValue("dataElements/prop1")).get("idShort").equals("prop1"));
			assertTrue(((Map<String, Object>) connSubModel1.getModelPropertyValue("dataElements/prop2")).get("idShort").equals("prop2"));
			assertTrue((int) connSubModel1.getModelPropertyValue("dataElements/prop2/dataElements/prop11/value") == 123);
			// - Change property value using VAB primitive
			connSubModel1.setModelPropertyValue("dataElements/prop1/value", 456);
			// - Read value back using VAB primitive
			assertTrue((int) connSubModel1.getModelPropertyValue("dataElements/prop1/value") == 456);

			// Read changed value back using SDK connector
			assertTrue((int) ((ISingleProperty) subModel.getDataElements().get("prop1")).get() == 456);
		}

		
		// Second example - Retrieve sub model (created manually) with SDK connector
		{
			// Create and connect SDK connector
			ISubModel subModel = manager
					.retrieveSubModel(new ModelUrn("aas-001M"), "sm-001M");
			// - Retrieve sub model values and compare to expected values
			assertTrue(subModel.getId().equals("sm-001M"));
			assertTrue(subModel.getDataElements().get("prop1").getId().equals("prop1"));
			assertTrue((int) ((ISingleProperty) subModel.getDataElements().get("prop1")).get() == 234);
			assertTrue((int) ((ISingleProperty) subModel.getDataElements().get("prop3")).get() == 17);
			assertTrue(subModel.getDataElements().get("prop2").getId().equals("prop2"));
			assertTrue((int) ((ISingleProperty) ((IContainerProperty) subModel.getDataElements().get("prop2")).getDataElements().get("prop11")).get() == 123);

			// Retrieve dummy AAS (created by factory) with SDK connector
			IAssetAdministrationShell shell = manager.retrieveAAS(new ModelUrn("aas-001"));
			// - Retrieve AAS values and compare to expected values
			assertTrue(shell.getId().equals("---"));
			
			
			// Connect to sub model using lower-level VAB interface
			VABElementProxy connSubModel1 = this.connManager.connectToVABElement("sm-001MVAB");
			// - Read property values and compare with expected values
			assertTrue(((Map<String, Object>) connSubModel1.getModelPropertyValue("/")).get("idShort").equals("sm-001M"));
			assertTrue((int) connSubModel1.getModelPropertyValue("dataElements/prop1/value") == 234);
			assertTrue((int) connSubModel1.getModelPropertyValue("dataElements/prop3/value") == 17);
			assertTrue(((Map<String, Object>) connSubModel1.getModelPropertyValue("dataElements/prop1")).get("idShort").equals("prop1"));
			assertTrue(((Map<String, Object>) connSubModel1.getModelPropertyValue("dataElements/prop2")).get("idShort").equals("prop2"));
			assertTrue((int) connSubModel1.getModelPropertyValue("dataElements/prop2/dataElements/prop11/value") == 123);
			// - Change property value using VAB primitive
			connSubModel1.setModelPropertyValue("dataElements/prop1/value", 456);
			// - Read value back using VAB primitive
			assertTrue((int) connSubModel1.getModelPropertyValue("dataElements/prop1/value") == 456);

			// Read changed value back using SDK connector
			assertTrue((int) ((ISingleProperty) subModel.getDataElements().get("prop1")).get() == 456);
		}
	}
}


