package org.eclipse.basyx.models.controlcomponent;

import static org.junit.Assert.assertTrue;

import org.junit.Test;



/**
 * Test cases for basic control component testing
 * 
 * @author kuhn
 *
 */
public class TestSimpleControlComponent {

	
	/**
	 * Execution state assignment tests
	 */
	@Test
	public void executionStateAssignmentTests() {
		// Instantiate simple control component
		SimpleControlComponent ctrlComponent = new SimpleControlComponent();
		
		// Change state, read state back
		ctrlComponent.setExecutionState("idle");
		assertTrue(ctrlComponent.getExecutionState().equals("idle"));
	}
	
	
	
	/**
	 * Test simple proxy control component
	 */
	@Test
	public void testSimpleProxyControlComponent() {
		// Instantiate simple proxy control component
		SimpleControlComponent ctrlComponent = new SimpleControlComponent();

		// Check component initial state
		// - Initial state
		assertTrue(ctrlComponent.getExecutionState().equals("idle"));
		// - Occupation state
		assertTrue(ctrlComponent.getOccupationState().equals(OccupationState.FREE));
		// - Execution mode
		assertTrue(ctrlComponent.getExecutionMode().equals(ExecutionMode.AUTO));
		// - Error state
		assertTrue(ctrlComponent.getErrorState().equals(""));
	}
	
	
	
	/**
	 * Run a normal sequence
	 */
	@Test
	public void testSimpleProxyControlComponentSequence() {
		// Instantiate simple proxy control component
		SimpleControlComponent ctrlComponent = new SimpleControlComponent();

		// Check component initial state
		assertTrue(ctrlComponent.getExecutionState().equals("idle"));

		// Change operation mode
		ctrlComponent.setOperationMode("DefaultService");
		// - Read operation mode back
		assertTrue(ctrlComponent.getOperationMode().equals("DefaultService"));

		// Issue start command
		ctrlComponent.setCommand("start");
		// - Check execution state
		assertTrue(ctrlComponent.getExecutionState().equals(ExecutionState.EXECUTE.getValue()));
		
		// Machine finishes service
		ctrlComponent.finishState();
		// - Check execution state
		assertTrue(ctrlComponent.getExecutionState().equals(ExecutionState.COMPLETE.getValue()));
		
		// Reset device
		ctrlComponent.setCommand("reset");
		// - Check execution state
		assertTrue(ctrlComponent.getExecutionState().equals(ExecutionState.RESETTING.getValue()));
		// - Indicate end of reset
		ctrlComponent.finishState();
		// - Check execution state
		assertTrue(ctrlComponent.getExecutionState().equals(ExecutionState.IDLE.getValue()));
	}
	
	
	/**
	 * Run a sequence that is aborted by device
	 */
	@Test
	public void testSimpleProxyControlComponentSequenceAbort() {
		// Instantiate simple proxy control component
		SimpleControlComponent ctrlComponent = new SimpleControlComponent();

		// Check component initial state
		assertTrue(ctrlComponent.getExecutionState().equals("idle"));

		// Change operation mode
		ctrlComponent.setOperationMode("DefaultService");
		// - Read operation mode back
		assertTrue(ctrlComponent.getOperationMode().equals("DefaultService"));

		// Issue start command
		ctrlComponent.setCommand("start");
		// - Check execution state
		assertTrue(ctrlComponent.getExecutionState().equals(ExecutionState.EXECUTE.getValue()));
		
		// Machine aborts service
		ctrlComponent.setCommand("abort");
		// - Check execution state
		assertTrue(ctrlComponent.getExecutionState().equals(ExecutionState.ABORTED.getValue()));
		
		// Operator clears machine state
		ctrlComponent.setCommand("clear");
		// - Check execution state
		assertTrue(ctrlComponent.getExecutionState().equals(ExecutionState.STOPPED.getValue()));

		// Operator restarts machine
		ctrlComponent.setCommand("reset");
		// - Check execution state
		assertTrue(ctrlComponent.getExecutionState().equals(ExecutionState.RESETTING.getValue()));
		// - Indicate end of reset
		ctrlComponent.finishState();
		// - Check execution state
		assertTrue(ctrlComponent.getExecutionState().equals(ExecutionState.IDLE.getValue()));
	}
}


