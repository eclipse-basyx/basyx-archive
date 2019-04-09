package examples.deviceaas.devices.devicemanager;

import java.util.Map;

import org.eclipse.basyx.aas.backend.connector.http.HTTPConnectorProvider;
import org.eclipse.basyx.aas.backend.http.tools.GSONTools;
import org.eclipse.basyx.aas.backend.http.tools.factory.DefaultTypeFactory;
import org.eclipse.basyx.tools.webserviceclient.WebServiceRawClient;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;

import basys.examples.aasdescriptor.AASDescriptor;
import basys.examples.aasdescriptor.SubmodelDescriptor;
import basys.examples.deployment.BaSyxContextRunnable;
import basys.examples.urntools.ModelUrn;
import examples.directory.ExamplesDirectory;




/**
 * Example BaSys 4.0 application that receives a status change
 * 
 * @author kuhn
 *
 */
public class ReceiveDeviceStatusApplication implements BaSyxContextRunnable {

	
	/**
	 * The WebServiceRawClient invokes BaSyx service calls via web services
	 */
	protected WebServiceRawClient client = new WebServiceRawClient();
	
	
	/**
	 * Directory web service URL
	 */
	protected String wsURL = "http://localhost:8080/basys.examples/Components/Directory/SQL";

	
	/**
	 * URN of device - used to identify device
	 */
	protected ModelUrn deviceAASID = new ModelUrn("de.FHG", "devices.es.iese", "aas", "1.0", "3", "x-509", "001");

	
	/**
	 * URN of device sub model - used too identify device status sub model
	 */
	protected ModelUrn deviceStatusSMID = new ModelUrn("de.FHG", "devices.es.iese", "statusSM", "1.0", "3", "x-509", "001");
	

	/**
	 * GSON serializer
	 */
	protected GSONTools serializer = new GSONTools(new DefaultTypeFactory());

	
	/**
	 * VAB connection manager backend
	 */
	protected VABConnectionManager connManager = new VABConnectionManager(new ExamplesDirectory(), new HTTPConnectorProvider());

	
	/**
	 * Application name
	 */
	protected String applicationName = null;
	
	
	
	
	
	/**
	 * Constructor
	 */
	public ReceiveDeviceStatusApplication() {
		// Create WebServiceRawClient that invokes BaSyx service calls via web services
		client = new WebServiceRawClient();
	}
	
	
	/**
	 * Receive status
	 */
	@SuppressWarnings("unchecked")
	public String getDeviceStatus() {
		// Lookup device AAS
		// - Lookup AAS from AAS directory, get AAS descriptor
		String jsonData = client.get(wsURL+"/api/v1/registry/"+deviceAASID.getEncodedURN());
		// - Read AAS end point from AAS descriptor
		AASDescriptor aasDescriptor = new AASDescriptor((Map<String, Object>) serializer.deserialize(serializer.getMap(serializer.getObjFromJsonStr(jsonData))));


		// - Get information about status sub model
		SubmodelDescriptor smDescriptor = aasDescriptor.getSubModelDescriptor(deviceStatusSMID.getURN());

		
		// Connect to status sub model end point
		VABElementProxy connSM = connManager.connectToVABElementByURL(smDescriptor.getFirstEndpoint());
		// - Read elements
		Map<String, Object> deviceSM = (Map<String, Object>) connSM.readElementValue("/");
		// - Output status information
		System.out.println("ReadBack:"+((Map<String, Object>) deviceSM.get("properties")).get("status"));
		
		
		// Return status
		return ((Map<String, Object>) deviceSM.get("properties")).get("status").toString();
	}
	
	
	
	/**
	 * Start the application
	 */
	@Override
	public void start() {
		// Do nothing
	}


	/**
	 * Stop the application
	 */
	@Override
	public void stop() {
		// Do nothing
	}
	
	
	/**
	 * Change the runnable name
	 */
	@Override
	public BaSyxContextRunnable setName(String newName) {
		// Set name
		applicationName = newName;
		
		// Return 'this' reference to enable chaining
		return this;
	}
	
	
	/**
	 * Get runnable name
	 */
	@Override
	public String getName() {
		return applicationName;
	}

}
