package examples.deviceaas.devices.devicemanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.aas.backend.connector.http.HTTPConnectorProvider;
import org.eclipse.basyx.aas.backend.http.tools.GSONTools;
import org.eclipse.basyx.aas.backend.http.tools.factory.DefaultTypeFactory;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.SubModel;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.identifier.IdentifierType;
import org.eclipse.basyx.tools.webserviceclient.WebServiceRawClient;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;

import basys.examples.aasdescriptor.AASDescriptor;
import basys.examples.aasdescriptor.SubmodelDescriptor;
import basys.examples.tools.BaSyxManagerComponent;
import basys.examples.urntools.ModelUrn;
import examples.directory.ExamplesDirectory;



/**
 * Example manufacturing device manager code
 * 
 * This example code illustrates a basic device manager component. It implements the interaction between a device and the BaSyx infrastructure.
 * This code is for example deployed on the device (in case of availability of a Java runtime environment) or to an explicit connector device.
 * The Asset Administration Shell is not kept on the device, but transferred to an AAS server during registration. This ensures its presence also
 * if the device itself is not available, e.g. due to a failure. Important asset data, such as manufacturer, and support contacts remain available
 * in this case.
 * 
 * This code implements the following:
 * - Registration of device the AAS and sub models with the BaSyx infrastructure
 * - Updating of sub model properties to reflect the device status
 * - TCP connection to legacy device
 * 
 * 
 * @author kuhn
 *
 */
public class ManufacturingDeviceManager extends BaSyxManagerComponent {

	
	
	/**
	 * TCP server thread
	 */
	public class TCPServer extends Thread {
		
		
		/**
		 * Device manager reference
		 */
		protected ManufacturingDeviceManager deviceManagerInstance = null;
		
		
		
