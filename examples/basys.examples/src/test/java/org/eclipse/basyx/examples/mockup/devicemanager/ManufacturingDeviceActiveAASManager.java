package org.eclipse.basyx.examples.mockup.devicemanager;

import java.util.Map;

import org.eclipse.basyx.aas.api.modelurn.ModelUrn;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.SubModel;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.descriptor.AASDescriptor;
import org.eclipse.basyx.tools.aas.active.HTTPGetter;
import org.eclipse.basyx.vab.provider.lambda.VABLambdaProviderHelper;



/**
 * Example manufacturing device manager code
 * 
 * This manager extends class ManufacturingDeviceManager. It adds an active sub model that dynamically queries the availability status of
 * machine spare parts from a supplier server. 
 * 
 * 
 * @author kuhn
 *
 */
public class ManufacturingDeviceActiveAASManager extends ManufacturingDeviceManager {


	/**
	 * Constructor
	 */
	public ManufacturingDeviceActiveAASManager(int port) {
		// Invoke base constructor
		super(port);
	}

	
	/**
	 * Create the device AAS and sub model structure
	 */
	@Override
	protected void createDeviceAASAndSubModels() {
		// Invoke base implementation
		super.createDeviceAASAndSubModels();
		
		
		// Register URNs of managed VAB objects
		addShortcut("Supply",     new ModelUrn("urn:de.FHG:devices.es.iese:supplySM:1.0:3:x-509#001"));
		

		// The device brings a sub model structure with an active AAS part
		// - Create dynamic get/set operation as lambda expression
		Map<String, Object> dynamicProperty = VABLambdaProviderHelper.createSimple(new HTTPGetter("http://localhost:8080/basys.examples/Mockup/Supplier"), null);

		// Create sub model
		SubModel supplySM = new SubModel()
		//   - Property status: indicate device status
				.putPath("properties/parts/availability", dynamicProperty);

		// Transfer device sub model to server
		aasServerConnection.createElement(lookupURN("Supply").toString(), supplySM);
	}


	/**
	 * Get AAS descriptor for managed device
	 */
	@Override 
	protected AASDescriptor getAASDescriptor() {
		// Create AAS and sub model descriptors
		AASDescriptor aasDescriptor = createAASDescriptorURI(lookupURN("AAS"));
		addSubModelDescriptorURI(aasDescriptor, lookupURN("Status"));
		addSubModelDescriptorURI(aasDescriptor, lookupURN("Supply"));
		addSubModelDescriptorURI(aasDescriptor, lookupURN("Controller"));
		
		// Return AAS and sub model descriptors
		return aasDescriptor;
	}
}

