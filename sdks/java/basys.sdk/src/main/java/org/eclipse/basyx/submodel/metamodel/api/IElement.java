package org.eclipse.basyx.submodel.metamodel.api;


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
	public String getIdShort();

	/**
	 * Set the ID of an element
	 * 
	 * @param id
	 *            New/updated element id
	 */
	public void setIdShort(String id);

}
