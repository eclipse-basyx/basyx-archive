package org.eclipse.basyx.examples.snippets.aas.submodels;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.eclipse.basyx.aas.factory.java.MetaModelElementFactory;
import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.components.servlet.submodel.SubmodelServlet;
import org.eclipse.basyx.examples.contexts.BaSyxExamplesContext_Empty;
import org.eclipse.basyx.examples.deployment.BaSyxDeployment;
import org.eclipse.basyx.examples.support.directory.ExampleAASRegistry;
import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.IDataElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.property.IContainerProperty;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.property.ISingleProperty;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.property.ContainerProperty;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.property.SingleProperty;
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
			// Set sub model id and name
			setIdShort("smName");
			setIdentification(IdentifierType.Custom, "sm-001");

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
			List<SubmodelElement> containerProperties = fac.createList(prop11);
			// - Add container to property map
			addSubModelElement(fac.createContainer(new ContainerProperty(), containerProperties, fac.emptyList(), "prop2"));

			// Add another property manually to sub model container "properties"
			SingleProperty prop3 = new SingleProperty(17);
			prop3.setIdShort("prop3");
			((Map<String, Object>) this.get("dataElements")).put("prop3", prop3);
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
		registry.addAASMapping("aas-001", "http://localhost:8080/basys.examples/Testsuite/components/BaSys/1.0/SampleModel/")
			.addSubmodelMapping("aas-001", "sm-001", "http://localhost:8080/basys.examples/Testsuite/components/BaSys/1.0/SampleModel/");
		
		// Create manager using the directory stub and the HTTPConnectorProvider
		ConnectedAssetAdministrationShellManager manager = new ConnectedAssetAdministrationShellManager(registry,
				// We connect via HTTP
				new HTTPConnectorProvider());
		
		
		// Retrieve sub model (created by factory) with SDK connector
		// - Create and connect SDK connector
		IIdentifier aasId = new Identifier(IdentifierType.Custom, "aas-001");
		IIdentifier smId = new Identifier(IdentifierType.Custom, "sm-001");
		ISubModel subModel = manager.retrieveSubModel(aasId, smId);

		// - Retrieve sub model values and compare to expected values
		Map<String, IDataElement> dataElements = subModel.getDataElements();
		ISingleProperty prop1 = (ISingleProperty) dataElements.get("prop1");
		IContainerProperty prop2 = (IContainerProperty) dataElements.get("prop2");
		ISingleProperty prop11 = (ISingleProperty) prop2.getDataElements().get("prop11");
		ISingleProperty prop3 = (ISingleProperty) dataElements.get("prop3");

		assertEquals(smId.getId(), subModel.getIdentification().getId());
		assertEquals("smName", subModel.getIdShort());
		assertEquals("prop1", prop1.getIdShort());
		assertEquals(234, prop1.get());
		assertEquals("prop2", prop2.getIdShort());
		assertEquals("prop11", prop11.getIdShort());
		assertEquals(123, prop11.get());
		assertEquals("prop3", prop3.getIdShort());
		assertEquals(17, prop3.get());
	}
}


