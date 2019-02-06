package examples.productaas;

import java.util.Map;

import org.eclipse.basyx.aas.backend.connector.http.HTTPConnectorProvider;
import org.eclipse.basyx.aas.backend.http.tools.JSONTools;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.AssetAdministrationShell_;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Identification;
import org.eclipse.basyx.tools.webserviceclient.WebServiceRawClient;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
import org.json.JSONObject;
import org.junit.Test;

import basys.examples.aasdescriptor.AASDescriptor;
import basys.examples.urntools.ModelUrn;
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
	 * Test basic queries
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void test() throws Exception {

		// Server connections
		// - Connect to AAS server
		VABElementProxy connSubModel = this.connManager.connectToVABElement("AASServer");
		// - Invoke BaSyx service calls via web services
		WebServiceRawClient client = new WebServiceRawClient();
		// - Directory web service URL
		String wsURL = "http://localhost:8080/basys.components/Testsuite/Directory/SQL";
		// - AAS repository server URL
		String aasSrvURL = "http://localhost:8080/basys.examples/Testsuite/components/BaSys/1.0/aasserver";

		
		
		// Create product AAS
		// - Product ID (urn:<legalEntity>:<subUnit>:<subModel>:<version>:<revision>:<elementID>#<elementInstance>)
		ModelUrn productID = new ModelUrn("de.FHG", "products.es.iese", "aas", "1.0", "3", "product1", null);
		// - Create map with complex type
		AssetAdministrationShell_ aas = new AssetAdministrationShell_();
		aas.put("idShort", "ProductIDShort");
		// - AAS URL on server
		String aasURLOnServer = "/aas/submodels/aasRepository/"+productID.getEncodedURN();
		// - Create AAS structure on server
		connSubModel.createElement(aasURLOnServer, aas);


		// Register AAS in directory (push AAS descriptor to server)
		// - Create an AAS descriptor
		AASDescriptor productAASDescriptor = new AASDescriptor(productID.getURN(), Identification.URI, aasSrvURL+aasURLOnServer);
		// - Push AAS descriptor to server
		client.post(wsURL+"/api/v1/registry", JSONTools.Instance.serialize(productAASDescriptor).toString());

		
		// Lookup AAS
		// - Lookup AAS from AAS directory, get AAS descriptor
		String jsonData = client.get(wsURL+"/api/v1/registry/"+productID.getEncodedURN());
		// - Read AAS end point from AAS descriptor
		AASDescriptor aasDescriptor = new AASDescriptor((Map<String, Object>) JSONTools.Instance.deserialize(new JSONObject(jsonData)));
		System.out.println("Endpoint:"+aasDescriptor.getFirstEndpoint());
		
		
		// Connect to AAS end point
		VABElementProxy connSM = connManager.connectToVABElementByURL(aasDescriptor.getFirstEndpoint());
		Map<String, Object> productAAS = (Map<String, Object>) connSM.readElementValue("");
		// - Read product AAS from server
		System.out.println("ReadBack:"+productAAS.get("idShort"));
	}
}
