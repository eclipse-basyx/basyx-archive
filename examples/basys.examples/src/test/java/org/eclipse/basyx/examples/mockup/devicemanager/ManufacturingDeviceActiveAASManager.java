package org.eclipse.basyx.examples.mockup.devicemanager;

import org.eclipse.basyx.aas.aggregator.restapi.AASAggregatorProvider;
import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.AASLambdaPropertyHelper;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.tools.aas.active.HTTPGetter;

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
		

		// Create sub model
		SubModel supplySM = new SubModel();
		// - Set submodel ID
		supplySM.setIdShort("Supply");
		//   - Property status: indicate device status
		Property availabililtyProp = new Property();
		// The device brings a sub model structure with an active AAS part
		// - Create dynamic get/set operation as lambda expression
		AASLambdaPropertyHelper.setLambdaValue(availabililtyProp,
				new HTTPGetter("http://localhost:8080/basys.examples/Mockup/Supplier"), null);
		availabililtyProp.setIdShort("partAvailability");
		supplySM.addSubModelElement(availabililtyProp);


		// Transfer device sub model to server
		aasServerConnection.setModelPropertyValue("/" + AASAggregatorProvider.PREFIX + "/" + lookupURN("AAS").getEncodedURN() + "/aas/submodels/" + supplySM.getIdShort(), supplySM);
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
		
		// Return AAS and sub model descriptors
		return aasDescriptor;
	}
}

