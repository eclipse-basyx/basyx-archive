package org.eclipse.basyx.examples.snippets.aas.deployment.device;

import static org.junit.Assert.assertEquals;

import org.eclipse.basyx.aas.api.metamodel.aas.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.metamodel.aas.identifier.IIdentifier;
import org.eclipse.basyx.aas.api.modelurn.ModelUrn;
import org.eclipse.basyx.aas.backend.connected.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.backend.provider.VABMultiSubmodelProvider;
import org.eclipse.basyx.aas.backend.provider.VirtualPathModelProvider;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.AssetAdministrationShell;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.identifier.IdentifierType;
import org.eclipse.basyx.examples.support.directory.ExampleAASRegistry;
import org.eclipse.basyx.vab.backend.connector.basyx.BaSyxConnectorProvider;
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
public class DeviceAASDeployment {

	
	/**
	 * Run code snippet. Connect to AAS on server, access AAS properties. 
	 */
	@Test
	public void createExportAndAccessSubModel() throws Exception {

		// Create AAS sub model and sub model properties
		// - Create AAS
		AssetAdministrationShell aas = new AssetAdministrationShell();
		// - Set AAS ID
		String rawURN = "urn:de.FHG:devices.es.iese:AAS:1.0:3:x-509#003";
		aas.setIdentification(IdentifierType.URI, rawURN);


		// Export AAS via BaSyx server
		VirtualPathModelProvider modelProvider = new VirtualPathModelProvider(aas);
		VABMultiSubmodelProvider aasProvider = new VABMultiSubmodelProvider(modelProvider);
		BaSyxTCPServer<VABMultiSubmodelProvider> server = new BaSyxTCPServer<>(aasProvider, 9998);
		// - Start local BaSyx/TCP server
		server.start();



		// - We pre-register the aas endpoints to the dynamic BaSyx server
		ExampleAASRegistry registry = new ExampleAASRegistry();
		registry.addAASMapping(rawURN, "basyx://localhost:9998/aas");

		// Create connected aas manager to connect with the dynamic server
		ConnectedAssetAdministrationShellManager manager = new ConnectedAssetAdministrationShellManager(registry,
				// We connect via BaSyx TCP protocol
				new BaSyxConnectorProvider());

		
		// Retrieve the AAS with ID "dynamicAAS" from the AAS server with SDK connector
		// - IAssetAdministrationShell is the interface for the local AAS proxy
		IAssetAdministrationShell shell = manager.retrieveAAS(new ModelUrn(rawURN));
		// - Retrieve AAS values and compare to expected values
		IIdentifier propertyId = shell.getIdentification();

		
		// Check value
		assertEquals(rawURN, propertyId.getId());
		
	
		// Stop local BaSyx/TCP server
		server.stop();
	}
}
