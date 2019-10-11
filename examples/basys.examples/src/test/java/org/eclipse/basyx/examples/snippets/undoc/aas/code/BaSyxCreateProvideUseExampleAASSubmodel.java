package org.eclipse.basyx.examples.snippets.undoc.aas.code;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.eclipse.basyx.aas.backend.provider.SubModelProvider;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.SubModel;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.submodelelement.property.SingleProperty;
import org.eclipse.basyx.vab.backend.connector.JSONConnector;
import org.eclipse.basyx.vab.backend.connector.basyx.BaSyxConnector;
import org.eclipse.basyx.vab.backend.server.basyx.BaSyxTCPServer;
import org.junit.Test;


/**
 * Illustrate manual creation and providing of AAS sub model
 * 
 * @author kuhn
 *
 */
public class BaSyxCreateProvideUseExampleAASSubmodel {

	
	/**
	 * Create, export, and access an example AAS sub model
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void createExportAndAccessSubModel() throws Exception {
				
		// Create sub model and add properties
		SubModel statusSM = new SubModel();
		//   - Property status: indicate device status
		SingleProperty statusProp = new SingleProperty("offline");
		statusProp.setId("status");
		statusSM.addSubModelElement(statusProp);
		//   - Property statistics: export invocation statistics for every service
		//     - invocations: indicate total service invocations. Properties are not persisted in this example,
		//                    therefore we start counting always at 0.
		SingleProperty invocationsProp = new SingleProperty(0);
		invocationsProp.setId("invocations");
		statusSM.addSubModelElement(invocationsProp);

		
		// Provide sub model via BaSyx server
		BaSyxTCPServer<SubModelProvider> server = new BaSyxTCPServer<>(new SubModelProvider(statusSM), 9998);
		// - Start local BaSyx/TCP server
		server.start();

		
		// Access BaSyx TCP server
		// - Create BaSyx connector to connect with the sub model
		BaSyxConnector basyxConnector = new BaSyxConnector("localhost", 9998);
		// - Create connection to device manager
		JSONConnector toDeviceManager = new JSONConnector(basyxConnector);	
		// - Access sub model property, check value
		Map<String, Object> property = (Map<String, Object>) toDeviceManager.getModelPropertyValue(SubModel.PROPERTIES + "/status");
		assertEquals("offline", property.get("value"));
		
		
		// Stop local BaSyx/TCP server
		server.stop();
	}
}
