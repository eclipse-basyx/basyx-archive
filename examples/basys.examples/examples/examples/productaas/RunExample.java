package examples.productaas;

import java.net.URLEncoder;
import java.util.Map;

import org.eclipse.basyx.aas.backend.connector.http.HTTPConnectorProvider;
import org.eclipse.basyx.aas.backend.http.tools.GSONTools;
import org.eclipse.basyx.aas.backend.http.tools.factory.DefaultTypeFactory;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.identifier.IdentifierType;
import org.eclipse.basyx.regression.support.server.AASHTTPServerResource;
import org.eclipse.basyx.tools.webserviceclient.WebServiceRawClient;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
import org.junit.ClassRule;
import org.junit.Test;

import basys.examples.aasdescriptor.AASDescriptor;
import basys.examples.urntools.ModelUrn;
import examples.contexts.BaSyxExamplesContext_1MemoryAASServer_1SQLDirectory;
import examples.directory.ExamplesDirectory;



/**
 * Run example for product AAS
 * 
 * @author kuhn
 *
 */
public class RunExample {

	
	/**
	 * VAB connection manager backend
	 */
	protected VABConnectionManager connManager = new VABConnectionManager(new ExamplesDirectory(), new HTTPConnectorProvider());

	
	/** 
	 * Makes sure Tomcat Server with basic BaSys topology is started
	 */
	@ClassRule
	public static AASHTTPServerResource res = AASHTTPServerResource.getTestResource(new BaSyxExamplesContext_1MemoryAASServer_1SQLDirectory());

	
	
	/**
	 * Test basic queries
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void test() throws Exception {
		// Create GSON serializer
		GSONTools serializer = new GSONTools(new DefaultTypeFactory());

		
		// Server connections
		// - Connect to AAS server
		VABElementProxy connSubModel = this.connManager.connectToVABElement("AASServer");
		// - Invoke BaSyx service calls via web services
		WebServiceRawClient client = new WebServiceRawClient();
		// - Directory web service URL
		String wsURL = "http://localhost:8080/basys.examples/Components/Directory/SQL";
		// - AAS repository server URL
		String aasSrvURL = "http://localhost:8080/basys.examples/Components/BaSys/1.0/aasServer";
		
		
		// Create product AAS
		// - Product ID (urn:<legalEntity>:<subUnit>:<subModel>:<version>:<revision>:<elementID>#<elementInstance>)
		ModelUrn productID = new ModelUrn("de.FHG", "products.es.iese", "aas", "1.0", "3", "product1", null);
		// - Create map with complex type
		AssetAdministrationShell aas = new AssetAdministrationShell();
		aas.put("idShort", "ProductIDShort");
		// - AAS URL on server
		//String aasURLOnServer = "/aas/submodels/rawSampleCFG/"+productID.getEncodedURN();
		String aasURLOnServer = "/aas/submodels/aasRepository/"+productID.getEncodedURN();
		// - Create AAS structure on server
		connSubModel.createElement(aasURLOnServer, aas);

		
		// Delete AAS registration for a fresh start - ignore if URL was not found. In this case, there was no previous registration and the registry was clean
		client.delete(wsURL+"/api/v1/registry/"+URLEncoder.encode(productID.getURN()));


		// Register AAS in directory (push AAS descriptor to server)
		// - Create an AAS descriptor
		AASDescriptor productAASDescriptor = new AASDescriptor(productID.getURN(), IdentifierType.URI, aasSrvURL+aasURLOnServer);
		// - Push AAS descriptor to server
		client.post(wsURL+"/api/v1/registry", serializer.getJsonString(serializer.serialize(productAASDescriptor)));

		
		// Lookup AAS
		// - Lookup AAS from AAS directory, get AAS descriptor
		String jsonData = client.get(wsURL+"/api/v1/registry/"+productID.getEncodedURN());
		// - Read AAS end point from AAS descriptor
		AASDescriptor aasDescriptor = new AASDescriptor((Map<String, Object>) serializer.deserialize(serializer.getMap(serializer.getObjFromJsonStr(jsonData))));
		System.out.println("Endpoint1:"+jsonData);
		System.out.println("Endpoint2:"+serializer.getObjFromJsonStr(jsonData));
		System.out.println("Endpoint3:"+serializer.getMap(serializer.getObjFromJsonStr(jsonData)));
		System.out.println("Endpoint:"+aasDescriptor.getFirstEndpoint());
		
		
		// Connect to AAS end point
		VABElementProxy connSM = connManager.connectToVABElementByURL(aasDescriptor.getFirstEndpoint());
		System.out.println("XXX");
		Map<String, Object> productAAS = (Map<String, Object>) connSM.readElementValue("");
		// - Read product AAS from server
		System.out.println("ReadBack1:"+connSM);
		System.out.println("ReadBack2:"+productAAS);
		
		System.out.println("ReadBack:"+productAAS.get("idShort"));
	}
}
