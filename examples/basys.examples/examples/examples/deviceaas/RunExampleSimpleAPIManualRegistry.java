package examples.deviceaas;

import java.util.Map;

import org.eclipse.basyx.aas.backend.connector.http.HTTPConnectorProvider;
import org.eclipse.basyx.aas.metamodel.hashmap.VABModelMap;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.SubModel;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.identifier.IdentifierType;
import org.eclipse.basyx.regression.support.server.AASHTTPServerResource;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
import org.junit.ClassRule;
import org.junit.Test;

import basys.examples.aasdescriptor.AASDescriptor;
import basys.examples.aasdescriptor.SubmodelDescriptor;
import basys.examples.frontend.client.connmanager.BaSysConnectionManager;
import basys.examples.frontend.client.connmanager.ModelServerProxy;
import basys.examples.frontend.client.proxies.AASRegistryProxy;
import basys.examples.urntools.ModelUrn;
import examples.contexts.BaSyxExamplesContext_1MemoryAASServer_1SQLDirectory;
import examples.directory.ExamplesDirectory;



/**
 * Run example for device AAS
 * 
 * @author kuhn
 *
 */
public class RunExampleSimpleAPIManualRegistry {

	
	/**
	 * BaSys connection manager backend
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
	@Test @SuppressWarnings("unchecked")
	public void test() throws Exception {

		// Server connections
		// - Connect to AAS server
		ModelServerProxy modelServer    = this.connManager.connectToModelServer("AASServer");

		
		// Instantiate AAS registry proxy that connects to registry
		AASRegistryProxy registry = new AASRegistryProxy("http://localhost:8080/basys.examples/Components/Directory/SQL");

		
		// Create device AAS
		// - Product ID (urn:<legalEntity>:<subUnit>:<subModel>:<version>:<revision>:<elementID>#<elementInstance>)
		ModelUrn deviceAASID = new ModelUrn("de.FHG", "devices.es.iese", "aas", "1.0", "3", "x-509", "001");
		// - Create device AAS
		AssetAdministrationShell aas = new AssetAdministrationShell();
		aas.put("idShort", "DeviceIDShort");
		// - Push AAS to model repository
		modelServer.pushToServer(deviceAASID, aas);

		
		// The device also brings a sub model structure with an own ID that is being pushed on the server
		ModelUrn deviceStatusSMID = new ModelUrn("de.FHG", "devices.es.iese", "statusSM", "1.0", "3", "x-509", "001");
		// - Create generic sub model 
		SubModel statusSM = new SubModel();
		statusSM.putPath("properties/status", "offline");
		// - Push static sub model to model repository
		modelServer.pushToServer(deviceStatusSMID, statusSM);
		
		
		// Delete AAS registration for a fresh start - ignore if URL was not found. In this case, there was no previous registration and the registry was clean
		registry.delete(deviceAASID);

		
		// Register AAS and sub models in directory (push AAS descriptor to server)
		// - Create an AAS descriptor
		AASDescriptor deviceAASDescriptor = new AASDescriptor(deviceAASID.getURN(), IdentifierType.URI, modelServer.getURLToModel(deviceAASID));
		// - Add a sub model descriptor for device
		SubmodelDescriptor deviceStatusSubmodelDescriptor = new SubmodelDescriptor(deviceStatusSMID.getURN(), IdentifierType.URI, modelServer.getURLToModel(deviceStatusSMID));
		deviceAASDescriptor.addSubmodelDescriptor(deviceStatusSubmodelDescriptor);
		// - Push AAS descriptor to server
		registry.register(deviceAASDescriptor);
		
		
		// Device updates status to ready
		//connSubModel.updateElementValue(statusSubmodelURLOnServer+"/properties/status", "ready");
		modelServer.updateElementValue(deviceStatusSMID, "/properties/status", "ready");
		// - Read updated status back
		modelServer.readElementValue(deviceStatusSMID, "/properties/status");
		

		// Lookup device AAS
		AASDescriptor aasDescriptor = registry.lookup(deviceAASID);
		// - Get information about status sub model
		SubmodelDescriptor smDescriptor = aasDescriptor.getSubModelDescriptor(deviceStatusSMID.getURN());
		
		
		// Connect to status sub model end point
		VABElementProxy connSubModel = connManager.connectToVABElementByURL(smDescriptor.getFirstEndpoint());
		Map<String, Object> deviceSM = (Map<String, Object>) connSubModel.readElementValue("/");
		// - Output status information
		System.out.println("ReadBack:"+((Map<String, Object>) deviceSM.get("properties")).get("status"));
	}
}
