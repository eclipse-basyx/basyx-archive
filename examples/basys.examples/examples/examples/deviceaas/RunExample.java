package examples.deviceaas;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.aas.backend.connector.http.HTTPConnectorProvider;
import org.eclipse.basyx.aas.backend.http.tools.JSONTools;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.AssetAdministrationShell_;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.SubModel_;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Identification;
import org.eclipse.basyx.tools.webserviceclient.WebServiceRawClient;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
import org.junit.Test;

import basys.examples.aasdescriptor.AASDescriptor;
import basys.examples.aasdescriptor.SubmodelDescriptor;
import basys.examples.urntools.ModelUrn;
import org.json.JSONObject;
import examples.directory.ExamplesDirectory;



/**
 * Run example for device AAS
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

		
		
		// Create device AAS
		// - Product ID (urn:<legalEntity>:<subUnit>:<subModel>:<version>:<revision>:<elementID>#<elementInstance>)
		ModelUrn deviceAASID = new ModelUrn("de.FHG", "devices.es.iese", "aas", "1.0", "3", "x-509", "001");
		// - Create device AAS
		AssetAdministrationShell_ aas = new AssetAdministrationShell_();
		aas.put("idShort", "DeviceIDShort");
		// - AAS URL on server
		String aasURLOnServer = "/aas/submodels/aasRepository/"+deviceAASID.getEncodedURN();
		// - Transfer device AAS to server
		connSubModel.createElement(aasURLOnServer, aas);

		
		// The device also brings a sub model structure with an own ID that is being pushed on the server
		ModelUrn deviceStatusSMID = new ModelUrn("de.FHG", "devices.es.iese", "statusSM", "1.0", "3", "x-509", "001");
		// - Create generic sub model 
		SubModel_ statusSM = new SubModel_();
		((Map<String, Object>) statusSM.get("properties")).put("status", "offline");
		// - Sub model URL on server
		String statusSubmodelURLOnServer = "/aas/submodels/aasRepository/"+deviceStatusSMID.getEncodedURN();
		// - Transfer device sub model to server
		connSubModel.createElement(statusSubmodelURLOnServer, statusSM);
		
		
		// The device also brings a sub model structure with an own ID that is being pushed on the server
		ModelUrn deviceControllerSMID = new ModelUrn("jp.orin", "devices.orin", "controllerSM", "1.0", "3", "x-509", "001");
		// - Create generic sub model 
		SubModel_ controllerSM = new SubModel_();
		//   - Create sub model contents
		Map<String, Object> listOfControllers = new HashMap<>();
		((Map<String, Object>) controllerSM.get("properties")).put("controllers", listOfControllers);
		// - Sub model URL on server
		String controllerSubmodelURLOnServer = "/aas/submodels/aasRepository/"+deviceControllerSMID.getEncodedURN();
		// - Transfer device sub model to server
		connSubModel.createElement(controllerSubmodelURLOnServer, controllerSM);

		
		
		// Register AAS and sub models in directory (push AAS descriptor to server)
		// - Create an AAS descriptor
		AASDescriptor deviceAASDescriptor = new AASDescriptor(deviceAASID.getURN(), Identification.URI, aasSrvURL+aasURLOnServer);
		// - Add a sub model descriptor for device
		SubmodelDescriptor deviceStatusSubmodelDescriptor = new SubmodelDescriptor(deviceStatusSMID.getURN(), Identification.URI, aasSrvURL+statusSubmodelURLOnServer);
		deviceAASDescriptor.addSubmodelDescriptor(deviceStatusSubmodelDescriptor);
		SubmodelDescriptor deviceControllerSubmodelDescriptor = new SubmodelDescriptor(deviceControllerSMID.getURN(), Identification.URI, aasSrvURL+controllerSubmodelURLOnServer);
		deviceAASDescriptor.addSubmodelDescriptor(deviceControllerSubmodelDescriptor);
		// - Push AAS descriptor to server
		client.post(wsURL+"/api/v1/registry", JSONTools.Instance.serialize(deviceAASDescriptor).toString());

		
		// Device updates status to ready
		connSubModel.updateElementValue(statusSubmodelURLOnServer+"/properties/status", "ready");
		

		// Lookup device AAS
		// - Lookup AAS from AAS directory, get AAS descriptor
		String jsonData = client.get(wsURL+"/api/v1/registry/"+deviceAASID.getEncodedURN());
		// - Read AAS end point from AAS descriptor
		AASDescriptor aasDescriptor = new AASDescriptor((Map<String, Object>) JSONTools.Instance.deserialize(new JSONObject(jsonData)));
		// - Get information about status sub model
		SubmodelDescriptor smDescriptor = aasDescriptor.getSubModelDescriptor(deviceStatusSMID.getURN());
		
		
		// Connect to status sub model end point
		VABElementProxy connSM = connManager.connectToVABElementByURL(smDescriptor.getFirstEndpoint());
		Map<String, Object> deviceSM = (Map<String, Object>) connSM.readElementValue("/");
		// - Output status information
		System.out.println("ReadBack:"+((Map<String, Object>) deviceSM.get("properties")).get("status"));
	}
}
