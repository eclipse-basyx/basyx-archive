package org.eclipse.basyx.aas.impl.resources.basic;

import org.eclipse.basyx.aas.api.resources.basic.IElement;



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
	 * Asset Kind
	 */
	protected AssetKind assetKind;
	
	
	/**
	 * Element name 
	 */
	protected String name;

		
	/**
	 * Return parent element of this element. Every element has 0 or 1 parents. 
	 */
	public BaseElement getParent() {
		return parent;
	}
	
	
	/**
	 * Relocate element to a new parent
	 */
	public void setParent(BaseElement parent) {
		this.parent = parent;
	}
	
	
	/**
	 * Return unique element ID of this element
	 */
	public String getId() {
		return id;
	}
	
	
	/**
	 * Change unique element ID of this element
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	
	/**
	 * Return asset kind for this element
	 */
	public AssetKind getAssetKind() {
		return assetKind;
	}

	
	/**
	 * Update asset kind of this element
	 */
	public void setAssetKind(AssetKind kind) {
		this.assetKind = kind;
	}

	
	/**
	 * Return element name
	 */
	public String getName() {
		return name;
	}

	
	/**
	 * Change element name
	 */
	public void setName(String name) {
		this.name = name;
	}

}

