package org.eclipse.basyx.examples.mockup.application;

import org.eclipse.basyx.aas.api.modelurn.ModelUrn;
import org.eclipse.basyx.aas.api.registry.AASHTTPRegistryProxy;
import org.eclipse.basyx.aas.backend.connector.http.HTTPConnectorProvider;
import org.eclipse.basyx.aas.metamodel.hashmap.VABModelMap;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.components.service.BaseBaSyxService;
import org.eclipse.basyx.examples.support.directory.ExamplesPreconfiguredDirectory;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;




/**
 * Example BaSys 4.0 application that checks availability of device spare parts
 * 
 * @author kuhn
 *
 */
public class ReceiveDeviceMaintenanceApplication extends BaseBaSyxService {

	
	/**
	 * AAS server connection
	 */
	protected VABElementProxy aasServerConnection = null;

	
	
	/**
	 * Constructor
	 */
	public ReceiveDeviceMaintenanceApplication() {
		// Create AAS registry for this service
		setRegistry(new AASHTTPRegistryProxy("http://localhost:8080/basys.examples/Components/Directory/SQL"));
		
		// Service connection manager
		setConnectionManager(new VABConnectionManager(new ExamplesPreconfiguredDirectory(), new HTTPConnectorProvider()));

		// Register URNs of used objects
		addShortcut("AAS",        new ModelUrn("urn:de.FHG:devices.es.iese:aas:1.0:3:x-509#001"));
		addShortcut("Supply",     new ModelUrn("urn:de.FHG:devices.es.iese:supplySM:1.0:3:x-509#001"));
	}

	
	/**
	 * Start application
	 */
	@Override
	public void start() {
		// Base implementation
		super.start();

		// Create connection to device sub model
		// - This code assumes that network location of device sub model does not change while application is running
		AASDescriptor      aasDescriptor = getRegistry().lookupAAS(lookupURN("AAS"));
		SubmodelDescriptor smDescriptor  = aasDescriptor.getSubModelDescriptor(lookupURN("Supply"));
		// - Connect to status sub model end point
		aasServerConnection = getConnectionManager().connectToVABElementByURL(smDescriptor.getFirstEndpoint());		
	}
	

	/**
	 * Receive device status
	 */
	@SuppressWarnings("unchecked")
	public int getDevicePartSupplyStatus() {
		// Read complete sub model ("/")
		VABModelMap<Object> deviceSM = (VABModelMap<Object>) aasServerConnection.readElementValue("/");
		
		// Return status property
		return Integer.parseInt(deviceSM.getPath("properties/parts/availability").toString());
	}
}

