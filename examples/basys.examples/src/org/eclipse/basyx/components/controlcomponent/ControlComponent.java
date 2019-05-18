package org.eclipse.basyx.components.controlcomponent;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;



/**
 * BaSys 4.0 control component interface. This is a VAB object that cannot be serialized.
 * 
 * @author kuhn
 *
 */
public abstract class ControlComponent extends HashMap<String, Object> {

	
	/**
	 * The status map implements the service/ substructure of the control component structure. It also 
	 * indicates variable changes via callbacks of the outer class.
	 * 
	 * @author kuhn
	 *
	 */
	class StatusMap extends HashMap<String, Object> {

		
		/**
		 * Version number of serialized instances
		 */
		private static final long serialVersionUID = 1L;
		
		
		
		/**
		 * Constructor
		 */
		public StatusMap() {
			// Populate control component "status" sub structure 
			put("occupationState ", 0);               // Occupation state: FREE
			put("occupier", "");                      // Occupier: none
			put("lastOccupier", "");                  // Last occupier: none
			put("exMode", 1);                         // Execution mode: AUTO
			put("exState", "IDLE");                   // Execution state: IDLE
			put("opMode", "");                        // Component specific operation mode (e.g. active service)
			put("workState", "");                     // Component specific work state
			put("errorState", "");                    // Component error state
			put("prevError", "");                     // Component previous error
		}
		
		
		/**
		 * Update an value
		 * 
		 * @return Added value
		 */
		@Override
		public Object put(String key, Object parValue) {
			// Value to be put in map
			Object value = parValue;
			// - Eventually we have to change the value to be put into the variable
			switch(key) {
				case "exState": value = filterExecutionState(value.toString()); break;
				case "opMode":  value = filterOperationMode(value.toString()); break;
			}
			
			// Invoke base implementation
			Object result = super.put(key, value);
			
			// Indicate value change
			for (ControlComponentChangeListener listener: listeners) listener.onVariableChange(key, value);
			
			// Indicate specific changes to callback operations of control component
			switch(key) {
				case "occupationState": for (ControlComponentChangeListener listener: listeners) listener.onNewOccupationState(OccupationState.byValue((int) value)); break;
				case "occupier":        for (ControlComponentChangeListener listener: listeners) listener.onNewOccupier(value.toString()); break;
				case "lastOccupier":    for (ControlComponentChangeListener listener: listeners) listener.onLastOccupier(value.toString()); break;
				case "exMode":          for (ControlComponentChangeListener listener: listeners) listener.onChangedExecutionMode(ExecutionMode.byValue((int) value)); break;
				case "exState":         for (ControlComponentChangeListener listener: listeners) listener.onChangedExecutionState(ExecutionState.byValue(value.toString())); break;
				case "opMode":          for (ControlComponentChangeListener listener: listeners) listener.onChangedOperationMode(value.toString()); break;
				case "workState":       for (ControlComponentChangeListener listener: listeners) listener.onChangedWorkState(value.toString()); break;
				case "errorState":      for (ControlComponentChangeListener listener: listeners) listener.onChangedErrorState(value.toString()); break;
				case "prevError":       for (ControlComponentChangeListener listener: listeners) listener.onChangedPrevError(value.toString()); break;
			}
						
			// Return result
			return result;
		}
	}
	
	
	
	
	/**
	 * Version number of serialized instances
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * Operations map
	 */
	protected Map<String, Function<?, ?>> operations = new HashMap<String, Function<?, ?>>();
	
	
	/**
	 * Saved occupier ID in case of local occupier overwrite
	 */
	protected String savedOccupierID = null;
	
	
	/**
	 * Status map
	 */
	protected Map<String, Object> status = null;

	
	/**
	 * Changed control component state listeners
	 */
	protected Collection<ControlComponentChangeListener> listeners = new LinkedList<>();



	
	/**
	 * Constructor
	 */
	public ControlComponent() {
		// Add control component output signals to map
		// - Order list
		put("orderList", new LinkedList<String>());
		// - "status" sub structure
		status = new StatusMap();
		put("status", status);

		// Input signals
		// - Command / stores last command
		put("cmd", "");                           // No command
		put("localOverwrite", "");                // Local override signal
		put("localOverwriteFree", "");            // Local override release signal
		
		// Operations
		// - Add "operations" sub structure
		put("operations", operations);
		// - Service operations
		Map<String, Function<?, ?>> serviceOperations = new HashMap<>();
		// - Populate service operations
		serviceOperations.put("free",       (Function<String, Void> & Serializable) (v) -> {freeControlComponent(v); return null;});
		serviceOperations.put("occupy",     (Function<String, Void> & Serializable) (v) -> {occupyControlComponent(v); return null;});
		serviceOperations.put("priority",   (Function<String, Void> & Serializable) (v) -> {priorityOccupation(v); return null;});
		serviceOperations.put("auto",       (Function<String, Void> & Serializable) (v) -> {this.setExecutionMode(ExecutionMode.AUTO); return null;});
		serviceOperations.put("semiauto",   (Function<String, Void> & Serializable) (v) -> {this.setExecutionMode(ExecutionMode.SEMIAUTO); return null;});
		serviceOperations.put("manual",     (Function<String, Void> & Serializable) (v) -> {this.setExecutionMode(ExecutionMode.MANUAL); return null;});
		serviceOperations.put("simulation", (Function<String, Void> & Serializable) (v) -> {this.setExecutionMode(ExecutionMode.SIMULATION); return null;});
		serviceOperations.put("start",      (Function<String, Void> & Serializable) (v) -> {this.changeExecutionState("START"); return null;});
		serviceOperations.put("reset",      (Function<String, Void> & Serializable) (v) -> {this.changeExecutionState("RESET"); return null;});
		serviceOperations.put("hold",       (Function<String, Void> & Serializable) (v) -> {this.changeExecutionState("HOLD"); return null;});
		serviceOperations.put("unhold",     (Function<String, Void> & Serializable) (v) -> {this.changeExecutionState("UNHOLD"); return null;});
		serviceOperations.put("suspend",    (Function<String, Void> & Serializable) (v) -> {this.changeExecutionState("SUSPEND"); return null;});
		serviceOperations.put("unsuspend",  (Function<String, Void> & Serializable) (v) -> {this.changeExecutionState("UNSUSPEND"); return null;});
		serviceOperations.put("abort",      (Function<String, Void> & Serializable) (v) -> {this.changeExecutionState("ABORT"); return null;});
		serviceOperations.put("stop",       (Function<String, Void> & Serializable) (v) -> {this.changeExecutionState("STOP"); return null;});
		serviceOperations.put("clear",      (Function<String, Void> & Serializable) (v) -> {this.changeExecutionState("CLEAR"); return null;});
		serviceOperations.put("reset",      (Function<String, Void> & Serializable) (v) -> {this.changeExecutionState("RESET"); return null;});
		serviceOperations.put("bstate",     (Function<String, Void> & Serializable) (v) -> {this.setOperationMode("BSTATE"); return null;});
	}


	
	/**
	 * Optionally filter a set execution state. This function is always invoked when an execution state changes
	 */
	protected String filterExecutionState(String exState) {
		// Do nothing here
		return exState;
	}

	
	/**
	 * Optionally filter a set operation mode. This function is always invoked when an operation mode changes
	 */
	protected String filterOperationMode(String opMode) {
		// Do nothing here
		return opMode;
	}

	
	
