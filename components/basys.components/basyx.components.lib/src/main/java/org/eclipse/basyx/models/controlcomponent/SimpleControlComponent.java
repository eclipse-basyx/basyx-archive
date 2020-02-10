package org.eclipse.basyx.models.controlcomponent;


/**
 * A simplified implementation of a control component for devices that offer only basic services
 * 
 * @author kuhn
 *
 */
public class SimpleControlComponent extends ControlComponent {

	
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
	}

	
	
	/**
	 * Indicate an execution state change
	 */
	@Override
	protected String filterExecutionState(String newExecutionState) {
		// Implement a simplified model that only consists of states idle/execute/complete/aborted/stopped
		switch (newExecutionState.toLowerCase()) {
			// Move from starting state directly to execute state after notifying the device
			case "starting": 
				return ExecutionState.EXECUTE.getValue();
				
			// Move from completing state directly to complete state
			case "completing":
				return ExecutionState.COMPLETE.getValue();

			// Move from resetting state directly to idle state
			case "resetting":
				//return ExecutionState.IDLE.getValue();
				break;

			// Move from aborting state directly to aborted state
			case "aborting":
				return ExecutionState.ABORTED.getValue();

			// Move from clearing state directly to stopped state
			case "clearing":
				return ExecutionState.STOPPED.getValue();
		}
		
		// Default behavior - leave execution state unchanged
		return newExecutionState;
	}

}

