package org.eclipse.basyx.examples.mockup.devicemanager;

import java.util.Map;

import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.property.SingleProperty;
import org.eclipse.basyx.tools.aas.active.HTTPGetter;
import org.eclipse.basyx.vab.modelprovider.lambda.VABLambdaProviderHelper;

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
		SubModel supplySM = new SubModel();
		// - Set submodel ID
		supplySM.setIdShort("Supply");
		//   - Property status: indicate device status
		SingleProperty availabililtyProp = new SingleProperty(dynamicProperty);
		availabililtyProp.setIdShort("partAvailability");
		supplySM.addSubModelElement(availabililtyProp);


		// Transfer device sub model to server
		aasServerConnection.createValue("/aas/submodels", supplySM);
	}


	/**
	 * Get AAS descriptor for managed device
	 */
	@Override 
	protected AASDescriptor getAASDescriptor() {
		// Create AAS and sub model descriptors
		AASDescriptor aasDescriptor = new AASDescriptor(lookupURN("AAS"), getAASEndpoint(lookupURN("AAS")));
		addSubModelDescriptorURI(aasDescriptor, lookupURN("Status"), "Status");
		addSubModelDescriptorURI(aasDescriptor, lookupURN("Supply"), "Supply");
		addSubModelDescriptorURI(aasDescriptor, lookupURN("Controller"), "Controller");
		
		// Return AAS and sub model descriptors
		return aasDescriptor;
	}
}

