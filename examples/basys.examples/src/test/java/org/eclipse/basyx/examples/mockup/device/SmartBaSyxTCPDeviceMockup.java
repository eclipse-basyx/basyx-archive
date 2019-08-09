package org.eclipse.basyx.examples.mockup.device;

import org.eclipse.basyx.aas.backend.connector.http.HTTPConnectorProvider;
import org.eclipse.basyx.aas.backend.provider.VirtualPathModelProvider;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.SubModel;
import org.eclipse.basyx.components.device.BaseSmartDevice;
import org.eclipse.basyx.components.proxy.registry.AASHTTPRegistryProxy;
import org.eclipse.basyx.examples.support.directory.ExamplesPreconfiguredDirectory;
import org.eclipse.basyx.models.controlcomponent.ExecutionState;
import org.eclipse.basyx.tools.aasdescriptor.AASDescriptor;
import org.eclipse.basyx.tools.modelurn.ModelUrn;
import org.eclipse.basyx.vab.backend.server.basyx.BaSyxTCPServer;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;



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
	protected BaSyxTCPServer<VirtualPathModelProvider> server = null;
	
	
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
		addShortcut("Controller", new ModelUrn("urn:de.FHG:devices.es.iese:controllerSM:1.0:3:x-509#001"));

		// Configure BaSyx service - registry and connection manager
		setRegistry(new AASHTTPRegistryProxy("http://localhost:8080/basys.examples/Components/Directory/SQL"));
		setConnectionManager(new VABConnectionManager(new ExamplesPreconfiguredDirectory(), new HTTPConnectorProvider()));
	}

	
	/**
	 * Indicate a service invocation
	 */
	@Override
	protected void onServiceInvocation() {
		// Base implementation
		super.onServiceInvocation();
		
		// Implement the device invocation counter - read and increment invocation counter
		int invocations = (int) aasServerConnection.readElementValue(lookupURN("Status").getEncodedURN()+"/properties/statistics/default/invocations");
		aasServerConnection.updateElementValue(lookupURN("Status").getEncodedURN()+"/properties/statistics/default/invocations", ++invocations);		
	}

	
	/**
	 * Smart device control component indicates an execution state change
	 */
	@Override
	public void onChangedExecutionState(ExecutionState newExecutionState) {
		// Invoke base implementation
		super.onChangedExecutionState(newExecutionState);
		
		// Update property "properties/status" in external AAS
		aasServerConnection.updateElementValue(lookupURN("Status").getEncodedURN()+"/properties/status", newExecutionState.getValue());
	}




	/**
	 * Start smart device
	 */
	@Override
	public void start() {
		// Invoke base implementation
		super.start();
		
		
		// Connect to AAS server
		aasServerConnection = this.getConnectionManager().connectToHTTPVABElement("AASServer", "/aas/submodels/aasRepository/");

		
		// Create device AAS
		// - Create device AAS
		AssetAdministrationShell aas = new AssetAdministrationShell().putPath("idShort", "DeviceIDShort");
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

		
		// Register control component as local sub model
		// - This sub model will stay with the device
		server = new BaSyxTCPServer<>(new VirtualPathModelProvider(simpleControlComponent), serverPort);
		// - Start local BaSyx/TCP server
		server.start();

		
		// Register AAS and sub models in directory (push AAS descriptor to server)
		// - AAS repository server URL
		String aasRepoURL = "http://localhost:8080/basys.examples/Components/BaSys/1.0/aasServer";
		// - Build an AAS descriptor, add sub model descriptors  
		AASDescriptor deviceAASDescriptor = new AASDescriptor(lookupURN("AAS"), aasRepoURL)
			.addSubmodelDescriptor(lookupURN("Status"), aasRepoURL)
			.addSubmodelDescriptor(lookupURN("Controller"), "basyx://127.0.0.1:"+serverPort);
		// - Push AAS descriptor to server
		getRegistry().register(lookupURN("AAS"), deviceAASDescriptor);		
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

