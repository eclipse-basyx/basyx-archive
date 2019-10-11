package org.eclipse.basyx.models.manufacturing.process.model;

import org.eclipse.basyx.models.manufacturing.process.model.device.Device;



/**
 * Sequential process node
 * 
 * This class implements a node of a sequential process model.
 * 
 * @author kuhn
 *
 */
public class SequentialProcessNode {

	
	/**
	 * Device of this process node
	 */
	protected Device device = null;

	
	/**
	 * Previous process node
	 */
	protected SequentialProcessNode prevProcessNode = null;
	
	
	/**
	 * Next SequentialProcessNode node
	 */
	protected SequentialProcessNode nextProcessNode = null;
	
	

	
	
	/**
	 * Constructor
	 */
	public SequentialProcessNode() {
		// Do nothing
	}

	
	/**
	 * Constructor that accepts a device instance for this process step
	 * 
	 * @param dev Device instance
	 */
	public SequentialProcessNode(Device dev) {
		// Store device instance
		device = dev;
	}

	
	/**
	 * Set next process node
	 */
	public void setNextNode(SequentialProcessNode nextNode) {
		nextProcessNode = nextNode;
	}
	
	
	/**
	 * Set previous device
	 */
	public void setPrevNode(SequentialProcessNode prevNode) {
		prevProcessNode = prevNode;
	}
	
	
	/**
	 * Get next process node
	 */
	public SequentialProcessNode getNextNode() {
		return nextProcessNode;
	}
	
	
	/**
	 * Get previous process node
	 */
	public SequentialProcessNode getPrevNode() {
		return prevProcessNode;
	}
	
	
	/**
	 * Set device instance
	 */
	public void setDevice(Device dev) {
		device = dev;
	}

	
	/**
	 * Get device instance
	 */
	public Device getDevice() {
		return device;
	}
}

