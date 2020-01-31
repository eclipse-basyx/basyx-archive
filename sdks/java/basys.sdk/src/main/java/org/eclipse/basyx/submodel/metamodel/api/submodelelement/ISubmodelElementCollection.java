package org.eclipse.basyx.submodel.metamodel.api.submodelelement;

import java.util.Collection;
import java.util.Map;

/**
 * A submodel element collection is a set or list of submodel elements.
 * 
 * @author rajashek, schnicke
 *
 */
public interface ISubmodelElementCollection extends ISubmodelElement {
	
	public Collection<ISubmodelElement> getValue();
	
	/**
	 * Gets if the collection is ordered or unordered
	 * 
	 * @return
	 */
	public boolean isOrdered();
	
	/**
	 * Gets if the collection allows duplicates
	 * 
	 * @return
	 */
	public boolean isAllowDuplicates();
	
	/**
	 * Gets the elements contained in the collection
	 * 
	 * @return
	 */
	public Map<String, ISubmodelElement> getElements();
}