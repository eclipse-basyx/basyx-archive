package org.eclipse.basyx.examples.snippets.aas.deployment.device;

import static org.junit.Assert.assertTrue;

import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.aas.restapi.VABMultiSubmodelProvider;
import org.eclipse.basyx.examples.support.directory.ExampleAASRegistry;
import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IProperty;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.restapi.SubModelProvider;
import org.eclipse.basyx.vab.protocol.basyx.connector.BaSyxConnectorProvider;
import org.eclipse.basyx.vab.protocol.basyx.server.BaSyxTCPServer;
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
		IIdentifier smId = new ModelUrn("urn:de.FHG:devices.es.iese:SampleSM:1.0:3:x-509#003");
		submodel.setIdShort("SampleSM");
		submodel.setIdentification(smId.getIdType(), smId.getId());
		// - Add example properties
		Property prop1 = new Property(7);
		prop1.setIdShort("prop1");
		submodel.addSubModelElement(prop1);

		Property prop2 = new Property("myStr");
		prop2.setIdShort("prop2");
		submodel.addSubModelElement(prop2);

		
		// Export sub model via BaSyx server
		SubModelProvider modelProvider = new SubModelProvider(submodel);
		VABMultiSubmodelProvider aasProvider = new VABMultiSubmodelProvider("SampleSM", modelProvider);
		BaSyxTCPServer<VABMultiSubmodelProvider> server = new BaSyxTCPServer<>(aasProvider, 9998);
		// - Start local BaSyx/TCP server
		server.start();
		
		
		// Create connected aas manager to connect with the dynamic server
		// We pre-register the aas endpoints to the dynamic BaSyx server
		ExampleAASRegistry registry = new ExampleAASRegistry();
		registry.addAASMapping("", ""); // No AAS is provided in this example
		registry.addSubmodelMapping("", smId.getId(), "basyx://localhost:9998/aas/submodels/SampleSM");
		
		// Create manager using the directory stub an the HTTPConnectorProvider
		ConnectedAssetAdministrationShellManager manager = new ConnectedAssetAdministrationShellManager(registry,
				// We connect via BaSyx TCP protocol
				new BaSyxConnectorProvider());
		
		// Create and connect SDK connector
		// - Retrieve sub model
		ISubModel subModel = manager.retrieveSubModel(new ModelUrn(""), smId);
		
		// Retrieve sub model values and compare to expected values
		String submodelId = subModel.getIdShort();
		String prop1Id    = subModel.getProperties().get("prop1").getIdShort();
		int prop1Val = (int) ((IProperty) subModel.getProperties().get("prop1")).get();
		String prop2Id    = subModel.getProperties().get("prop2").getIdShort();
		String prop2Val = (String) ((IProperty) subModel.getProperties().get("prop2")).get();

		
		// Compare received property values to expected values
		assertTrue(submodelId.equals("SampleSM"));
		assertTrue(prop1Id.equals("prop1"));
		assertTrue(prop1Val == 7);
		assertTrue(prop2Id.equals("prop2"));
		assertTrue(prop2Val.equals("myStr"));

	
		// Stop local BaSyx/TCP server
		server.stop();
	}
}
