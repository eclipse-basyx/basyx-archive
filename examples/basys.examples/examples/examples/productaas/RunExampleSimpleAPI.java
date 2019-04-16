package examples.productaas;

import java.net.URLEncoder;
import java.util.Map;

import org.eclipse.basyx.aas.backend.connector.http.HTTPConnectorProvider;
import org.eclipse.basyx.aas.backend.http.tools.GSONTools;
import org.eclipse.basyx.aas.backend.http.tools.factory.DefaultTypeFactory;
import org.eclipse.basyx.aas.metamodel.hashmap.VABModelMap;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.identifier.IdentifierType;
import org.eclipse.basyx.regression.support.server.AASHTTPServerResource;
import org.eclipse.basyx.tools.webserviceclient.WebServiceRawClient;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
import org.junit.ClassRule;
import org.junit.Test;

import basys.examples.aasdescriptor.AASDescriptor;
import basys.examples.aasdescriptor.ModelUrn;
import basys.examples.frontend.client.connmanager.BaSysConnectionManager;
import basys.examples.frontend.client.connmanager.ModelServerProxy;
import basys.examples.frontend.client.proxies.AASRegistryProxy;
import examples.contexts.BaSyxExamplesContext_1MemoryAASServer_1SQLDirectory;
import examples.directory.ExamplesDirectory;



/**
 * Run example for product AAS
 * 
 * @author kuhn
 *
 */
public class RunExampleSimpleAPI {

	
	/**
	 * VAB connection manager backend
	 */
	protected BaSysConnectionManager connManager = new BaSysConnectionManager(new ExamplesDirectory(), new HTTPConnectorProvider());

	
	/** 
	 * Makes sure Tomcat Server with basic BaSys topology is started
	 */
	@ClassRule
	public static AASHTTPServerResource res = AASHTTPServerResource.getTestResource(new BaSyxExamplesContext_1MemoryAASServer_1SQLDirectory());

	
	
	/**
	 * Test basic queries
	 */
	@SuppressWarnings("unchecked") @Test
	public void test() throws Exception {

		// Server connections
		// - Connect to AAS server
		ModelServerProxy modelServer = this.connManager.connectToModelServer("AASServer");

		// Instantiate AAS registry proxy
		AASRegistryProxy registry = new AASRegistryProxy("http://localhost:8080/basys.examples/Components/Directory/SQL");
		
		// Create product AAS
		// - Product ID (urn:<legalEntity>:<subUnit>:<subModel>:<version>:<revision>:<elementID>#<elementInstance>)
		ModelUrn productID = new ModelUrn("de.FHG", "products.es.iese", "aas", "1.0", "3", "product1", null);
		// - Create map with complex type
		AssetAdministrationShell aas = new AssetAdministrationShell();
		aas.put("idShort", "ProductIDShort");
		// - Push AAS to model repository
		modelServer.pushToServer(productID, aas);

		
		// Delete AAS registration for a fresh start - ignore if URL was not found. In this case, there was no previous registration and the registry was clean
		registry.delete(productID);


		// Register AAS in directory (push AAS descriptor to server)
		// - Create an AAS descriptor
		AASDescriptor productAASDescriptor = new AASDescriptor(productID.getURN(), IdentifierType.URI, modelServer.getURLToModel(productID));
		// - Push AAS descriptor to server
		registry.register(productAASDescriptor);

		
		// Lookup AAS
		// - Read AAS end point from AAS descriptor
		AASDescriptor aasDescriptor = registry.lookup(productID);
		// Connect to AAS end point
		VABElementProxy connSubModel2 = connManager.connectToVABElementByURL(aasDescriptor.getFirstEndpoint());
		// - Get AAS
		Map<String, Object> productAAS = (Map<String, Object>) connSubModel2.readElementValue("/");
		// - Read product AAS from server
		System.out.println("ReadBack:"+productAAS.get("idShort"));
	}
}
