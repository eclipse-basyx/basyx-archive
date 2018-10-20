package org.eclipse.basyx.aas.api.resources.basic;

import org.eclipse.basyx.aas.impl.resources.basic.AssetKind;
import org.eclipse.basyx.aas.impl.resources.basic.BaseElement;



/**
 * Base interface for all AAS elements
 *  
 * @author kuhn
 *
 */
public interface IElement {

	
	/**
	 * Return reference to parent element
	 * 
	 * @return Reference to parent element
	 */
	public BaseElement getParent();
	
	
	/**
	 * Relocate the element to a new parent
	 * 
	 * @param parent New parent element
	 */
	public void setParent(BaseElement parent);

	
	/**
	 * Return the unique ID that identifies an element
	 * 
	 * @return unique ID
	 */
	public String getId();

	
	/**
	 * Set the ID of an element
	 * 
	 * @param id New/updated element id
	 */
	public void setId(String id);

	
	/**
	 * Return asset kind (type/instance)
	 * 
	 * @return Asset kind
	 */
	public AssetKind getAssetKind();

	
	/**
	 * Set asset kind (type/instance)
	 * 
	 * @param kind Asset kind
	 */
	public void setAssetKind(AssetKind kind);

	
	
	/**
	 * Return human readable (informative) name of element
	 * 
	 * @return Element name
	 */
	public String getName();

	
	/**
	 * Set/update human readable (informative) name of element
	 * 
	 * @param name New element name
	 */
	public void setName(String name);



}

