package org.eclipse.basyx.components;

/**
 * Common interfaces for all components allowing starting/stopping the component
 * 
 * @author schnicke
 *
 */
public interface IComponent {

	/**
	 * Starts the component
	 */
	public void startComponent();

	/**
	 * Shuts down the component
	 */
	public void stopComponent();
}
