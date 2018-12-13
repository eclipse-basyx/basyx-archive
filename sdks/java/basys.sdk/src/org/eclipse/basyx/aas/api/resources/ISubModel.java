package org.eclipse.basyx.aas.api.resources;

import java.util.Map;

/**
 * Interface API for sub models
 * 
 * @author kuhn
 *
 */
public interface ISubModel extends IElement {

	/**
	 * Clone this sub model. Cloned sub models are no longer frozen
	 * 
	 * @return New sub model instance
	 */
	// public ISubModel cloneSubModel();

	/**
	 * Get sub model properties
	 * 
	 * @return Sub model properties
	 */
	public Map<String, IProperty> getProperties();

	/**
	 * Get sub model operations
	 * 
	 * @return Sub model operations
	 */
	public Map<String, IOperation> getOperations();

}
