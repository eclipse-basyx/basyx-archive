package org.eclipse.basyx.examples.snippets.undoc.aas.code;

import static org.junit.Assert.assertTrue;

import org.eclipse.basyx.aas.backend.connector.JSONConnector;
import org.eclipse.basyx.aas.backend.connector.basyx.BaSyxConnector;
import org.eclipse.basyx.aas.backend.provider.VirtualPathModelProvider;
import org.eclipse.basyx.aas.metamodel.hashmap.VABModelMap;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.SubModel;
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
	@Test
	public void createExportAndAccessSubModel() throws Exception {
				
		// Create sub model and add properties
		VABModelMap<Object> statusSM = new SubModel()
		//   - Property status: indicate device status
				.putPath("properties/status", "offline")
		//   - Property statistics: export invocation statistics for every service
		//     - invocations: indicate total service invocations. Properties are not persisted in this example,
		//                    therefore we start counting always at 0.
				.putPath("properties/statistics/default/invocations", 0);

		
		// Provide sub model via BaSyx server
		BaSyxTCPServer<VirtualPathModelProvider> server = new BaSyxTCPServer<>(new VirtualPathModelProvider(statusSM), 9998);
		// - Start local BaSyx/TCP server
		server.start();

		
		// Access BaSyx TCP server
		// - Create BaSyx connector to connect with the sub model
		BaSyxConnector basyxConnector = new BaSyxConnector("localhost", 9998);
		// - Create connection to device manager
		JSONConnector toDeviceManager = new JSONConnector(basyxConnector);	
		// - Access sub model property, check value
		assertTrue(toDeviceManager.getModelPropertyValue("properties/status").equals("offline"));
		
		
		// Stop local BaSyx/TCP server
		server.stop();
	}
}
