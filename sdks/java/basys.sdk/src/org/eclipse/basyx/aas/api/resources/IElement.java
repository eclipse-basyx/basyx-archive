package org.eclipse.basyx.aas.api.resources;

import org.eclipse.basyx.aas.impl.resources.basic.BaseElement;

/**
 * Base interface for all AAS elements
 * 
 * @author kuhn
 *
 */
public interface IElement {

	/**
	 * Return the unique ID that identifies an VAB element
	 * 
	 * @return unique ID
	 */
	public String getId();

	/**
	 * Set the ID of an element
	 * 
	 * @param id
	 *            New/updated element id
	 */
	public void setId(String id);

	/**
	 * Get IElement properties
	 * 
	 * @return
	 */

	// public Map<String, IProperty> getProperties();

	/**
	 * Get IElement operations
	 * 
	 * @return IElement operations
	 */
	// public Map<String, IOperation> getOperations();

	/**
	 * Set parent element
	 */
	public void setParent(BaseElement parent);

	/**
	 * Get parent element
	 */
	public BaseElement getParent();

}
