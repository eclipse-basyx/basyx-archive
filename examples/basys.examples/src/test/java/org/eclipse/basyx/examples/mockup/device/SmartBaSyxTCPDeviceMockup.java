package org.eclipse.basyx.examples.mockup.device;

import java.util.Map;

import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.components.device.BaseSmartDevice;
import org.eclipse.basyx.examples.support.directory.ExamplesPreconfiguredDirectory;
import org.eclipse.basyx.models.controlcomponent.ExecutionState;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.restapi.SubmodelElementProvider;
import org.eclipse.basyx.vab.manager.VABConnectionManager;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
import org.eclipse.basyx.vab.modelprovider.map.VABMapProvider;
import org.eclipse.basyx.vab.protocol.basyx.server.BaSyxTCPServer;
import org.eclipse.basyx.vab.protocol.http.connector.HTTPConnectorProvider;

/**
 * This class implements a mockup of a smart manufacturing device
 * 
 * The device pushes its AAS to an external asset administration shell server
 * - The sub model "statusSM" is pushed to the external asset administration shell server as well
 * - The sub model "controllerSM" is provided by an BaSyx/TCP server of the smart device
 * 
 * @author kuhn
 *
 */
public class SmartBaSyxTCPDeviceMockup extends BaseSmartDevice {

	
	/**
	 * Server port
	 */
	protected int serverPort = -1;
	
	
	/**
	 * BaSyx/TCP Server that exports the control component
	 */
	protected BaSyxTCPServer<VABMapProvider> server = null;
	
	
	/**
	 * AAS server connection
	 */
	protected VABElementProxy aasServerConnection = null;

	
	

	
	/**
	 * Constructor
	 */
	public SmartBaSyxTCPDeviceMockup(int port) {
		// Invoke base constructor
		super();
		
		// Store server port
		serverPort = port;

		// Register URNs of managed VAB objects
		addShortcut("AAS",        new ModelUrn("urn:de.FHG:devices.es.iese:aas:1.0:3:x-509#001"));
		addShortcut("Status",     new ModelUrn("urn:de.FHG:devices.es.iese:statusSM:1.0:3:x-509#001"));

		// Configure BaSyx service - registry and connection manager
		setRegistry(new AASRegistryProxy("http://localhost:8080/basys.examples/Components/Directory/SQL"));
		setConnectionManager(new VABConnectionManager(new ExamplesPreconfiguredDirectory(), new HTTPConnectorProvider()));
	}

	
	/**
	 * Indicate a service invocation
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void onServiceInvocation() {
		// Base implementation
		super.onServiceInvocation();
		// Implement the device invocation counter - read and increment invocation counter
		Map<String, Object> property = (Map<String, Object>) aasServerConnection
				.getModelPropertyValue(
						"/aas/submodels/Status/" + SubmodelElementProvider.PROPERTIES + "/invocations");
		int invocations = (int) property.get("value");
		aasServerConnection.setModelPropertyValue("/aas/submodels/Status/" + SubmodelElementProvider.PROPERTIES + "/invocations/value", ++invocations);
	}

	
	/**
	 * Smart device control component indicates an execution state change
	 */
	@Override
	public void onChangedExecutionState(ExecutionState newExecutionState) {
		// Invoke base implementation
		super.onChangedExecutionState(newExecutionState);
		
		// Update property "properties/status" in external AAS
		aasServerConnection.setModelPropertyValue("/aas/submodels/Status/" + SubmodelElementProvider.PROPERTIES + "/status/value",
				newExecutionState.getValue());
	}




	/**
	 * Start smart device
	 */
	@Override
	public void start() {
		// Invoke base implementation
		super.start();
		
		
		// Connect to AAS server
		aasServerConnection = this.getConnectionManager().connectToVABElement("AASServer");

		
		// Create device AAS
		// - Create device AAS
		AssetAdministrationShell aas = new AssetAdministrationShell().putPath("idShort", "DeviceIDShort");
		// - Transfer device AAS to server
		aasServerConnection.createValue("/aas", aas);

		
		// The device also brings a sub model structure with an own ID that is being pushed on the server
		// - Create generic sub model and add properties
		SubModel statusSM = new SubModel();
		statusSM.setIdShort("Status");
		//   - Property status: indicate device status
		Property statusProp = new Property("offline");
		statusProp.setIdShort("status");
		statusSM.addSubModelElement(statusProp);
		//   - Property statistics: export invocation statistics for every service
		//     - invocations: indicate total service invocations. Properties are not persisted in this example,
		//                    therefore we start counting always at 0.
		Property invocationsProp = new Property(0);
		invocationsProp.setIdShort("invocations");
		statusSM.addSubModelElement(invocationsProp);
		// - Transfer device sub model to server
		aasServerConnection.createValue("/aas/submodels", statusSM);

		
		// Register control component as local sub model
		// - This sub model will stay with the device
		server = new BaSyxTCPServer<>(new VABMapProvider(controlComponent), serverPort);
		// - Start local BaSyx/TCP server
		server.start();

		
		// Register AAS and sub models in directory (push AAS descriptor to server)
		// - AAS repository server URL
		String aasRepoURL = "http://localhost:8080/basys.examples/Components/BaSys/1.0/aasServer/aas";
		// - Build an AAS descriptor, add sub model descriptors
		AASDescriptor deviceAASDescriptor = new AASDescriptor(lookupURN("AAS"), aasRepoURL);
		// Create sub model descriptors
		SubmodelDescriptor statusSMDescriptor = new SubmodelDescriptor("Status", lookupURN("Status"),
				aasRepoURL + "/submodels/Status");
		// Add sub model descriptor to AAS descriptor
		deviceAASDescriptor.addSubmodelDescriptor(statusSMDescriptor);
		// - Push AAS descriptor to server
		getRegistry().register(deviceAASDescriptor);
	}


	/**
	 * Stop smart device
	 */
	@Override
	public void stop() {
		// Stop local BaSyx/TCP server
		server.stop();
	}


	/**
	 * Wait for completion of all threads
	 */
	@Override
	public void waitFor() {
		// Wait for server end
		server.waitFor();
	}
}

