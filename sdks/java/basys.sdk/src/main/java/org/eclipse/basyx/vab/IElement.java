package org.eclipse.basyx.vab;


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

}
