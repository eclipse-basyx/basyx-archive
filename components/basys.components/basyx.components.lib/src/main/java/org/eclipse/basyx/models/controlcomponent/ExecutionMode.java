package org.eclipse.basyx.models.controlcomponent;


/**
 * Execution mode enum
 * 
 * @author kuhn
 *
 */
public enum ExecutionMode {
	// Enumeration constants
	AUTO(1), SEMIAUTO(2), MANUAL(3), RESERVED(4), SIMULATION(5);

	
	
	/**
	 * Get OccupationState by its value
	 */
	public static ExecutionMode byValue(int value) {
		// Switch by requested value
		switch (value) {
			case 1: return AUTO;
			case 2: return SEMIAUTO;
			case 3: return MANUAL;
			case 4: return RESERVED;
			case 5: return SIMULATION;
		}
		
		// Indicate error
		throw new RuntimeException("Unknown value requested");
	}

	
	
	
	/**
	 * Enumeration item value
	 */
	protected int value = -1;
	
	
	
	/**
	 * Constructor
	 */
	private ExecutionMode(int val) {
		this.value = val;
	}
	
	
	/**
	 * Get enumeration value
	 */
	public int getValue() {
		return value;
	}
}
