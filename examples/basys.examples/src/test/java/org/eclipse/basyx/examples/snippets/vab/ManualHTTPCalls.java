package org.eclipse.basyx.examples.snippets.vab;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.aas.api.webserviceclient.WebServiceJSONClient;
import org.eclipse.basyx.aas.backend.connector.http.HTTPConnectorProvider;
import org.eclipse.basyx.components.servlet.submodel.EmptyVABLambdaElementServlet;
import org.eclipse.basyx.examples.contexts.BaSyxExamplesContext_1MemoryAASServer_1SQLDirectory;
import org.eclipse.basyx.examples.deployment.BaSyxDeployment;
import org.eclipse.basyx.examples.support.directory.ExamplesPreconfiguredDirectory;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
import org.junit.ClassRule;
import org.junit.Test;



/**
 * Code snippet that illustrates the use of VABvia HTTP REST calls
 * 
 * The snippet communicates with a VAB element that is deployed to a VABLambdaServlet on a
 * Apache Tomcat HTTP server instance. The VABLambdaServlet provides an empty container that
 * is able to host any VAB object.
 * 
 * @author kuhn
 *
 */
public class ManualHTTPCalls {

	
	/**
	 * Create VAB connection manager backend
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
	 * Run code snippet. This code snippet illustrates the use of HTTP operations to 
	 * access Virtual Automation Bus objects
	 */
	@Test @SuppressWarnings("unchecked")
	public void snippet() throws Exception {

		// Server connections
		// - Connect to VAB object by ID. The connection manager looks up this ID in
		//   its directory
		VABElementProxy connSubModel1 = this.connManager.connectToVABElement("urn:de.FHG:devices.es.iese:statusSM:1.0:3:x-509#003");

		
		// Create properties in VAB object using connection
		// - This code creates a simple container "properties" and two contained
		//   properties "prop1" and "prop2". Container and properties lack the 
		//   required properties for AAS and AAS sub models. They are therefore
		//   not compliant to Asset Administration Shells.
		connSubModel1.createElement("properties", new HashMap<String, Object>());
		connSubModel1.createElement("properties/prop1", 7);
		connSubModel1.createElement("properties/prop2", "myStr");
		
		
		// Web service client 
		// - This client enables the execution of generic HTTP queries that expect
		//   and return JSON encoded values. 
		WebServiceJSONClient jsonClient = new WebServiceJSONClient();
		
		
		// Read property values via WebServiceJSONClient class. 
		// - Returned property contains meta data. The actual value is stored in property "entity"
		int    prop1Val = (int) ((Map<String, Object>) jsonClient.get("http://localhost:8080/basys.examples/Testsuite/components/BaSys/1.0/devicestatusVAB/properties/prop1")).get("entity");
		String prop2Val = (String) ((Map<String, Object>) jsonClient.get("http://localhost:8080/basys.examples/Testsuite/components/BaSys/1.0/devicestatusVAB/properties/prop2")).get("entity");
		
		
		// Compare read values to expected values
		assertTrue(prop1Val == 7);
		assertTrue(prop2Val.equals("myStr"));
	}
}