		/**
		 * Constructor
		 */
		public TCPServer(ManufacturingDeviceManager instance) {
			// Store instance reference
			deviceManagerInstance = instance;
		}
		
		
		/**
		 * Run server
		 */
		@Override
		public void run() {
			// Communication socket
			ServerSocket deviceCommunicationSocket = null;
			
			// IOExceptions on this level are not expected. The server will not recover
			try {
				// Create a server socket
				deviceCommunicationSocket = new ServerSocket(9998);
			
				// Continuously listen for incoming connection requests from device, 
				// but only support one connection at a time
				while (!deviceManagerInstance.endExecution) {
					// Accept one socket connection at a time
					Socket deviceConnectionSocket = deviceCommunicationSocket.accept();

					// Stream reader
					BufferedReader fromClient = new BufferedReader(new InputStreamReader(deviceConnectionSocket.getInputStream()));

					// Wait for data as long as socket is kept open
					while (!deviceConnectionSocket.isClosed()) {
						// An IOException here could indicate a closed socket
						try {
							// This server implements a very simple protocol that transmits status updates as strings
							String newDeviceStatus = fromClient.readLine();

							// Invoke callback
							deviceManagerInstance.onDeviceStatusChange(newDeviceStatus);
							// Handle IO exceptions
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}

				// Close socket
				deviceCommunicationSocket.close();
			
			// Handle all non-recoverable exceptions
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	/**
	 * VAB connection manager backend
	 */
	protected VABConnectionManager connManager = new VABConnectionManager(new ExamplesDirectory(), new HTTPConnectorProvider());

	
	/**
	 * GSON serializer
	 */
	protected GSONTools serializer = new GSONTools(new DefaultTypeFactory());
	
	
	/**
	 * Server connection
	 */
	protected VABElementProxy connSubModel = null;

	
	/**
	 * Status sub model URN
	 */
	protected String statusSubmodelURLOnServer = null;
	

	/**
	 * Controller sub model URN
	 */
	protected String controllerSubmodelURLOnServer = null;




		
	
	/**
	 * Constructor
	 */
	public ManufacturingDeviceManager() {
		// Do nothing here
	}
	
	
	
	/**
	 * Initialize the device, and register it with the backend
	 */
	@Override @SuppressWarnings("unchecked")
	public void initialize() {
		// Create TCP thread (or any other connection) to legacy device
		new TCPServer(this).start();
		
		
		// Server connections
		// - Connect to AAS server
		connSubModel = this.connManager.connectToVABElement("AASServer");
		// - Invoke BaSyx service calls via web services
		WebServiceRawClient client = new WebServiceRawClient();
		// - Directory web service URL
		String wsURL = "http://localhost:8080/basys.examples/Components/Directory/SQL";
		// - AAS repository server URL
		String aasSrvURL = "http://localhost:8080/basys.examples/Components/BaSys/1.0/aasServer";

		
		// Create device AAS
		// - Product ID (urn:<legalEntity>:<subUnit>:<subModel>:<version>:<revision>:<elementID>#<elementInstance>)
		ModelUrn deviceAASID = new ModelUrn("de.FHG", "devices.es.iese", "aas", "1.0", "3", "x-509", "001");
		// - Create device AAS
		AssetAdministrationShell aas = new AssetAdministrationShell();
		aas.put("idShort", "DeviceIDShort");
		// - AAS URL on server
		String aasURLOnServer = "/aas/submodels/aasRepository/"+deviceAASID.getEncodedURN();
		// - Transfer device AAS to server
		connSubModel.createElement(aasURLOnServer, aas);

		
		// The device also brings a sub model structure with an own ID that is being pushed on the server
		ModelUrn deviceStatusSMID = new ModelUrn("de.FHG", "devices.es.iese", "statusSM", "1.0", "3", "x-509", "001");
		// - Create generic sub model 
		SubModel statusSM = new SubModel();
		((Map<String, Object>) statusSM.get("properties")).put("status", "offline");
		// - Sub model URL on server
		statusSubmodelURLOnServer = "/aas/submodels/aasRepository/"+deviceStatusSMID.getEncodedURN();
		// - Transfer device sub model to server
		connSubModel.createElement(statusSubmodelURLOnServer, statusSM);

		
		// The device also brings a sub model structure with an own ID that is being pushed on the server
		ModelUrn deviceControllerSMID = new ModelUrn("de.FHG", "devices.es.iese", "controllerSM", "1.0", "3", "x-509", "001");
		// - Create generic sub model 
		SubModel controllerSM = new SubModel();
		//   - Create sub model contents
		Map<String, Object> listOfControllers = new HashMap<>();
		((Map<String, Object>) controllerSM.get("properties")).put("controllers", listOfControllers);
		// - Sub model URL on server
		controllerSubmodelURLOnServer = "/aas/submodels/aasRepository/"+deviceControllerSMID.getEncodedURN();
		// - Transfer device sub model to server
		connSubModel.createElement(controllerSubmodelURLOnServer, controllerSM);


		// Delete AAS registration for a fresh start - ignore if URL was not found. In this case, there was no previous registration and the registry was clean
		client.delete(wsURL+"/api/v1/registry/"+URLEncoder.encode(deviceAASID.getURN()));

			
		// Register AAS and sub models in directory (push AAS descriptor to server)
		// - Create an AAS descriptor
		AASDescriptor deviceAASDescriptor = new AASDescriptor(deviceAASID.getURN(), IdentifierType.URI, aasSrvURL+aasURLOnServer);
		// - Add a sub model descriptor for device
		SubmodelDescriptor deviceStatusSubmodelDescriptor = new SubmodelDescriptor(deviceStatusSMID.getURN(), IdentifierType.URI, aasSrvURL+statusSubmodelURLOnServer);
		deviceAASDescriptor.addSubmodelDescriptor(deviceStatusSubmodelDescriptor);
		SubmodelDescriptor deviceControllerSubmodelDescriptor = new SubmodelDescriptor(deviceControllerSMID.getURN(), IdentifierType.URI, aasSrvURL+controllerSubmodelURLOnServer);
		deviceAASDescriptor.addSubmodelDescriptor(deviceControllerSubmodelDescriptor);
		// - Push AAS descriptor to server
		client.post(wsURL+"/api/v1/registry", serializer.getJsonString(serializer.serialize(deviceAASDescriptor)));
	}
	
	
	/**
	 * Change the device status
	 */
	public void onDeviceStatusChange(String newStatus) {
		System.out.println("Status change:"+newStatus);
		
		// Device updates status to ready
		connSubModel.updateElementValue(statusSubmodelURLOnServer+"/properties/status", "ready");
	}
	
	
	
	
	/**
	 * Main method - instantiate and run this manager component
	 */
	public static void main(String[] args) {
		// Run this manager
		new ManufacturingDeviceManager().start();
	}
}
