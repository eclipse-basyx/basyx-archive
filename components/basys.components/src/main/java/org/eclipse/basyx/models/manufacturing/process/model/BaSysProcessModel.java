package org.eclipse.basyx.models.manufacturing.process.model;

import java.util.Collection;
import java.util.LinkedList;

import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.models.manufacturing.process.model.device.DeviceIF;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;



/**
 * This class implements the base class for BaSyx process sub models
 * 
 * BaSyx processes are virtual devices as well and therefore processes may be nested
 * 
 * @author kuhn
 *
 */
public abstract class BaSysProcessModel extends SubModel implements DeviceIF {

	
	/**
	 * Version number of serialized instances
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	/**
	 * Process unique ID
	 */
	protected ModelUrn processID = null;

	
	
	
	
	/**
	 * Constructor
	 */
	public BaSysProcessModel(ModelUrn id) {
		// Store ID
		processID = id;
	}
	
	
	/**
	 * Start process step on given device
	 */
	public abstract void startProcessStepOnDevice(ModelUrn devID);

	
	/**
	 * Finish process step on given device
	 * 
	 * This implementation moves product forward to next device
	 */
	public abstract void finishProcessStepOnDevice(ModelUrn devID);

	
	/**
	 * Get URN ID of products on device
	 */
	public abstract Collection<ModelUrn> getCurrentProductsOnDevice(ModelUrn devID);

	
	/**
	 * Add a product to the manufacturing queue
	 */
	public abstract void addProductToManufacturingQueue(ModelUrn productID);
	
	
	/**
	 * Remove a product from the manufacturing queue
	 */
	public abstract ModelUrn removeProductFromManufacturingQueue();

	
	/**
	 * Get finished products
	 */
	public abstract Collection<ModelUrn> getFinishedProducts();
	
	
	/**
	 * Get waiting products (i.e. those products in the process that wait for being processed)
	 */
	public abstract Collection<ModelUrn> getWaitingProducts();
	
	
	/**
	 * Add a device to the process
	 */
	public abstract void addDeviceToProcess(ModelUrn deviceID);
	
	
	
	
	/**
	 * Process starts processing product with given ID
	 */
	public void startProcessing(ModelUrn productAASID) {
		// Add the given product to manufacturing queue
		addProductToManufacturingQueue(productAASID);
	}
	
	
	/**
	 * Device ends processing product
	 * 
	 * @return Product ID
	 */
	@Override
	public ModelUrn endProcessing() {
		// Get finished product
		ModelUrn productID = getFinishedProducts().iterator().next();
		
		// Remove product
		getFinishedProducts().remove(productID);
		
		// Return product ID
		return productID;
	}
	

	/**
	 * Get products on device. This returns an unsorted collection of products.
	 */
	@Override
	public Collection<ModelUrn> getProductsOnDevice() {
		// Products collection
		LinkedList<ModelUrn> productsOnDevice = new LinkedList<>();
		
		// Add waiting and finished products, as well as all products that are being processed
		productsOnDevice.addAll(getFinishedProducts());
		productsOnDevice.addAll(getWaitingProducts());
		//productsOnDevice.addAll(getWaitingProducts());
		
		// Return product collection
		return productsOnDevice;
	}

	
	/**
	 * Get device ID. This is the process ID in this case.
	 */
	@Override
	public ModelUrn getDeviceID() {
		// Return process ID
		return processID;
	}
}

