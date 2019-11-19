package org.eclipse.basyx.examples.snippets.aas.deployment.device;

import static org.junit.Assert.assertTrue;

import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.aas.restapi.AASModelProvider;
import org.eclipse.basyx.aas.restapi.VABMultiSubmodelProvider;
import org.eclipse.basyx.examples.support.directory.ExampleAASRegistry;
import org.eclipse.basyx.vab.protocol.basyx.connector.BaSyxConnectorProvider;
import org.eclipse.basyx.vab.protocol.basyx.server.BaSyxTCPServer;
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
		aas.setIdShort("urn:de.FHG:devices.es.iese:AAS:1.0:3:x-509#003");


		// Export AAS via BaSyx server
		AASModelProvider modelProvider = new AASModelProvider(aas);
		VABMultiSubmodelProvider aasProvider = new VABMultiSubmodelProvider(modelProvider);
		BaSyxTCPServer<VABMultiSubmodelProvider> server = new BaSyxTCPServer<VABMultiSubmodelProvider>(aasProvider, 9998);
		// - Start local BaSyx/TCP server
		server.start();



		// - We pre-register the aas endpoints to the dynamic BaSyx server
		ExampleAASRegistry registry = new ExampleAASRegistry();
		registry.addAASMapping("dynamicAAS", "basyx://localhost:9998/aas");

		// Create connected aas manager to connect with the dynamic server
		ConnectedAssetAdministrationShellManager manager = new ConnectedAssetAdministrationShellManager(registry,
				// We connect via BaSyx TCP protocol
				new BaSyxConnectorProvider());

		
		// Retrieve the AAS with ID "dynamicAAS" from the AAS server with SDK connector
		// - IAssetAdministrationShell is the interface for the local AAS proxy
		IAssetAdministrationShell shell = manager.retrieveAAS(new ModelUrn("dynamicAAS"));
		// - Retrieve AAS values and compare to expected values
		Object propertyId = shell.getIdShort();

		
		// Check value
		assertTrue(propertyId.equals("urn:de.FHG:devices.es.iese:AAS:1.0:3:x-509#003"));
		
	
		// Stop local BaSyx/TCP server
		server.stop();
	}
}
