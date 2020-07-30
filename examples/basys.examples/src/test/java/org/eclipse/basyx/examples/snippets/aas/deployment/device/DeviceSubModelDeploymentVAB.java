package org.eclipse.basyx.examples.snippets.aas.deployment.device;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.eclipse.basyx.aas.restapi.VABMultiSubmodelProvider;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.restapi.SubModelProvider;
import org.eclipse.basyx.submodel.restapi.SubmodelElementProvider;
import org.eclipse.basyx.vab.coder.json.connector.JSONConnector;
import org.eclipse.basyx.vab.protocol.basyx.connector.BaSyxConnector;
import org.eclipse.basyx.vab.protocol.basyx.server.BaSyxTCPServer;
import org.junit.Test;

/**
 * Code snippet that illustrates the deployment of a AAS sub model to a device, and connects to that sub model using
 * Virtual Automation Bus (VAB) primitives.
 * 
 * The AAS sub model is deployed to a dynamic BaSyxTCPServer that exports the sub model using the BaSyx TCP protocol.
 * 
 * @author kuhn
 *
 */
public class DeviceSubModelDeploymentVAB {

	
	/**
	 * Run code snippet. Connect to AAS sub model on server, access sub model properties using VAB properties. 
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void createExportAndAccessSubModel() throws Exception {

		
		// Create AAS sub model and sub model properties
		// - Create sub model
		SubModel submodel = new SubModel();
		// - Set sub model ID
		submodel.setIdShort("dynamicSM");
		// - Add example properties
		Property prop1 = new Property(7);
		prop1.setIdShort("prop1");
		submodel.addSubModelElement(prop1);

		Property prop2 = new Property("myStr");
		prop2.setIdShort("prop2");
		submodel.addSubModelElement(prop2);

		
		// Export sub model via BaSyx server
		SubModelProvider modelProvider = new SubModelProvider(submodel);
		VABMultiSubmodelProvider aasProvider = new VABMultiSubmodelProvider("dynamicSM", modelProvider);
		BaSyxTCPServer<VABMultiSubmodelProvider> server = new BaSyxTCPServer<VABMultiSubmodelProvider>(aasProvider, 9998);
		// - Start local BaSyx/TCP server
		server.start();
				
		
		// Access BaSyx TCP server using low-level BaSyx connector instead of connection manager
		// - Create BaSyx connector to connect with the sub model
		BaSyxConnector basyxConnector = new BaSyxConnector("localhost", 9998);
		// - Create connection to BaSyx server manager
		JSONConnector toDeviceManager = new JSONConnector(basyxConnector);	
		// - Access sub model property, check value
		int propVal = (int) ((HashMap<String, Object>) toDeviceManager
				.getModelPropertyValue("/aas/submodels/dynamicSM/" + SubmodelElementProvider.PROPERTIES + "/prop1/value")).get("value");
		
		
		// Check value
		assertTrue(propVal == 7);
		
	
		// Stop local BaSyx/TCP server
		server.stop();
	}
}
