package org.eclipse.basyx.components.controlcomponent;


/**
 * A simplified implementation of a control component for devices that offer only basic services
 * 
 * @author kuhn
 *
 */
public class SimpleControlComponent extends ControlComponent implements ControlComponentChangeListener {

	
	/**
	 * Version information for serialized instances 
	 */
	private static final long serialVersionUID = 1L;

		

	
	/**
	 * Constructor
	 */
	public SimpleControlComponent() {
		// Initial execution state
		setExecutionState("idle");
		
		// Register this component as change listener
		this.addControlComponentChangeListener(this);
	}
	
	
	
	
	/**
	 * Indicate change of a variable
	 */
	@Override
	public void onVariableChange(String varName, Object newValue) {
		// Do nothing
	}

	
	/**
	 * Indicate new occupier
	 */
	@Override
	public void onNewOccupier(String occupierId) {
		// Do nothing
	}

	
	/**
	 * Indicate new occupation state
	 */
	@Override
	public void onNewOccupationState(OccupationState state) {
		// Do nothing
	}

	
	/**
	 * Indicate an execution mode change
	 */
	@Override
	public void onChangedExecutionMode(ExecutionMode newExecutionMode) {
		// Do nothing
	}

	
	/**
	 * Indicate an execution state change
	 */
	@Override
	public void onChangedExecutionState(ExecutionState newExecutionState) {
		// Implement a simplified model that only consists of states idle/execute/complete/aborted/stopped
		switch (newExecutionState.value.toLowerCase()) {
			// Move from starting state directly to execute state after notifying the device
			case "starting":
				setExecutionState(ExecutionState.EXECUTE.getValue());
				break;
				
			// Move from completing state directly to complete state
			case "completing":
				setExecutionState(ExecutionState.COMPLETE.getValue());
				break;

			// Move from resetting state directly to idle state
			case "resetting":
				//setExecutionState(ExecutionState.IDLE.getValue());
				break;

			// Move from aborting state directly to aborted state
			case "aborting":
				setExecutionState(ExecutionState.ABORTED.getValue());
				break;

			// Move from clearing state directly to stopped state
			case "clearing":
				setExecutionState(ExecutionState.STOPPED.getValue());
				break;
		}
	}

	
	/**
	 * Indicate an operation mode change
	 */
	@Override
	public void onChangedOperationMode(String newOperationMode) {
		// Do nothing
	}

	
	/**
	 * Indicate an work state change
	 */
	@Override
	public void onChangedWorkState(String newWorkState) {
		// Do nothing
	}

	
	/**
	 * Indicate an error state change
	 */
	@Override
	public void onChangedErrorState(String newWorkState) {
		// Do nothing
	}


	/**
	 * Indicate a change in previous error variable
	 */
	@Override
	public void onChangedPrevError(String newWorkState) {
		// Do nothing
	}
}

