package org.eclipse.basyx.examples.snippets.aas.submodels;

import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.components.servlet.aas.AASServlet;
import org.eclipse.basyx.examples.TestContext;
import org.eclipse.basyx.examples.deployment.BaSyxDeployment;
import org.eclipse.basyx.examples.support.directory.ExamplesPreconfiguredDirectory;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.restapi.SubmodelElementProvider;
import org.eclipse.basyx.tools.webserviceclient.WebServiceJSONClient;
import org.eclipse.basyx.vab.manager.VABConnectionManager;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
import org.eclipse.basyx.vab.protocol.http.connector.HTTPConnectorProvider;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * Code snippet that illustrates the creation of an AAS sub model, and the access to the sub model via HTTP REST calls
 * 
 * @author kuhn
 *
 */
public class DynamicSubModelDeploymentHTTP {

	
	/**
	 * Create VAB connection manager backend
	 * 
	 * The connection manager uses a preconfigured directory for resolving IDs to 
	 * network addresses, and a HTTP connector to connect to VAB objects.
	 */
	protected VABConnectionManager connManager = new VABConnectionManager(
			new ExamplesPreconfiguredDirectory()
				// Add example specific mappings
					.addMapping("urn:de.FHG:devices.es.iese:aas:1.0:3:x-509:003",
							"http://localhost:8080/basys.examples/Testsuite/components/BaSys/1.0/dynamicModelRepository/aas"),
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
					// Deploy example specific servlets to Apache Tomcat server in this context
					addServletMapping("/Testsuite/components/BaSys/1.0/dynamicModelRepository/*",
							new AASServlet(new AssetAdministrationShell()))
			);

	
	
	
	/**
	 * Run code snippet. This code snippet illustrates the dynamic deployment of a sub model and the use of HTTP REST operations to 
	 * access the sub model
	 */
	@Test @SuppressWarnings("unchecked")
	public void snippet() throws Exception {

		// Server connections
		// - Connect to sub model. Connecting to a sub model by its ID is discouraged, because a sub 
		//   model ID is not necessarily unique outside the scope of its AAS. If users want to connect
		//   directly to sub models, the registry needs to support this, and unique identifies (as here)
		//   must be used. For portability, users should connect to sub models instead via an AAS ID and 
		//   sub model ID tuple, as illustrated in the registry examples. 
		VABElementProxy connSubModels = this.connManager.connectToVABElement("urn:de.FHG:devices.es.iese:aas:1.0:3:x-509:003");

		// Add example properties
		SubModel submodel = new SubModel();
		submodel.setIdShort("Status");
		Property prop1 = new Property(7);
		prop1.setIdShort("prop1");
		submodel.addSubModelElement(prop1);

		Property prop2 = new Property("myStr");
		prop2.setIdShort("prop2");
		submodel.addSubModelElement(prop2);

		// Transfer sub model to server
		connSubModels.createValue("/submodels", submodel);

		
		// Web service client accesses AAS using HTTP REST calls
		WebServiceJSONClient jsonClient = new WebServiceJSONClient();
		
		// Read property values using the WebServiceJSONClient class. 
		// - Returned property contains meta data. The actual property is stored in property "entity", property value is in entity property "value"
		String smEndpoint = "http://localhost:8080/basys.examples/Testsuite/components/BaSys/1.0/dynamicModelRepository/aas/submodels/Status";
		Map<String, Object> sm = ((Map<String, Object>) jsonClient.get(smEndpoint));
		String smId = (String) sm.get("idShort");
		int prop1Val = (int) ((Map<String, Object>) jsonClient.get(smEndpoint + "/" + SubmodelElementProvider.PROPERTIES + "/prop1")).get("value");
		String prop1Id = (String) ((Map<String, Object>) jsonClient.get(smEndpoint + "/" + SubmodelElementProvider.PROPERTIES + "/prop1")).get("idShort");
		String prop2Val = (String) ((Map<String, Object>) jsonClient.get(smEndpoint + "/" + SubmodelElementProvider.PROPERTIES + "/prop2")).get("value");

		
		// Check results
		assertTrue(smId.equals("Status"));
		assertTrue(prop1Val == 7);
		assertTrue(prop1Id.equals("prop1"));
		assertTrue(prop2Val.equals("myStr"));
	}
}

