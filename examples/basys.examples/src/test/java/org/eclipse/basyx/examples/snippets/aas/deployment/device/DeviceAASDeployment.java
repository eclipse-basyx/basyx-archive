package org.eclipse.basyx.examples.snippets.aas.deployment.device;

import static org.junit.Assert.assertTrue;

import org.eclipse.basyx.aas.api.resources.IAssetAdministrationShell;
import org.eclipse.basyx.aas.backend.connected.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.backend.connector.basyx.BaSyxConnectorProvider;
import org.eclipse.basyx.aas.backend.provider.VABMultiSubmodelProvider;
import org.eclipse.basyx.aas.backend.provider.VirtualPathModelProvider;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.AssetAdministrationShell;
import org.eclipse.basyx.examples.support.directory.ExamplesPreconfiguredDirectory;
import org.eclipse.basyx.vab.backend.server.basyx.BaSyxTCPServer;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.junit.Test;

/**
 * Code snippet that illustrates the deployment of an AAS to a device, and connects to that AAS
 * 
 * The AAS is deployed to a dynamic BaSyxTCPServer that exports the AAS using the BaSyx TCP protocol.
 * 
 * @author kuhn
 *
 */
public class DeviceAASDeployment {

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


		// Create connection manager to connect with the dynamic server
		// - We pre-register the connection endpoint to the dynamic BaSyx server
		VABConnectionManager connManager = new VABConnectionManager(
				// Add example specific mappings
				new ExamplesPreconfiguredDirectory()
				    // - SDK connectors encapsulate relative path Asset Administration Shell
					.addMapping("dynamicAAS",    "basyx://localhost:9998"),
				// We connect via BaSyx TCP protocol
				new BaSyxConnectorProvider());


		// Create manager using the directory stub an the HTTPConnectorProvider
		// - Connect to VAB object by ID. The connection manager looks up this ID in
		//   its directory
		ConnectedAssetAdministrationShellManager manager = new ConnectedAssetAdministrationShellManager(connManager);

		
		// Retrieve the AAS with ID "dynamicAAS" from the AAS server with SDK connector
		// - IAssetAdministrationShell is the interface for the local AAS proxy
		IAssetAdministrationShell shell = manager.retrieveAAS("dynamicAAS");
		// - Retrieve AAS values and compare to expected values
		Object propertyId = shell.getId();

		
		// Check value
		assertTrue(propertyId.equals("urn:de.FHG:devices.es.iese:AAS:1.0:3:x-509#003"));
		
	
		// Stop local BaSyx/TCP server
		server.stop();
	}
}
