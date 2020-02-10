package org.eclipse.basyx.models.manufacturing.process.model.device;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.models.manufacturing.process.model.queue.BoundedQueue;



/**
 * Virtual device that represents buffer space between two manufacturing devices
 * 
 * @author kuhn
 *
 */
public class QueueDevice extends Device {

	
	/**
	 * Queue instance
	 */
	protected Queue<ModelUrn> queue = null;
	
	
	
	/**
	 * Constructor - Creates unbounded queue
	 */
	public QueueDevice(ModelUrn id) {
		// Base constructor
		super(id);
		
		// Create unbounded queue
		queue = new LinkedList<ModelUrn>();
	}
	
	
	/**
	 * Constructor - Creates bounded queue
	 */
	public QueueDevice(ModelUrn id, int bound) {
		// Base constructor
		super(id);
		
		// Create bounded queue
		queue = new BoundedQueue<ModelUrn>(bound);
	}
	
	
	
	/**
	 * Push product to queue
	 */
	public void pushProduct(ModelUrn productAASID) {
		// Add product to queue
		queue.add(productAASID);
	}
	
	
	/**
	 * Device ends processing product with given ID
	 */
	public ModelUrn fetchProduct() {
		// Get product from queue
		return queue.poll();
	}

	

	/**
	 * Device starts processing product
	 */
	@Override
	public void startProcessing(ModelUrn productAASID) {
		// Push product to queue
		pushProduct(productAASID);
	}


	/**
	 * Device finishes processing product
	 */
	@Override
	public ModelUrn endProcessing() {
		// Fetch product from queue
		return fetchProduct();
	}
	
	
	/**
	 * Get products on device
	 */
	@Override
	public Collection<ModelUrn> getProductsOnDevice() {
		// Return products that are being processed by this device
		return queue;
	}
}

