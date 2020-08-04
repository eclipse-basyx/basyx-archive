package org.eclipse.basyx.examples.snippets.undoc.aas.dynamic;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.eclipse.basyx.components.servlet.vab.VABLambdaServlet;
import org.eclipse.basyx.examples.TestContext;
import org.eclipse.basyx.examples.deployment.BaSyxDeployment;
import org.eclipse.basyx.examples.support.directory.ExamplesPreconfiguredDirectory;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
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
				TestContext.sqlContext.
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

		int prop1Val = 7;
		String prop1IdShort = "prop1";
		String prop2Val = "myStr";
		// Add example properties
		SubModel submodel = new SubModel();
		submodel.setIdShort("urn:de.FHG:devices.es.iese:statusSM:1.0:3:x-509#003");
		Property prop1 = new Property(prop1Val);
		prop1.setIdShort(prop1IdShort);
		submodel.addSubModelElement(prop1);

		Property prop2 = new Property(prop2Val);
		prop2.setIdShort("prop2");
		submodel.addSubModelElement(prop2);

		// Transfer sub model to server
		connSubModel1.setModelPropertyValue("/", submodel);
		
		// Web service client accesses AAS using HTTP REST calls
		WebServiceJSONClient jsonClient = new WebServiceJSONClient();
		
		// Read property values
		// - Use WebServiceJSONClient class. Returned property contains meta data. The actual property is stored in property "entity", property value is in entity property "value"
		int retrievedProp1Val = (int) ((Map<String, Object>) jsonClient.get("http://localhost:8080/basys.examples/Testsuite/components/BaSys/1.0/devicestatusVAB/submodelElements/prop1")).get("value");
		String retrievedProp1Id = (String) ((Map<String, Object>) jsonClient.get("http://localhost:8080/basys.examples/Testsuite/components/BaSys/1.0/devicestatusVAB/submodelElements/prop1")).get("idShort");
		String retrievedProp2Val = (String) ((Map<String, Object>) jsonClient.get("http://localhost:8080/basys.examples/Testsuite/components/BaSys/1.0/devicestatusVAB/submodelElements/prop2")).get("value");
		
		// Check results
		assertEquals(prop1Val, retrievedProp1Val);
		assertEquals(prop1IdShort, retrievedProp1Id);
		assertEquals(prop2Val, retrievedProp2Val);
	}
}

