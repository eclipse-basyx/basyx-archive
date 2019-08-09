package org.eclipse.basyx.examples.snippets.aas.deployment.device;

import static org.junit.Assert.assertTrue;

import org.eclipse.basyx.aas.backend.connector.JSONConnector;
import org.eclipse.basyx.aas.backend.connector.basyx.BaSyxConnector;
import org.eclipse.basyx.aas.backend.provider.VABMultiSubmodelProvider;
import org.eclipse.basyx.aas.backend.provider.VirtualPathModelProvider;
import org.eclipse.basyx.aas.metamodel.factory.MetaModelElementFactory;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.SubModel;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property.Property;
import org.eclipse.basyx.vab.backend.server.basyx.BaSyxTCPServer;
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
	@Test
	public void createExportAndAccessSubModel() throws Exception {

		
		// Create AAS sub model and sub model properties
		// - The MetaModelElementFactory factory class creates sub model properties and ensures 
		//   presence of all meta data
		MetaModelElementFactory fac = new MetaModelElementFactory();
		// - Create sub model
		SubModel submodel = new SubModel();
		// - Set sub model ID
		submodel.setId("dynamicSM");
		// - Add example properties
		submodel.getProperties().put(fac.create(new Property(),       7, "prop1"));
		submodel.getProperties().put(fac.create(new Property(), "myStr", "prop2"));

		
		// Export sub model via BaSyx server
		VirtualPathModelProvider modelProvider = new VirtualPathModelProvider(submodel);
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
		int propVal = (int) toDeviceManager.getModelPropertyValue("/aas/submodels/dynamicSM/dataElements/prop1/value");
		
		
		// Check value
		assertTrue(propVal == 7);
		
	
		// Stop local BaSyx/TCP server
		server.stop();
	}
}
