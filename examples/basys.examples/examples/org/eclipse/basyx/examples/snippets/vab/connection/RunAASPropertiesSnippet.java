package org.eclipse.basyx.examples.snippets.vab.connection;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
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
 * Illustrate the use of AAS operations
 * 
 * @author kuhn
 *
 */
public class RunAASPropertiesSnippet {

	
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
		connSubModel1.createElement("properties", new HashMap<String, Object>());
		connSubModel1.createElement("properties/prop1", 7);
		connSubModel1.createElement("properties/prop2", "myStr");
		
		// Read property values
		assertTrue((int) connSubModel1.readElementValue("properties/prop1") == 7);
		assertTrue(connSubModel1.readElementValue("properties/prop2").equals("myStr"));
		
		// Update property values
		connSubModel1.updateElementValue("properties/prop1", 8);
		connSubModel1.updateElementValue("properties/prop2", "stillMine");
		
		// Read property values again
		assertTrue((int) connSubModel1.readElementValue("properties/prop1") == 8);
		assertTrue(connSubModel1.readElementValue("properties/prop2").equals("stillMine"));

		// Delete property values
		connSubModel1.deleteElement("properties/prop1");
		connSubModel1.deleteElement("properties/prop2");
		
		// Read property values again
		assertTrue(connSubModel1.readElementValue("properties/prop1") == null);
		assertTrue(connSubModel1.readElementValue("properties/prop2") == null);
	}
}

