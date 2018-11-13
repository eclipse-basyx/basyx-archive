package org.eclipse.basyx.aas.impl.resources.basic;

import org.eclipse.basyx.aas.api.resources.IElement;

/**
 * Base element class - fields for all BaSys model elements
 * 
 * @author schoeffler, ziesche
 *
 */
public class BaseElement implements IElement {

	/**
	 * Parent element
	 */
	protected BaseElement parent;

	/**
	 * Element ID
	 */
	protected String id;

	/**
	 * Return parent element of this element. Every element has 0 or 1 parents.
	 */
	@Override
	public BaseElement getParent() {
		return parent;
	}

	/**
	 * Relocate element to a new parent
	 */
	@Override
	public void setParent(BaseElement parent) {
		this.parent = parent;
	}

	/**
	 * Return unique element ID of this element
	 */
	@Override
	public String getId() {
		return id;
	}

	/**
	 * Change unique element ID of this element
	 */
	@Override
	public void setId(String id) {
		this.id = id;
	}
}