	/**
	 * Add ControlComponentChangeListener
	 */
	public void addControlComponentChangeListener(ControlComponentChangeListener listener) {
		listeners.add(listener);
	}
	
	
	/**
	 * Remove ControlComponentChangeListener
	 */
	public void removeControlComponentChangeListener(ControlComponentChangeListener listener) {
		listeners.remove(listener);
	}
	

	/**
	 * Get "operations" map
	 */
	protected Map<String, Function<?, ?>> getOperationsMap() {
		return operations;
	}
	
	
	
	/**
	 * Update an value
	 * 
	 * @return Added value
	 */
	@Override
	public Object put(String key, Object value) {
		// Invoke base implementation
		Object result = super.put(key, value);
		
		// Indicate value change
		for (ControlComponentChangeListener listener: listeners) listener.onVariableChange(key, value);
		
		// Process variable changes
		switch(key) {
			case "cmd": 			   changeExecutionState(value.toString()); break;
			case "localOverwrite": 	   invokeLocalOverwrite(); break;
			case "localOverwriteFree": clearLocalOverwrite(); break;
		}
					
		// Return result
		return result;
	}
	
	
	/**
	 * Helper method - free this control component
	 */
	private void freeControlComponent(String senderId) {
		// Update occupier if sender is occupier
		if (senderId.equals(this.getOccupierID())) {
			// Get occupier from last occupier and reset last occupier
			this.setOccupierID(this.getLastOccupierID());
			this.setLastOccupierID("");
			// Component is free if last occupier is empty, occupied otherwise
			if (this.getOccupierID().isEmpty()) this.setOccupationState(OccupationState.FREE); else this.setOccupationState(OccupationState.OCCUPIED);
		}
	}

	
	/**
	 * Helper method - occupy this control component if it is free
	 */
	private void occupyControlComponent(String occupier) {
		// Update occupier if component is FREE
		if (this.getOccupationState().equals(OccupationState.FREE)) {this.setOccupierID(occupier); this.setOccupationState(OccupationState.OCCUPIED);}
	}

	
	/**
	 * Helper method - priority occupation of this component
	 */
	private void priorityOccupation(String occupier) {
		// Occupy component if component is FREE or OCCUPIED
		if ((this.getOccupationState().equals(OccupationState.FREE)) || (this.getOccupationState().equals(OccupationState.OCCUPIED))) {
			this.setLastOccupierID(this.getOccupierID());
			this.setOccupierID(occupier); 
			this.setOccupationState(OccupationState.PRIORITY);
		}
	}
	
	
	/**
	 * Helper method - local overwrite of OCCUPIED or PRIORITY occupation
	 */
	private void invokeLocalOverwrite() {
		// Store current occupier because we need to restore it later
		savedOccupierID = this.getOccupierID();
		
		// Enter local overwrite state
		this.setOccupationState(OccupationState.LOCAL);
		this.setOccupierID("LOCAL");
	}
	
	
	/**
	 * Helper method - clear local occupier overwrite status
	 */
	private void clearLocalOverwrite() {
		// Restore current occupier ID
		this.setOccupierID(savedOccupierID);

		// Restore occupier state based on variables
		if (this.getOccupierID().isEmpty()) this.setOccupationState(OccupationState.FREE); else
			if (this.getLastOccupierID().isEmpty()) this.setOccupationState(OccupationState.OCCUPIED); else 
				this.setOccupationState(OccupationState.PRIORITY);
	}
	
	
	/**
	 * Change execution state based on execution order
	 */
	private void changeExecutionState(String orderString) {
		// Do not react on empty values
		if (orderString.isEmpty()) return;

		// Get execution order based on order string
		ExecutionOrder order = ExecutionOrder.byValue(orderString);		

		// Check if execution order leads to valid state in current state
		switch(getExecutionState().toLowerCase()) {
			case "idle":
				// Process expected orders
				if (order.equals(ExecutionOrder.START)) {this.setExecutionState(ExecutionState.STARTING.getValue()); break;}
				if (order.equals(ExecutionOrder.STOP))  {this.setExecutionState(ExecutionState.STOPPING.getValue()); break;}
				if (order.equals(ExecutionOrder.ABORT)) {this.setExecutionState(ExecutionState.ABORTING.getValue()); break;}
				// Unexpected order in this state
				throw new RuntimeException("Unexpected command "+orderString+" in state "+getExecutionState());

			case "starting":
				if (order.equals(ExecutionOrder.STOP))  {this.setExecutionState(ExecutionState.STOPPING.getValue()); break;}
				if (order.equals(ExecutionOrder.ABORT)) {this.setExecutionState(ExecutionState.ABORTING.getValue()); break;}
				// Unexpected order in this state
				throw new RuntimeException("Unexpected command "+orderString+" in state "+getExecutionState());

			case "execute":
				// Process expected orders
				if (order.equals(ExecutionOrder.COMPLETE)) {this.setExecutionState(ExecutionState.COMPLETING.getValue()); break;}
				if (order.equals(ExecutionOrder.HOLD))     {this.setExecutionState(ExecutionState.HOLDING.getValue()); break;}
				if (order.equals(ExecutionOrder.SUSPEND))  {this.setExecutionState(ExecutionState.SUSPENDING.getValue()); break;}
				if (order.equals(ExecutionOrder.STOP))     {this.setExecutionState(ExecutionState.STOPPING.getValue()); break;}
				if (order.equals(ExecutionOrder.ABORT))    {this.setExecutionState(ExecutionState.ABORTING.getValue()); break;}
				// Unexpected order in this state
				throw new RuntimeException("Unexpected command "+orderString+" in state "+getExecutionState());

			case "completing":
				if (order.equals(ExecutionOrder.STOP))     {this.setExecutionState(ExecutionState.STOPPING.getValue()); break;}
				if (order.equals(ExecutionOrder.ABORT))    {this.setExecutionState(ExecutionState.ABORTING.getValue()); break;}
				// Unexpected order in this state
				throw new RuntimeException("Unexpected command "+orderString+" in state "+getExecutionState());

			case "complete":
				if (order.equals(ExecutionOrder.RESET)) {this.setExecutionState(ExecutionState.RESETTING.getValue()); break;}
				if (order.equals(ExecutionOrder.STOP))  {this.setExecutionState(ExecutionState.STOPPING.getValue()); break;}
				if (order.equals(ExecutionOrder.ABORT)) {this.setExecutionState(ExecutionState.ABORTING.getValue()); break;}
				// Unexpected order in this state
				throw new RuntimeException("Unexpected command "+orderString+" in state "+getExecutionState());

			case "resetting":
				if (order.equals(ExecutionOrder.STOP))     {this.setExecutionState(ExecutionState.STOPPING.getValue()); break;}
				if (order.equals(ExecutionOrder.ABORT))    {this.setExecutionState(ExecutionState.ABORTING.getValue()); break;}
				// Unexpected order in this state
				throw new RuntimeException("Unexpected command "+orderString+" in state "+getExecutionState());

			case "holding":
				if (order.equals(ExecutionOrder.STOP))     {this.setExecutionState(ExecutionState.STOPPING.getValue()); break;}
				if (order.equals(ExecutionOrder.ABORT))    {this.setExecutionState(ExecutionState.ABORTING.getValue()); break;}
				// Unexpected order in this state
				throw new RuntimeException("Unexpected command "+orderString+" in state "+getExecutionState());

			case "held":
				if (order.equals(ExecutionOrder.UNHOLD)) {this.setExecutionState(ExecutionState.UNHOLDING.getValue()); break;}
				if (order.equals(ExecutionOrder.STOP))   {this.setExecutionState(ExecutionState.STOPPING.getValue()); break;}
				if (order.equals(ExecutionOrder.ABORT))  {this.setExecutionState(ExecutionState.ABORTING.getValue()); break;}
				// Unexpected order in this state
				throw new RuntimeException("Unexpected command "+orderString+" in state "+getExecutionState());
				
			case "unholding":
				if (order.equals(ExecutionOrder.STOP))     {this.setExecutionState(ExecutionState.STOPPING.getValue()); break;}
				if (order.equals(ExecutionOrder.ABORT))    {this.setExecutionState(ExecutionState.ABORTING.getValue()); break;}
				// Unexpected order in this state
				throw new RuntimeException("Unexpected command "+orderString+" in state "+getExecutionState());

			case "suspending":
				if (order.equals(ExecutionOrder.STOP))     {this.setExecutionState(ExecutionState.STOPPING.getValue()); break;}
				if (order.equals(ExecutionOrder.ABORT))    {this.setExecutionState(ExecutionState.ABORTING.getValue()); break;}
				// Unexpected order in this state
				throw new RuntimeException("Unexpected command "+orderString+" in state "+getExecutionState());

			case "suspended":
				if (order.equals(ExecutionOrder.UNSUSPEND)) {this.setExecutionState(ExecutionState.UNSUSPENDING.getValue()); break;}
				if (order.equals(ExecutionOrder.STOP))      {this.setExecutionState(ExecutionState.STOPPING.getValue()); break;}
				if (order.equals(ExecutionOrder.ABORT))     {this.setExecutionState(ExecutionState.ABORTING.getValue()); break;}
				// Unexpected order in this state
				throw new RuntimeException("Unexpected command "+orderString+" in state "+getExecutionState());

			case "unsuspending":
				if (order.equals(ExecutionOrder.STOP))     {this.setExecutionState(ExecutionState.STOPPING.getValue()); break;}
				if (order.equals(ExecutionOrder.ABORT))    {this.setExecutionState(ExecutionState.ABORTING.getValue()); break;}
				// Unexpected order in this state
				throw new RuntimeException("Unexpected command "+orderString+" in state "+getExecutionState());

			case "stopping":
				if (order.equals(ExecutionOrder.ABORT))    {this.setExecutionState(ExecutionState.ABORTING.getValue()); break;}
				// Unexpected order in this state
				throw new RuntimeException("Unexpected command "+orderString+" in state "+getExecutionState());

			case "stopped":
				if (order.equals(ExecutionOrder.RESET)) {this.setExecutionState(ExecutionState.RESETTING.getValue()); break;}
				if (order.equals(ExecutionOrder.ABORT)) {this.setExecutionState(ExecutionState.ABORTING.getValue()); break;}
				// Unexpected order in this state
				throw new RuntimeException("Unexpected command "+orderString+" in state "+getExecutionState());

			case "aborted":
				if (order.equals(ExecutionOrder.CLEAR)) {this.setExecutionState(ExecutionState.CLEARING.getValue()); break;}
				// Unexpected order in this state
				throw new RuntimeException("Unexpected command "+orderString+" in state "+getExecutionState());

			case "clearing":
				if (order.equals(ExecutionOrder.ABORT))    {this.setExecutionState(ExecutionState.ABORTING.getValue()); break;}
				// Unexpected order in this state
				throw new RuntimeException("Unexpected command "+orderString+" in state "+getExecutionState());

			// Received order in unexpected state
			default:
				// Indicate error
				throw new RuntimeException("Unexpected order "+orderString+" in state "+getExecutionState());
		}
	}
	
	
	/**
	 * Finish current execution state (execute 'SC' order). This only works in transition states
	 */
	public void finishState() {
		// Check if state complete message leads to valid state in current state
		switch(getExecutionState().toLowerCase()) {
			// Process state changes
			case "starting":     this.setExecutionState(ExecutionState.EXECUTE.getValue()); break;
			case "execute":      this.setExecutionState(ExecutionState.COMPLETING.getValue()); break;
			case "completing":   this.setExecutionState(ExecutionState.COMPLETE.getValue()); break;
			case "resetting":    this.setExecutionState(ExecutionState.IDLE.getValue()); break;
			case "holding":      this.setExecutionState(ExecutionState.HELD.getValue()); break;
			case "unholding":    this.setExecutionState(ExecutionState.EXECUTE.getValue()); break;
			case "suspending":   this.setExecutionState(ExecutionState.SUSPENDED.getValue()); break;
			case "unsuspending": this.setExecutionState(ExecutionState.EXECUTE.getValue()); break;
			case "stopping":     this.setExecutionState(ExecutionState.STOPPED.getValue()); break;
			case "stopped":      this.setExecutionState(ExecutionState.IDLE.getValue()); break;
			case "aborting":     this.setExecutionState(ExecutionState.ABORTED.getValue()); break;
			case "clearing":     this.setExecutionState(ExecutionState.STOPPED.getValue()); break;

			// Received order in unexpected state
			default:
				// Indicate error
				throw new RuntimeException("Unexpected state complete order in state "+getExecutionState());
		}
	}
		
	
	/**
	 * Get order list
	 */
	@SuppressWarnings("unchecked")
	public List<String> getOrderList() {
		// Get map entry
		return (List<String>) get("orderList");
	}
	
	
	/**
	 * Add order to order list
	 */
	@SuppressWarnings("unchecked")
	public void addOrder(String newOrder) {
		// Get map entry
		((List<String>) get("orderList")).add(newOrder);		
	}
	
	
	/**
	 * Clear order list
	 */
	@SuppressWarnings("unchecked")
	public void clearOrder() {
		// Get map entry
		((List<String>) get("orderList")).clear();		
	}
	
	
	/**
	 * Get occupation state
	 */
	public OccupationState getOccupationState() {
		// Return occupation state
		return OccupationState.byValue((Integer) status.get("occupationState"));
	}
	
	
	/**
	 * Set occupation state
	 */
	public void setOccupationState(OccupationState occSt) {
		// Update occupation state
		status.put("occupationState", occSt.getValue());
	}
	
	
	/**
	 * Get occupier ID
	 */
	public String getOccupierID() {
		// If occupier is not set, a null pointer Exception will be thrown when invoking toString(). Return an empty string in this case (=no occupier)
		try {return status.get("occupier").toString();} catch (NullPointerException e) {return "";}
	}
	
	
	/**
	 * Set occupier ID
	 */
	public void setOccupierID(String occId) {
		status.put("occupier", occId);
	}
	

