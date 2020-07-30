package org.eclipse.basyx.examples.snippets.aas.submodels;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.components.servlet.submodel.SubmodelServlet;
import org.eclipse.basyx.examples.contexts.BaSyxExamplesContext_Empty;
import org.eclipse.basyx.examples.deployment.BaSyxDeployment;
import org.eclipse.basyx.examples.support.directory.ExampleAASRegistry;
import org.eclipse.basyx.examples.support.directory.ExamplesPreconfiguredDirectory;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Identifiable;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.restapi.SubmodelElementProvider;
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
		 * Constructor - create sub model
		 * 
		 * This sub model contains static properties, i.e. properties that have a static value assigned.
		 */
		@SuppressWarnings("unchecked")
		public SampleSubModel() {
			// Set sub model id and name
			setIdShort("smName");
			setIdentification(IdentifierType.CUSTOM, "sm-001");

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
			((Map<String, Object>) this.get("submodelElements")).put("prop3", prop3);
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
		// - Connect to sub model using lower-level VAB interface
		VABElementProxy connSubModel1 = this.connManager.connectToVABElement("sm-001VAB");
		Map<String, Object> submodel = (Map<String, Object>) connSubModel1.getModelPropertyValue("");
		Map<String, Object> smId = (Map<String, Object>) submodel.get(Identifiable.IDENTIFICATION);

		// - Read properties
		Map<String, Object> prop1 = (Map<String, Object>) connSubModel1.getModelPropertyValue(SubmodelElementProvider.PROPERTIES + "/prop1");
		Map<String, Object> prop2 = (Map<String, Object>) connSubModel1.getModelPropertyValue(SubmodelElementProvider.PROPERTIES + "/prop2");
		Map<String, Object> prop11 = (Map<String, Object>) connSubModel1.getModelPropertyValue("submodelElements/prop2/prop11");
		Map<String, Object> prop3 = (Map<String, Object>) connSubModel1.getModelPropertyValue(SubmodelElementProvider.PROPERTIES + "/prop3");

		// - Change property value using VAB primitive
		connSubModel1.setModelPropertyValue(SubmodelElementProvider.PROPERTIES + "/prop1/value", 456);

		// - Read value back using VAB primitive
		Map<String, Object> changedProp1 = (Map<String, Object>) connSubModel1
				.getModelPropertyValue(SubmodelElementProvider.PROPERTIES + "/prop1");

		// - Check results
		assertEquals("sm-001", smId.get(Identifier.ID));
		assertEquals("smName", submodel.get(Referable.IDSHORT));
		assertEquals("prop1", prop1.get(Referable.IDSHORT));
		assertEquals(234, prop1.get(Property.VALUE)); // old value of prop1 has been stored locally
		assertEquals(456, changedProp1.get(Property.VALUE)); // new value is 456
		assertEquals("prop2", prop2.get(Referable.IDSHORT));
		assertEquals("prop11", prop11.get(Referable.IDSHORT));
		assertEquals(123, prop11.get(Property.VALUE));
		assertEquals("prop3", prop3.get(Referable.IDSHORT));
		assertEquals(17, prop3.get(Property.VALUE));

	}
}


