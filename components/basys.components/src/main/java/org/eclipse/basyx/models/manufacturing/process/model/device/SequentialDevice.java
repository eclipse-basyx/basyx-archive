package org.eclipse.basyx.models.manufacturing.process.model.device;

import org.eclipse.basyx.aas.api.modelurn.ModelUrn;

/**
 * A sequential device model
 * 
 * This device processes one product at a time
 * 
 * @author kuhn
 *
 */
public class SequentialDevice extends QueueDevice {

	
	/**
	 * Constructor 
	 */
	public SequentialDevice(ModelUrn id) {
		// Invoke base constructor, create queue device with queue bound 1
		// - The device therefore may process one product at a time
		super(id, 1);
	}
}
