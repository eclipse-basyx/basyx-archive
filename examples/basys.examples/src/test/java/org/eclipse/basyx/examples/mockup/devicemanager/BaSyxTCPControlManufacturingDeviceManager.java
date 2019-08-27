package org.eclipse.basyx.examples.mockup.devicemanager;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.aas.api.modelurn.ModelUrn;
import org.eclipse.basyx.aas.api.registry.AASHTTPRegistryProxy;
import org.eclipse.basyx.aas.backend.connector.http.HTTPConnectorProvider;
import org.eclipse.basyx.aas.backend.provider.VirtualPathModelProvider;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.SubModel;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.descriptor.AASDescriptor;
import org.eclipse.basyx.components.devicemanager.TCPControllableDeviceManagerComponent;
import org.eclipse.basyx.examples.support.directory.ExamplesPreconfiguredDirectory;
import org.eclipse.basyx.models.controlcomponent.ControlComponentChangeListener;
import org.eclipse.basyx.vab.backend.server.basyx.BaSyxTCPServer;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;



/**
 * Example manufacturing device manager code
 * 
 * This example code extends the BaSyxTCPManufacturingDeviceManager by adding a control component sub model to control the managed device.
 *  
 * @author kuhn
 *
 */
public class BaSyxTCPControlManufacturingDeviceManager extends TCPControllableDeviceManagerComponent implements ControlComponentChangeListener {

	
	/**
	 * AAS server connection
	 */
	protected VABElementProxy aasServerConnection = null;

	
	

	/**
	 * Constructor
	 */
	public BaSyxTCPControlManufacturingDeviceManager(int deviceTCPPort, int ctrlComponentServerPort) {
		// Invoke base constructor
		super(deviceTCPPort, ctrlComponentServerPort);
		
		
		// Set registry that will be used by this service
		setRegistry(new AASHTTPRegistryProxy("http://localhost:8080/basys.examples/Components/Directory/SQL"));
		
		
		// Set service connection manager and create AAS server connection
		setConnectionManager(new VABConnectionManager(new ExamplesPreconfiguredDirectory(), new HTTPConnectorProvider()));
		// - Create AAS server connection
		aasServerConnection = getConnectionManager().connectToHTTPVABElement("AASServer", "/aas/submodels/aasRepository/");
		
		
		// Set AAS server VAB object ID, AAS server URL, and AAS server path prefix
		setAASServerObjectID("AASServer");
		setAASServerURL("http://localhost:8080/basys.examples/Components/BaSys/1.0/aasServer");
		setAASServerPathPrefix("/aas/submodels/aasRepository/");
	}

	
	/**
	 * Initialize the device, and register it with the backend
	 */
	@Override 
	public void start() {
		// Base implementation
		super.start();
		
		// Create the device AAS and sub model structure
		createDeviceAASAndSubModels();
		
		// Register AAS and sub model descriptors in directory (push AAS descriptor to server)
		getRegistry().register(lookupURN("AAS"), getAASDescriptor());
	}

	
	
	/**
	 * Create the device AAS and sub model structure
	 */
	@SuppressWarnings("unchecked")
	protected void createDeviceAASAndSubModels() {
		// Register URNs of managed VAB objects
		addShortcut("AAS",        new ModelUrn("urn:de.FHG:devices.es.iese:aas:1.0:3:x-509#001"));
		addShortcut("Status",     new ModelUrn("urn:de.FHG:devices.es.iese:statusSM:1.0:3:x-509#001"));
		addShortcut("Controller", new ModelUrn("urn:de.FHG:devices.es.iese:controllerSM:1.0:3:x-509#001"));
		

		// Create device AAS
		AssetAdministrationShell aas = new AssetAdministrationShell();
		// - Populate AAS
		aas.setId("DeviceIDShort");
		// - Transfer device AAS to server
		aasServerConnection.createElement(lookupURN("AAS").toString(), aas);

	
		// The device also brings a sub model structure with an own ID that is being pushed on the server
		// - Create generic sub model and add properties
		SubModel statusSM = new SubModel()
		//   - Property status: indicate device status
				.putPath("properties/status", "offline")
		//   - Property statistics: export invocation statistics for every service
		//     - invocations: indicate total service invocations. Properties are not persisted in this example,
		//                    therefore we start counting always at 0.
				.putPath("properties/statistics/default/invocations", 0);
		// - Transfer device sub model to server
		aasServerConnection.createElement(lookupURN("Status").toString(), statusSM);

		
		// The device also brings a sub model structure with an own ID that is being pushed on the server
		// - Create generic sub model 
		SubModel controllerSM = new SubModel();
		//   - Create sub model contents manually
		Map<String, Object> listOfControllers = new HashMap<>();
		((Map<String, Object>) controllerSM.get(SubModel.PROPERTIES)).put("controllers", listOfControllers);
		// - Transfer device sub model to server
		aasServerConnection.createElement(lookupURN("Controller").toString(), controllerSM);

				
		
		// Register URNs of control component VAB object
		addShortcut("ControlComponent", new ModelUrn("urn:de.FHG:devices.es.iese:controlComponentSM:1.0:3:x-509#001"));

		
		// Register control component as local sub model
		// - This sub model will stay with the device
		server = new BaSyxTCPServer<>(new VirtualPathModelProvider(simpleControlComponent), controlComponentServerPort);
		// - Start local BaSyx/TCP server
		server.start();
	}
	
	@Override
	public void stop() {
		super.stop();
		// End server
		server.stop();
	}
	
	
	/**
	 * Get AAS descriptor for managed device
	 */
	@Override 
	protected AASDescriptor getAASDescriptor() {
		// Create AAS and sub model descriptors
		AASDescriptor aasDescriptor = createAASDescriptorURI(lookupURN("AAS"));
		addSubModelDescriptorURI(aasDescriptor, lookupURN("Status"));
		addSubModelDescriptorURI(aasDescriptor, lookupURN("Controller"));
		
		// Add descriptor for control component sub model
		aasDescriptor.addSubmodelDescriptor(lookupURN("ControlComponent"), "basyx://127.0.0.1:"+controlComponentServerPort);
		
		// Return AAS, sub model descriptors, and added control component sub model descriptor
		return aasDescriptor;
	}

	
	
	/**
	 * Received a string from network
	 */
	@Override
	public void onReceive(byte[] rxData) {
		// Do not process null values
		if (rxData == null) return;
		
		// Convert received data to string
		String rxStr = new String(rxData); 
		// - Trim string to remove possibly trailing and leading white spaces
		rxStr = rxStr.trim();
		
		System.out.println("- - --------------- RXMSG:"+rxStr);
		
		// Check what was being received. This check is performed based on a prefix that he device has to provide);
		// - Update of device status
		if (hasPrefix(rxStr, "status:")) aasServerConnection.updateElementValue(lookupURN("Status").getEncodedURN()+"/properties/status", removePrefix(rxStr, "status"));
		// - Device indicates service invocation
		if (hasPrefix(rxStr, "invocation:")) {
			// Start of process
			if (hasPrefix(rxStr, "invocation:start")) {
				// Read and increment invocation counter
				int invocations = (int) aasServerConnection.readElementValue(lookupURN("Status").getEncodedURN()+"/properties/statistics/default/invocations");
				aasServerConnection.updateElementValue(lookupURN("Status").getEncodedURN()+"/properties/statistics/default/invocations", ++invocations);
			}
			
			// End of process
			if (hasPrefix(rxStr, "invocation:end")) {
				// Do nothing for now
			}
		}
		
		// Let base implementation process the message
		super.onReceive(rxData);
	}
}

