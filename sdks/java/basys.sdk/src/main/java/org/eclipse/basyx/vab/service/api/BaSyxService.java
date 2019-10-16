package org.eclipse.basyx.vab.service.api;



/**
 * Runnable BaSyx service
 * 
 * @author kuhn
 *
 */
public interface BaSyxService {

	
	/**
	 * Start the runnable
	 */
	public void start();
	
	
	/**
	 * Stop the runnable
	 */
	public void stop();
	
	
	/**
	 * Wait for end of runnable
	 */
	public void waitFor(); 
	
	
	/**
	 * Change the runnable name
	 */
	public BaSyxService setName(String newName);
	
	
	/**
	 * Get runnable name
	 */
	public String getName();
	
	
	/**
	 * Indicate if this service has ended
	 */
	public boolean hasEnded();
}
