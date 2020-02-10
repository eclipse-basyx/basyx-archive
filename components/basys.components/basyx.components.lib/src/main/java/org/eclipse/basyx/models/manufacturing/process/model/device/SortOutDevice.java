package org.eclipse.basyx.models.manufacturing.process.model.device;

import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;

/**
 * A device model that sorts products out
 * 
 * This device processes one product at a time
 * 
 * @author kuhn
 *
 */
public class SortOutDevice extends SequentialDevice {

	
	/**
	 * Constructor 
	 */
	public SortOutDevice(ModelUrn id) {
		// Invoke base constructor, create queue device with queue bound 1
		// - The device therefore may process one product at a time
		super(id);
	}
	
	
	/**
	 * Remove current product from device
	 * 
	 * This function models the sorting out of a (faulty) product from production line
	 */
	public ModelUrn removeCurrentProduct() {
		// Remove product
		return this.removeCurrentProduct();
	}
}