	/**
	 * Get last occupier ID
	 */
	public String getLastOccupierID() {
		// If member is not set, a null pointer Exception will be thrown when invoking toString(). Return an empty string in this case (=no occupier)
		try {return status.get("lastOccupier").toString();} catch (NullPointerException e) {return "";}
	}
	
	
	/**
	 * Set last occupier ID
	 */
	public void setLastOccupierID(String occId) {
		status.put("lastOccupier", occId);
	}
	
	
	/**
	 * Get execution mode
	 */
	public ExecutionMode getExecutionMode() {
		// Return execution mode
		return ExecutionMode.byValue((Integer) status.get("exMode"));
	}
	
	
	/**
	 * Set execution mode
	 */
	public void setExecutionMode(ExecutionMode exMode) {
		// Return execution mode
		status.put("exMode", exMode.getValue());
	}
	

	/**
	 * Get execution state
	 */
	public String getExecutionState() {
		// If member is not set, a null pointer Exception will be thrown when invoking toString(). Return an empty string in this case (=no occupier)
		try {return status.get("exState").toString();} catch (NullPointerException e) {return "";}
	}
	
	
	/**
	 * Set execution state
	 */
	public void setExecutionState(String newSt) {
		System.out.println("Comp: change to:"+newSt);
		// Change execution state
		status.put("exState", newSt);		
	}
	
	
	/**
	 * Get operation mode
	 */
	public String getOperationMode() {
		// If member is not set, a null pointer Exception will be thrown when invoking toString(). Return an empty string in this case (=no occupier)
		try {return status.get("opMode").toString();} catch (NullPointerException e) {return "";}
	}
	
	
	/**
	 * Set operation mode
	 */
	public void setOperationMode(String opMode) {
		// Change operation mode
		status.put("opMode", opMode);		
	}
	
	
	/**
	 * Get work state
	 */
	public String getWorkState() {
		// If member is not set, a null pointer Exception will be thrown when invoking toString(). Return an empty string in this case (=no occupier)
		try {return status.get("workState").toString();} catch (NullPointerException e) {return "";}
	}
	

