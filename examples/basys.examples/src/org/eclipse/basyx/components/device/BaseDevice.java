package org.eclipse.basyx.components.device;

import org.eclipse.basyx.components.controlcomponent.ExecutionState;
import org.eclipse.basyx.components.service.BaseBaSyxService;




/**
 * Base class for integrating devices with BaSys
 * 
 * This base class provides a simple framework for integrating devices with BaSys/BaSyx. It defines callback 
 * functions that are invoked by native devices, and that are used to communicate the device status. 
 *  
 * @author kuhn
 *
 */
public abstract class BaseDevice extends BaseBaSyxService implements BaSysNativeDeviceStatusIF {

	
	
	/**
	 * Constructor
	 */
	public BaseDevice() {
		// Do nothing
	}
	
	
	/**
	 * Device interface function: (usually native code) indicates that device has been initialized
	 */
	@Override
	public void deviceInitialized() {
		// Change status
		statusChange(ExecutionState.IDLE.getValue());
	}
	
	
	/**
	 * Device interface function: (usually native code) indicates that device service is running
	 */
	@Override
	public void serviceRunning() {
		// Indicate service invocation to device manager
		onServiceInvocation();
		
		// Change status
		statusChange(ExecutionState.EXECUTE.getValue());
	}
	
	
	/**
	 * Device interface function: (usually native code) indicates that device service execution has completed
	 */
	@Override
	public void serviceCompleted() {
		// Change status
		statusChange(ExecutionState.COMPLETE.getValue());		
	}

	
	/**
	 * Device interface function: (usually native code) indicates that device is ready again
	 */
	@Override
	public void resetCompleted() {
		// Change status
		statusChange(ExecutionState.IDLE.getValue());		
	}



	/**
	 * Indicate device status change
	 */
	protected abstract void statusChange(String newStatus);
	
	
	/**
	 * Indicate device service invocation
	 */
	protected abstract void onServiceInvocation();

	
	/**
	 * Indicate device service end
	 */
	protected abstract void onServiceEnd();
}
