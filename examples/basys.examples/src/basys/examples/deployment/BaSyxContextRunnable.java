package basys.examples.deployment;



/**
 * Runnable in a BaSyx context
 * 
 * @author kuhn
 *
 */
public interface BaSyxContextRunnable {

	
	/**
	 * Start the runnable
	 */
	public void start();
	
	
	/**
	 * Stop the runnable
	 */
	public void stop();
	
	
	/**
	 * Change the runnable name
	 */
	public BaSyxContextRunnable setName(String newName);
	
	
	/**
	 * Get runnable name
	 */
	public String getName();
}
