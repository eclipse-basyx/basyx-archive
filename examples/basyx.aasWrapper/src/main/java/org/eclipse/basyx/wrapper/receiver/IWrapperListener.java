package org.eclipse.basyx.wrapper.receiver;

/**
 * A listener for values that have been generated by the wrapper
 * 
 * @author espen
 *
 */
public interface IWrapperListener {
	/**
	 * Informs the listener about a new value that has been generated
	 * 
	 * @param result
	 */
	public void newValue(String propId, PropertyResult result);
}
