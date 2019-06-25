package org.eclipse.basyx.examples.snippets.vab;

import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.util.Map;
import java.util.function.Supplier;

import org.eclipse.basyx.aas.backend.connector.http.HTTPConnectorProvider;
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
 * The use of a Supplier class is an alternative approach for defining dynamic operations
 * 
 * Supplier classes implement get operations, but support more complex implementations
 * compared to Lambda expressions.
 * 
 * @author kuhn
 *
 */
class TailoredBaSyxSupplier implements Supplier<Object>, Serializable {

	/**
	 * Version number of serialized instances
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * Getter operation - return property value
	 */
	@Override
	public Object get() {
		// Delegate call to tailored BaSyx supplier base class
		return getInternal();
	}

	
	/**
	 * Example function of tailored BaSyx supplier base class
	 */
	protected String getInternal() {
		// Return constant value
		return "BaSyxSupplier!";
	}
}




/**
 * Code snippet that illustrates the dynamic deployment of VAB object operations. This 
 * example defines the dynamic operations as a Class. This enables the operation implementations 
 * to define and maintain properties.
 * 
 * The snippet communicates with a VAB element that is deployed to a VABLambdaServlet on a
 * Apache Tomcat HTTP server instance. The VABLambdaServlet provides an empty container that
 * is able to host any VAB object. It supports dynamic properties with lambda expressions, 
 * i.e. all get/set/create/delete operations for every property may be replaced with a 
 * lambda operation.
 * 
 * @author kuhn
 *
 */
public class DynamicPropertyClass {

	
	/**
	 * VAB connection manager backend
	 * 
	 * The connection manager uses a preconfigured directory for resolving IDs to 
	 * network addresses, and a HTTP connector to connect to VAB objects.
	 */
	protected VABConnectionManager connManager = new VABConnectionManager(
			new ExamplesPreconfiguredDirectory()
				// Add example specific mappings
			    .addMapping("urn:de.FHG:devices.es.iese:statusSM:1.0:3:x-509#003",  "http://localhost:8080/basys.examples/Testsuite/components/BaSys/1.0/devicestatusVAB/"),
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
					// Deploy example specific servlets to Tomcat server in this context
					addServletMapping("/Testsuite/components/BaSys/1.0/devicestatusVAB/*", new EmptyVABLambdaElementServlet())
			);

		
	
	/**
	 * Run code snippet. This code snippet illustrates the creation and uploading 
	 * of dynamic operations on a EmptyVABLambdaElementServlet servlet. 
	 */
	@Test
	public void snippet() throws Exception {

		// Server connections
		// - Connect to device (VAB object)
		VABElementProxy connSubModel1 = this.connManager.connectToVABElement("urn:de.FHG:devices.es.iese:statusSM:1.0:3:x-509#003");

		
		// Create dynamic get/set operation. Instantiate class TailoredBaSyxSupplier as getter, 
		// no setter (null) is provided.
		Map<String, Object> dynamicPropertyVal = VABLambdaProviderHelper.createSimple(new TailoredBaSyxSupplier(), null);
		// - Update property properties/dynamicExample with dynamic get/set operation
		connSubModel1.updateElementValue("dynamicExampleProperty", dynamicPropertyVal);

		// Read dynamicExample property
		Object propertyValue = connSubModel1.readElementValue("dynamicExampleProperty");

		
		// Compare returned to expected values
		assertTrue(propertyValue.equals("BaSyxSupplier!"));
	}
}
