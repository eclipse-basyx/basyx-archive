package org.eclipse.basyx.models.manufacturing.process.model.device;

import java.util.Collection;

import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;




/**
 * Base class for device models
 * 
 * @author kuhn
 *
 */
public abstract class Device implements DeviceIF {

	
	/**
	 * Store device ID
	 */
	protected ModelUrn devId = null;
	
	
	
	/**
	 * Constructor
	 */
	public Device(ModelUrn id) {
		// Store device ID
		devId = id;
	}
	
	
	/**
	 * Device starts processing product with given ID
	 */
	@Override
	public abstract void startProcessing(ModelUrn productAASID);
	
	
	/**
	 * Device ends processing product
	 * 
	 * @return Product ID
	 */
	@Override
	public abstract ModelUrn endProcessing();
	

	/**
	 * Get products on device
	 */
	@Override
	public abstract Collection<ModelUrn> getProductsOnDevice();
	
	
	/**
	 * Get device ID
	 */
	@Override
	public ModelUrn getDeviceID() {
		// Return device ID
		return devId;
	}
}
