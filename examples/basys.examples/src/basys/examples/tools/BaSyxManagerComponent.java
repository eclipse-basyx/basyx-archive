package basys.examples.tools;

import basys.examples.deployment.BaSyxContextRunnable;

/**
 * Base class for stand alone device managers that connect legacy devices with BaSyx. This component runs
 * for example in interface devices.
 * 
 * @author kuhn
 *
 */
public abstract class BaSyxManagerComponent implements BaSyxContextRunnable {

	
	/**
	 * Flag that indicates the request for ending the execution
	 */
	protected boolean endExecution = false;
	
	
	/**
	 * Store device name
	 */
	protected String deviceName = null;



	
	
	
	/**
	 * Initialization method. Perform one-time tasks, e.g. initialization and registration. 
	 */
	public abstract void initialize();
	
	
	/**
	 * Indicate change of end execution flag
	 */
	protected void onEndExecutionChanged(boolean endExecutionFlag) {
		// Default implementation does nothing
	}
	
	
	/**
	 * End the execution of this BaSyx manager
	 */
	public void endExecution() {
		// Change flag
		endExecution = true;
		
		// Signal flag change
		onEndExecutionChanged(endExecution);
	}
	
	
	/**
	 * Run this manager
	 */
	@Override
	public void start() {
		// Initialize this BaSyx manager
		initialize();
	}
	
	
	/**
	 * Stop this manager
	 */
	@Override
	public void stop() {
		// End execution of this BaSyx manager
		endExecution();
	}
	
	
	/**
	 * Change the runnable name
	 */
	@Override
	public BaSyxContextRunnable setName(String newName) {
		// Set name
		deviceName = newName;
		
		// Return 'this' reference to enable chaining
		return this;
	}
	
	
	/**
	 * Get runnable name
	 */
	@Override
	public String getName() {
		return deviceName;
	}
}

