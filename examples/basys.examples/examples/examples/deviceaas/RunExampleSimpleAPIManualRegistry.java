package examples.deviceaas;

import org.eclipse.basyx.aas.backend.connector.http.HTTPConnectorProvider;
import org.eclipse.basyx.aas.metamodel.hashmap.VABModelMap;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.AssetAdministrationShell_;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.SubModel_;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Identification;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
import org.junit.Test;

import basys.examples.aasdescriptor.AASDescriptor;
import basys.examples.aasdescriptor.SubmodelDescriptor;
import basys.examples.frontend.client.connmanager.BaSysConnectionManager;
import basys.examples.frontend.client.connmanager.ModelServerProxy;
import basys.examples.frontend.client.proxies.AASRegistryProxy;
import basys.examples.urntools.ModelUrn;
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
	 * Test basic queries
	 */
	@Test @SuppressWarnings("unchecked")
	public void test() throws Exception {

		// Server connections
		// - Connect to AAS server
		//VABElementProxy connSubModel = this.connManager.connectToVABElement("AASServer");
		ModelServerProxy modelServer    = this.connManager.connectToModelServer("AASServer");
		// - Invoke BaSyx service calls via web services
		//WebServiceRawClient client = new WebServiceRawClient();
		// - Directory web service URL
		//String wsURL = "http://localhost:8080/basys.components/Testsuite/Directory/SQL";
		// - AAS repository server URL
		//String aasSrvURL = "http://localhost:8080/basys.examples/Testsuite/components/BaSys/1.0/aasserver";

		
		// Instantiate AAS registry proxy
		AASRegistryProxy registry = new AASRegistryProxy("http://localhost:8080/basys.components/Testsuite/Directory/SQL");
		
		
		// Create device AAS
		// - Product ID (urn:<legalEntity>:<subUnit>:<subModel>:<version>:<revision>:<elementID>#<elementInstance>)
		ModelUrn deviceAASID = new ModelUrn("de.FHG", "devices.es.iese", "aas", "1.0", "3", "x-509", "001");
		// - Create device AAS
		AssetAdministrationShell_ aas = new AssetAdministrationShell_();
		aas.put("idShort", "DeviceIDShort");
		// - Push AAS to model repository
		modelServer.pushToServer(deviceAASID, aas);
		// - AAS URL on server
		//String aasURLOnServer = "/aas/submodels/aasRepository/"+deviceAASID.getEncodedURN();
		// - Transfer device AAS to server
		//connSubModel.createElement(aasURLOnServer, aas);

		
		// The device also brings a sub model structure with an own ID that is being pushed on the server
		ModelUrn deviceStatusSMID = new ModelUrn("de.FHG", "devices.es.iese", "statusSM", "1.0", "3", "x-509", "001");
		// - Create generic sub model 
		SubModel_ statusSM = new SubModel_();
		statusSM.putPath("properties/status", "offline");
		//((Map<String, Object>) statusSM.get("properties")).put("status", "offline");
		// - Push static sub model to model repository
		modelServer.pushToServer(deviceStatusSMID, statusSM);
		// - Sub model URL on server
		//String statusSubmodelURLOnServer = "/aas/submodels/aasRepository/"+deviceStatusSMID.getEncodedURN();
		// - Transfer device sub model to server
		//connSubModel.createElement(statusSubmodelURLOnServer, statusSM);
		
		
		// Register AAS and sub models in directory (push AAS descriptor to server)
		// - Create an AAS descriptor
		//AASDescriptor deviceAASDescriptor = new AASDescriptor(deviceAASID.getURN(), Identification.URI, aasSrvURL+aasURLOnServer);
		AASDescriptor deviceAASDescriptor = new AASDescriptor(deviceAASID.getURN(), Identification.URI, modelServer.getURLToModel(deviceAASID));
		// - Add a sub model descriptor for device
		SubmodelDescriptor deviceStatusSubmodelDescriptor = new SubmodelDescriptor(deviceStatusSMID.getURN(), Identification.URI, modelServer.getURLToModel(deviceStatusSMID));
		deviceAASDescriptor.addSubmodelDescriptor(deviceStatusSubmodelDescriptor);
		// - Push AAS descriptor to server
		//client.post(wsURL+"/api/v1/registry", JSONTools.Instance.serialize(deviceAASDescriptor).toString());
		registry.register(deviceAASDescriptor);
		
		
		// Device updates status to ready
		//connSubModel.updateElementValue(statusSubmodelURLOnServer+"/properties/status", "ready");
		modelServer.updateElementValue(deviceStatusSMID, "/properties/status", "ready");
		// - Read updated status back
		modelServer.readElementValue(deviceStatusSMID, "/properties/status");
		

		// Lookup device AAS
		// - Lookup AAS from AAS directory, get AAS descriptor
		//String jsonData = client.get(wsURL+"/api/v1/registry/"+deviceAASID.getEncodedURN());
		// - Read AAS end point from AAS descriptor
		//AASDescriptor aasDescriptor = new AASDescriptor((Map<String, Object>) JSONTools.Instance.deserialize(new JSONObject(jsonData)));
		AASDescriptor aasDescriptor = registry.lookup(deviceAASID);
		// - Get information about status sub model
		SubmodelDescriptor smDescriptor = aasDescriptor.getSubModelDescriptor(deviceStatusSMID.getURN());
		
		
		// Connect to status sub model end point
		// - FIXME - we need the ability to connect to an absolute URL here
		//Map<String, Object> deviceSM = (Map<String, Object>) connSubModel.readElementValue(statusSubmodelURLOnServer);
		//Map<String, Object> deviceSM = (Map<String, Object>) connSubModel.readElementValue(smDescriptor.getFirstEndpoint());
		VABElementProxy connSubModel = connManager.connectToVABElementByURL(smDescriptor.getFirstEndpoint());
		VABModelMap<Object> deviceSM = (VABModelMap<Object>) connSubModel.readElementValue("/");
		// - Output status information
		//System.out.println("ReadBack:"+((Map<String, Object>) deviceSM.get("properties")).get("status"));
		System.out.println("ReadBack:"+deviceSM.getPath("properties/status"));
	}
}
