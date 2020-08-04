package org.eclipse.basyx.examples.snippets.vab;

import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.util.Map;
import java.util.function.Supplier;

import org.eclipse.basyx.components.servlet.vab.VABLambdaServlet;
import org.eclipse.basyx.examples.TestContext;
import org.eclipse.basyx.examples.deployment.BaSyxDeployment;
import org.eclipse.basyx.examples.support.directory.ExamplesPreconfiguredDirectory;
import org.eclipse.basyx.vab.manager.VABConnectionManager;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
import org.eclipse.basyx.vab.modelprovider.lambda.VABLambdaProviderHelper;
import org.eclipse.basyx.vab.protocol.http.connector.HTTPConnectorProvider;
import org.junit.ClassRule;
import org.junit.Test;



/**
 * Code snippet that illustrates the dynamic deployment of VAB object operations with
 * Lambda expressions
 * 
 * The snippet communicates with a VAB element that is deployed to a VABLambdaServlet on a
 * Apache Tomcat HTTP server instance. The VABLambdaServlet provides an empty container that
 * is able to host any VAB object. It supports dynamic properties with lambda expressions, 
 * i.e. all get/set/create/delete operations for every property may be replaced with a 
 * lambda operation.
 *
 * 
 * @author kuhn
 *
 */
public class DynamicPropertyLambda {

	
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
				TestContext.sqlContext.
					// Deploy example specific servlets to Tomcat server in this context
					addServletMapping("/Testsuite/components/BaSys/1.0/devicestatusVAB/*", new VABLambdaServlet())
			);

	
	
	
	/**
	 * Run code snippet. This code snippet illustrates the creation and uploading 
	 * of dynamic operations on a EmptyVABLambdaElementServlet servlet. 
	 */
	@Test
	public void snippet() throws Exception {

		// Server connections
		// - Connect to VAB object by ID. The connection manager looks up this ID in
		//   its directory
		VABElementProxy connSubModel1 = this.connManager.connectToVABElement("urn:de.FHG:devices.es.iese:statusSM:1.0:3:x-509#003");

		
		// Create dynamic get/operation as Lambda expression, no set operation (null) is provided.
		Map<String, Object> dynamicPropertyVal = VABLambdaProviderHelper.createSimple((Supplier<Object> & Serializable) () -> {
			return "dynamicExampleValue";
		}, null);
		// - Update property properties/dynamicExample with dynamic get/set operation
		connSubModel1.createValue("dynamicExampleProperty", dynamicPropertyVal);

		// Read dynamicExample property
		// - This will invoke the previously uploaded Lambda expression
		Object propertyValue = connSubModel1.getModelPropertyValue("dynamicExampleProperty");
		
		
		// Compare returned to expected values
		assertTrue(propertyValue.equals("dynamicExampleValue"));
	}
}

