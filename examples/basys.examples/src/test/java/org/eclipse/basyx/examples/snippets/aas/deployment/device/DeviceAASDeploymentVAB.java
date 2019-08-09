package org.eclipse.basyx.examples.snippets.aas.deployment.device;

import static org.junit.Assert.assertTrue;

import org.eclipse.basyx.aas.backend.connector.JSONConnector;
import org.eclipse.basyx.aas.backend.connector.basyx.BaSyxConnector;
import org.eclipse.basyx.aas.backend.provider.VABMultiSubmodelProvider;
import org.eclipse.basyx.aas.backend.provider.VirtualPathModelProvider;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.AssetAdministrationShell;
import org.eclipse.basyx.vab.backend.server.basyx.BaSyxTCPServer;
import org.junit.Test;



/**
 * Code snippet that illustrates the deployment of an AAS to a device, and connects to that AAS
 * 
 * The AAS is deployed to a dynamic BaSyxTCPServer that exports the AAS using the BaSyx TCP protocol.
 * 
 * @author kuhn
 *
 */
public class DeviceAASDeploymentVAB {

	
	/**
	 * Run code snippet. Connect to AAS on server, access AAS properties. 
	 */
	@Test
	public void createExportAndAccessSubModel() throws Exception {


		// Create AAS sub model and sub model properties
		// - Create AAS
		AssetAdministrationShell aas = new AssetAdministrationShell();
		// - Set sub model ID
		aas.setId("urn:de.FHG:devices.es.iese:AAS:1.0:3:x-509#003");


		// Export AAS via BaSyx server
		VirtualPathModelProvider modelProvider = new VirtualPathModelProvider(aas);
		VABMultiSubmodelProvider aasProvider = new VABMultiSubmodelProvider(modelProvider);
		BaSyxTCPServer<VABMultiSubmodelProvider> server = new BaSyxTCPServer<VABMultiSubmodelProvider>(aasProvider, 9998);
		// - Start local BaSyx/TCP server
		server.start();
		
		
		// Access BaSyx TCP server using Virtual Automation Bus low level BaSyxConnector class
		// - Create BaSyx connector to connect with the sub model
		BaSyxConnector basyxConnector = new BaSyxConnector("localhost", 9998);
		// - Create connection to BaSyx server manager
		JSONConnector toDeviceManager = new JSONConnector(basyxConnector);	
		// - Access sub model property, check value
		Object propertyId = toDeviceManager.getModelPropertyValue("aas/idShort");

		
		// Check value
		assertTrue(propertyId.equals("urn:de.FHG:devices.es.iese:AAS:1.0:3:x-509#003"));
		
	
		// Stop local BaSyx/TCP server
		server.stop();
	}
}