	/**
	 * Set work state
	 */
	public void setWorkState(String workState) {
		// Change work state
		status.put("workState", workState);		
	}
	

	/**
	 * Get error state
	 */
	public String getErrorState() {
		// If member is not set, a null pointer Exception will be thrown when invoking toString(). Return an empty string in this case (=no occupier)
		try {return status.get("errorState").toString();} catch (NullPointerException e) {return "";}
	}
	
	
	/**
	 * Set error state
	 */
	public void setErrorState(String errorState) {
		// Change error state
		status.put("errorState", errorState);		
	}
	
	
	/**
	 * Get last error state
	 */
	public String getLastErrorState() {
		// If member is not set, a null pointer Exception will be thrown when invoking toString(). Return an empty string in this case (=no occupier)
		try {return status.get("prevError").toString();} catch (NullPointerException e) {return "";}
	}
	
	
	/**
	 * Set last error state
	 */
	public void setLastErrorState(String lastErrorState) {
		// Change last error state
		status.put("prevError", lastErrorState);		
	}
	
	

	/**
	 * Get last command
	 */
	public String getCommand() {
		// If member is not set, a null pointer Exception will be thrown when invoking toString(). Return an empty string in this case (=no occupier)
		try {return get("cmd").toString();} catch (NullPointerException e) {return "";}
	}
	
	
	/**
	 * Set command
	 */
	public void setCommand(String cmd) {
		// Change last command
		put("cmd", cmd);		
	}
	
	
	/**
	 * Get local overwrite variable
	 */
	public String getLocalOverwrite() {
		// If member is not set, a null pointer Exception will be thrown when invoking toString(). Return an empty string in this case (=no occupier)
		try {return get("localOverwrite").toString();} catch (NullPointerException e) {return "";}
	}
	
	
	/**
	 * Set local overwrite variable
	 */
	public void setLocalOverwrite(String cmd) {
		// Change local overwrite command
		put("localOverwrite", cmd);		
	}


	/**
	 * Get local overwrite free variable
	 */
	public String getLocalOverwriteFree() {
		// If member is not set, a null pointer Exception will be thrown when invoking toString(). Return an empty string in this case (=no occupier)
		try {return get("localOverwriteFree").toString();} catch (NullPointerException e) {return "";}
	}
	
	
	/**
	 * Set local overwrite free variable
	 */
	public void setLocalOverwriteFree(String cmd) {
		// Change local overwrite free command
		put("localOverwriteFree", cmd);		
	}
}


