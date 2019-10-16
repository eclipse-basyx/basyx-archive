package org.eclipse.basyx.models.manufacturing.process.model.device;

import java.util.Collection;

import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;




/**
 * Minimal interface for a BaSyx device
 * 
 * @author kuhn
 *
 */
public interface DeviceIF {

	
	/**
	 * Device starts processing product with given ID
	 */
	public void startProcessing(ModelUrn productAASID);
	
	
	/**
	 * Device ends processing product
	 * 
	 * @return Product ID
	 */
	public ModelUrn endProcessing();
	

	/**
	 * Get products on device. This returns an unsorted collection of products.
	 */
	public Collection<ModelUrn> getProductsOnDevice();
	
	
	/**
	 * Get device ID
	 */
	public ModelUrn getDeviceID();
}

