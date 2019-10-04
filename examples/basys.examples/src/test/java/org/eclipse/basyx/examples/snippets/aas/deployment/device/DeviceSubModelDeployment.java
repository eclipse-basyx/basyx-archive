package org.eclipse.basyx.examples.snippets.aas.deployment.device;

import static org.junit.Assert.assertTrue;

import org.eclipse.basyx.aas.api.metamodel.aas.ISubModel;
import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.property.ISingleProperty;
import org.eclipse.basyx.aas.api.modelurn.ModelUrn;
import org.eclipse.basyx.aas.backend.connected.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.backend.provider.VABMultiSubmodelProvider;
import org.eclipse.basyx.aas.backend.provider.VirtualPathModelProvider;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.SubModel;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.submodelelement.property.SingleProperty;
import org.eclipse.basyx.examples.support.directory.ExampleAASRegistry;
import org.eclipse.basyx.vab.backend.connector.basyx.BaSyxConnectorProvider;
import org.eclipse.basyx.vab.backend.server.basyx.BaSyxTCPServer;
import org.junit.Test;



/**
 * Code snippet that illustrates the deployment of a AAS sub model to a device, and connects to that sub model
 * 
 * The AAS sub model is deployed to a dynamic BaSyxTCPServer that exports the sub model using the BaSyx TCP protocol.
 * 
 * @author kuhn
 *
 */
public class DeviceSubModelDeployment {

	
	/**
	 * Run code snippet. Connect to AAS sub model on server, access sub model properties. 
	 */
	@Test
	public void createExportAndAccessSubModel() throws Exception {

		
		// Create AAS sub model and sub model properties

		// - Create sub model
		SubModel submodel = new SubModel();
		// - Set sub model ID "SampleSM" to full qualified ID urn:de.FHG:devices.es.iese:SampleSM:1.0:3:x-509#003
		submodel.setId("urn:de.FHG:devices.es.iese:SampleSM:1.0:3:x-509#003");
		// - Add example properties
		SingleProperty prop1 = new SingleProperty(7);
		prop1.setId("prop1");
		submodel.addSubModelElement(prop1);

		SingleProperty prop2 = new SingleProperty("myStr");
		prop2.setId("prop2");
		submodel.addSubModelElement(prop2);

		
		// Export sub model via BaSyx server
		VirtualPathModelProvider modelProvider = new VirtualPathModelProvider(submodel);
		VABMultiSubmodelProvider aasProvider = new VABMultiSubmodelProvider("urn:de.FHG:devices.es.iese:SampleSM:1.0:3:x-509#003", modelProvider);
		BaSyxTCPServer<VABMultiSubmodelProvider> server = new BaSyxTCPServer<>(aasProvider, 9998);
		// - Start local BaSyx/TCP server
		server.start();
		
		
		// Create connected aas manager to connect with the dynamic server
		// We pre-register the aas endpoints to the dynamic BaSyx server
		ExampleAASRegistry registry = new ExampleAASRegistry();
		String smRawUrn = "urn:de.FHG:devices.es.iese:SampleSM:1.0:3:x-509#003";
		String aasRawUrn = "urn:de.FHG:devices.es.iese:aas:1.0:3:x-509#003";
		registry.addAASMapping(aasRawUrn, "basyx://localhost:9998/aas/");
		registry.addSubmodelMapping(aasRawUrn, smRawUrn, "basyx://localhost:9998/aas/submodels/" + smRawUrn);
		
		// Create manager using the directory stub an the HTTPConnectorProvider
		ConnectedAssetAdministrationShellManager manager = new ConnectedAssetAdministrationShellManager(registry,
				// We connect via BaSyx TCP protocol
				new BaSyxConnectorProvider());
		
		// Create and connect SDK connector
		// - Retrieve sub model
		ISubModel subModel = manager.retrieveSubModel(new ModelUrn(aasRawUrn), smRawUrn);
		
		// Retrieve sub model values and compare to expected values
		String submodelId = subModel.getId();
		String prop1Id    = subModel.getDataElements().get("prop1").getId();
		int    prop1Val   = (int) ((ISingleProperty) subModel.getDataElements().get("prop1")).get();
		String prop2Id    = subModel.getDataElements().get("prop2").getId();
		String prop2Val   = (String) ((ISingleProperty) subModel.getDataElements().get("prop2")).get();

		
		// Compare received property values to expected values
		assertTrue(submodelId.equals("urn:de.FHG:devices.es.iese:SampleSM:1.0:3:x-509#003"));
		assertTrue(prop1Id.equals("prop1"));
		assertTrue(prop1Val == 7);
		assertTrue(prop2Id.equals("prop2"));
		assertTrue(prop2Val.equals("myStr"));

	
		// Stop local BaSyx/TCP server
		server.stop();
	}
}
