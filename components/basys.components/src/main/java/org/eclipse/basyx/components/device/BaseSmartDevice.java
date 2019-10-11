package org.eclipse.basyx.components.device;

import org.eclipse.basyx.models.controlcomponent.ControlComponentChangeListener;
import org.eclipse.basyx.models.controlcomponent.ExecutionMode;
import org.eclipse.basyx.models.controlcomponent.ExecutionState;
import org.eclipse.basyx.models.controlcomponent.OccupationState;
import org.eclipse.basyx.models.controlcomponent.SimpleControlComponent;




/**
 * Base class for BaSys smart devices
 * 
 * This base class implements a control component for a smart device with a SimpleControlComponent instance
 *  
 * @author kuhn
 *
 */
public abstract class BaseSmartDevice extends BaseDevice implements ControlComponentChangeListener, BaSysNativeDeviceStatusIF {

	
	/**
	 * Device control component
	 */
	protected SimpleControlComponent simpleControlComponent = null;
	
	

	
	/**
	 * Constructor
	 */
	public BaseSmartDevice() {
		// Do nothing
	}


	/**
	 * Start smart device
	 */
	@Override
	public void start() {
		// Create control component
		simpleControlComponent = new SimpleControlComponent();
		// - Register this component as event listener
		simpleControlComponent.addControlComponentChangeListener(this);
	}
	
	
	/**
	 * Get control component instance
	 */
	public SimpleControlComponent getControlComponent() {
		// Return control component instance
		return simpleControlComponent;
	}
	
	
	/**
	 * Indicate device status change
	 */
	@Override
	public void statusChange(String newStatus) {
		// Change control component execution status
		simpleControlComponent.setExecutionState(newStatus);
	}

	
	
	
	/**
	 * Smart device control component indicates a variable change
	 */
	@Override
	public void onVariableChange(String varName, Object newValue) {
		// TODO Auto-generated method stub
		
	}


	/**
	 * Smart device control component indicates an occupier change
	 */
	@Override
	public void onNewOccupier(String occupierId) {
		// TODO Auto-generated method stub
		
	}


	/**
	 * Smart device control component indicates an occupation state change
	 */
	@Override
	public void onNewOccupationState(OccupationState state) {
		// TODO Auto-generated method stub
		
	}


	/**
	 * Smart device control component indicates an execution mode change
	 */
	@Override
	public void onChangedExecutionMode(ExecutionMode newExecutionMode) {
		// TODO Auto-generated method stub
		
	}


	/**
	 * Smart device control component indicates an execution state change
	 */
	@Override
	public void onChangedExecutionState(ExecutionState newExecutionState) {
		// Indicate service start in "Executing" state
		if (newExecutionState == ExecutionState.EXECUTE) this.onServiceInvocation();
	}


	/**
	 * Smart device control component indicates an operation mode change
	 */
	@Override
	public void onChangedOperationMode(String newOperationMode) {
		// TODO Auto-generated method stub
		
	}


	/**
	 * Smart device control component indicates a work state change
	 */
	@Override
	public void onChangedWorkState(String newWorkState) {
		// TODO Auto-generated method stub
		
	}


	/**
	 * Smart device control component indicates an error state change
	 */
	@Override
	public void onChangedErrorState(String newWorkState) {
		// TODO Auto-generated method stub
		
	}
}

