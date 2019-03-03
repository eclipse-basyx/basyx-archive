package examples.productaas;

import org.eclipse.basyx.aas.backend.connector.http.HTTPConnectorProvider;
import org.eclipse.basyx.aas.metamodel.hashmap.VABModelMap;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.identifier.IdentifierType;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
import org.junit.Test;

import basys.examples.aasdescriptor.AASDescriptor;
import basys.examples.frontend.client.connmanager.BaSysConnectionManager;
import basys.examples.frontend.client.connmanager.ModelServerProxy;
import basys.examples.frontend.client.proxies.AASRegistryProxy;
import basys.examples.urntools.ModelUrn;
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
	 * Test basic queries
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void test() throws Exception {

		// Server connections
		// - Connect to AAS server
		ModelServerProxy modelServer = this.connManager.connectToModelServer("AASServer");

		
		// Instantiate AAS registry proxy
		AASRegistryProxy registry = new AASRegistryProxy("http://localhost:8080/basys.components/Testsuite/Directory/SQL");
		
		
		// Create product AAS
		// - Product ID (urn:<legalEntity>:<subUnit>:<subModel>:<version>:<revision>:<elementID>#<elementInstance>)
		ModelUrn productID = new ModelUrn("de.FHG", "products.es.iese", "aas", "1.0", "3", "product1", null);
		// - Create map with complex type
		AssetAdministrationShell aas = new AssetAdministrationShell();
		aas.put("idShort", "ProductIDShort");
		// - Push AAS to model repository
		modelServer.pushToServer(productID, aas);


		// Register AAS in directory (push AAS descriptor to server)
		// - Create an AAS descriptor
		AASDescriptor productAASDescriptor = new AASDescriptor(productID.getURN(), IdentifierType.URI, modelServer.getURLToModel(productID));
		// - Push AAS descriptor to server
		registry.register(productAASDescriptor);

		
		// Lookup AAS
		// - Read AAS end point from AAS descriptor
		AASDescriptor aasDescriptor = registry.lookup(productID);
		System.out.println("Endpoint:"+aasDescriptor.getFirstEndpoint());


		// Connect to AAS end point
		VABElementProxy connSubModel = connManager.connectToVABElementByURL(aasDescriptor.getFirstEndpoint());
		VABModelMap<Object> productAAS = (VABModelMap<Object>) connSubModel.readElementValue("/");
		// - Read product AAS from server
		System.out.println("ReadBack:"+productAAS.get("idShort"));
	}
}
