package org.eclipse.basyx.aas.api.resources.basic;

import java.util.Map;




/**
 * Interface API for AAS elements that contain other AAS elements
 * 
 * @author kuhn
 *
 */
public interface IElementContainer extends IElement {

	
	/**
	 * Return a contained element
	 */
	public IElement getElement(String name);
	
	
	/**
	 * Return contained elements
	 * 
	 * @return contained elements
	 */
	public Map<String, IElement> getElements();
}
