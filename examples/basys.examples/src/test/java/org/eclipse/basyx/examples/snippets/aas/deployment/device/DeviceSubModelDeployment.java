package org.eclipse.basyx.examples.snippets.aas.deployment.device;

import static org.junit.Assert.assertTrue;

import org.eclipse.basyx.aas.api.resources.ISingleProperty;
import org.eclipse.basyx.aas.api.resources.ISubModel;
import org.eclipse.basyx.aas.backend.connected.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.backend.connector.basyx.BaSyxConnectorProvider;
import org.eclipse.basyx.aas.backend.provider.VABMultiSubmodelProvider;
import org.eclipse.basyx.aas.backend.provider.VirtualPathModelProvider;
import org.eclipse.basyx.aas.metamodel.factory.MetaModelElementFactory;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.SubModel;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property.Property;
import org.eclipse.basyx.examples.support.directory.ExamplesPreconfiguredDirectory;
import org.eclipse.basyx.vab.backend.server.basyx.BaSyxTCPServer;
import org.eclipse.basyx.vab.core.VABConnectionManager;
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
		// - The MetaModelElementFactory factory class creates sub model properties and ensures 
		//   presence of all meta data
		MetaModelElementFactory fac = new MetaModelElementFactory();
		// - Create sub model
		SubModel submodel = new SubModel();
		// - Set sub model ID "SampleSM" to full qualified ID urn:de.FHG:devices.es.iese:SampleSM:1.0:3:x-509#003
		submodel.setId("urn:de.FHG:devices.es.iese:SampleSM:1.0:3:x-509#003");
		// - Add example properties
		submodel.getProperties().put(fac.create(new Property(),       7, "prop1"));
		submodel.getProperties().put(fac.create(new Property(), "myStr", "prop2"));

		
		// Export sub model via BaSyx server
		VirtualPathModelProvider modelProvider = new VirtualPathModelProvider(submodel);
		VABMultiSubmodelProvider aasProvider = new VABMultiSubmodelProvider("urn:de.FHG:devices.es.iese:SampleSM:1.0:3:x-509#003", modelProvider);
		BaSyxTCPServer<VABMultiSubmodelProvider> server = new BaSyxTCPServer<VABMultiSubmodelProvider>(aasProvider, 9998);
		// - Start local BaSyx/TCP server
		server.start();
		
		
		// Create connection manager to connect with the dynamic server
		// - We pre-register the connection endpoint to the dynamic BaSyx server
		VABConnectionManager connManager = new VABConnectionManager(
				// Add example specific mappings
				new ExamplesPreconfiguredDirectory()
				    // - SDK connectors encapsulate relative path Asset Administration Shell
				    // - The dynamic deployed sub model receives a unique URN, as it needs to be locatable
				    // - In most cases, the discovery server will provide the endpoint of the sub model
					.addMapping("urn:de.FHG:devices.es.iese:SampleSM:1.0:3:x-509#003",    "basyx://localhost:9998"),
				// We connect via BaSyx TCP protocol
				new BaSyxConnectorProvider());

		
		// Create manager using the directory stub an the HTTPConnectorProvider
		ConnectedAssetAdministrationShellManager manager = new ConnectedAssetAdministrationShellManager(connManager);
		
		
		// Create and connect SDK connector
		// - Retrieve sub model
		ISubModel subModel = manager.retrieveSM("urn:de.FHG:devices.es.iese:SampleSM:1.0:3:x-509#003");
		
		// Retrieve sub model values and compare to expected values
		String submodelId = subModel.getId();
		String prop1Id    = subModel.getProperties().get("prop1").getId();
		int    prop1Val   = (int) ((ISingleProperty) subModel.getProperties().get("prop1")).get();
		String prop2Id    = subModel.getProperties().get("prop2").getId();
		String prop2Val   = (String) ((ISingleProperty) subModel.getProperties().get("prop2")).get();

		
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
