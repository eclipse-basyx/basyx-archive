package org.eclipse.basyx.examples.snippets.undoc.aas.dynamic;

import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.util.Map;
import java.util.function.Supplier;

import org.eclipse.basyx.aas.backend.connector.http.HTTPConnectorProvider;
import org.eclipse.basyx.aas.metamodel.factory.MetaModelElementFactory;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.SubModel;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property.Property;
import org.eclipse.basyx.components.servlet.submodel.EmptyVABLambdaElementServlet;
import org.eclipse.basyx.examples.contexts.BaSyxExamplesContext_1MemoryAASServer_1SQLDirectory;
import org.eclipse.basyx.examples.deployment.BaSyxDeployment;
import org.eclipse.basyx.examples.support.directory.ExamplesPreconfiguredDirectory;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
import org.eclipse.basyx.vab.provider.lambda.VABLambdaProviderHelper;
import org.junit.ClassRule;
import org.junit.Test;



/**
 * Illustrate the dynamic deployment of AAS operations
 * 
 * @author kuhn
 *
 */
public class RunAASDynamicOperationSnippet {

	
	/**
	 * VAB connection manager backend
	 */
	protected VABConnectionManager connManager = new VABConnectionManager(
			new ExamplesPreconfiguredDirectory()
				// Add example specific mappings
			    .addMapping("urn:de.FHG:devices.es.iese:statusSM:1.0:3:x-509#003",  "http://localhost:8080/basys.examples/Testsuite/components/BaSys/1.0/devicestatusVAB/"),
			new HTTPConnectorProvider());

	
	/**
	 * Instantiate and start context elements for this example. BaSyxDeployment contexts instantiate all
	 * components on the IP address of the host. Therefore, all components use the same IP address. 
	 */
	@ClassRule
	public static BaSyxDeployment context = new BaSyxDeployment(
				// Simulated servlets
				// - BaSys topology with one AAS Server and one SQL directory
				new BaSyxExamplesContext_1MemoryAASServer_1SQLDirectory().
					// Deploy example specific servlets to Tomcat server in this context
					addServletMapping("/Testsuite/components/BaSys/1.0/devicestatusVAB/*", new EmptyVABLambdaElementServlet())
			);

	
	
	
	/**
	 * Test basic queries
	 */
	@Test
	public void snippet() throws Exception {

		// Server connections
		// - Connect to device (VAB object)
		VABElementProxy connSubModel1 = this.connManager.connectToVABElement("urn:de.FHG:devices.es.iese:statusSM:1.0:3:x-509#003");

		
		// Create properties on AAS
		// - This factory creates sub model properties and ensures presence of all meta data
		MetaModelElementFactory fac = new MetaModelElementFactory();
		// - Add example properties
		SubModel submodel = new SubModel();
		submodel.setId("urn:de.FHG:devices.es.iese:statusSM:1.0:3:x-509#003");
		submodel.getProperties().put(fac.create(new Property(),       7, "prop1"));
		submodel.getProperties().put(fac.create(new Property(), "myStr", "prop2"));
		// - Transfer sub model to server
		connSubModel1.updateElementValue("/", submodel);

		
		// Read property values
		String prop2Val = (String) connSubModel1.readElementValue("dataElements/prop2/value");
		// - Check property values
		assertTrue(prop2Val.equals("myStr"));

		
		// Create dynamic get/set operation as lambda expression
		Map<String, Object> dynamicPropertyVal = VABLambdaProviderHelper.createSimple((Supplier<Object> & Serializable) () -> {
			return "dynamicExampleValue";
		}, null);
		// - Update property properties/dynamicExample with dynamic get/set operation
		connSubModel1.updateElementValue("dataElements/prop2/value", dynamicPropertyVal);

		
		// Read dynamicExample property
		prop2Val = (String) connSubModel1.readElementValue("dataElements/prop2/value");
		// - Check property values
		assertTrue(prop2Val.equals("dynamicExampleValue"));
	}
}

