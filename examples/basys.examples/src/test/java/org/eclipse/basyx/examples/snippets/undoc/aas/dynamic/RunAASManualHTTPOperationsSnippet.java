package org.eclipse.basyx.examples.snippets.undoc.aas.dynamic;

import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.eclipse.basyx.components.servlet.submodel.VABLambdaServlet;
import org.eclipse.basyx.examples.contexts.BaSyxExamplesContext_1MemoryAASServer_1SQLDirectory;
import org.eclipse.basyx.examples.deployment.BaSyxDeployment;
import org.eclipse.basyx.examples.support.directory.ExamplesPreconfiguredDirectory;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.property.SingleProperty;
import org.eclipse.basyx.tools.webserviceclient.WebServiceJSONClient;
import org.eclipse.basyx.vab.manager.VABConnectionManager;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
import org.eclipse.basyx.vab.protocol.http.connector.HTTPConnectorProvider;
import org.junit.ClassRule;
import org.junit.Test;



/**
 * Illustrate the use of AAS operations using HTTP REST calls
 * 
 * @author kuhn
 *
 */
public class RunAASManualHTTPOperationsSnippet {

	
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
					addServletMapping("/Testsuite/components/BaSys/1.0/devicestatusVAB/*", new VABLambdaServlet())
			);

	
	
	
	/**
	 * Test basic queries
	 */
	@Test @SuppressWarnings("unchecked")
	public void snippet() throws Exception {

		// Server connections
		// - Connect to device (VAB object)
		VABElementProxy connSubModel1 = this.connManager.connectToVABElement("urn:de.FHG:devices.es.iese:statusSM:1.0:3:x-509#003");

		// Add example properties
		SubModel submodel = new SubModel();
		submodel.setIdShort("urn:de.FHG:devices.es.iese:statusSM:1.0:3:x-509#003");
		SingleProperty prop1 = new SingleProperty(7);
		prop1.setIdShort("prop1");
		submodel.addSubModelElement(prop1);

		SingleProperty prop2 = new SingleProperty("myStr");
		prop2.setIdShort("prop2");
		submodel.addSubModelElement(prop2);

		// Transfer sub model to server
		connSubModel1.setModelPropertyValue("/", submodel);
		
		// Web service client accesses AAS using HTTP REST calls
		WebServiceJSONClient jsonClient = new WebServiceJSONClient();
		
		// Read property values
		// - Use WebServiceJSONClient class. Returned property contains meta data. The actual property is stored in property "entity", property value is in entity property "value"
		int prop1Val = (int) ((Map<String, Object>) ((Map<String, Object>) jsonClient.get("http://localhost:8080/basys.examples/Testsuite/components/BaSys/1.0/devicestatusVAB/dataElements/prop1")).get("entity")).get("value");
		String prop1Id = (String) ((Map<String, Object>) ((Map<String, Object>) jsonClient.get("http://localhost:8080/basys.examples/Testsuite/components/BaSys/1.0/devicestatusVAB/dataElements/prop1")).get("entity")).get("idShort");
		String prop2Val = (String) ((Map<String, Object>) ((Map<String, Object>) jsonClient.get("http://localhost:8080/basys.examples/Testsuite/components/BaSys/1.0/devicestatusVAB/dataElements/prop2")).get("entity")).get("value");
		
		// Check results
		assertTrue(prop1Val == 7);
		assertTrue(prop1Id.equals("prop1"));
		assertTrue(prop2Val.equals("myStr"));
	}
}

