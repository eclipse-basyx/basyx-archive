package org.eclipse.basyx.components.netcomm;




/**
 * Interface for TCP receiver components
 * 
 * @author kuhn
 *
 */
public interface NetworkReceiver {

	
	/**
	 * Received a string from network connection 
	 */
	public void onReceive(byte[] rxData);
}
