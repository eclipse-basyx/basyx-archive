package org.eclipse.basyx.submodel.metamodel.api.submodelelement;

import java.util.Collection;
import java.util.Map;

/**
 * Interface for SubmodelElementCollection
 * @author rajashek
 *
*/
public interface ISubmodelElementCollection extends ISubmodelElement {

	public void setValue(Collection<ISubmodelElement> value);
	
	public Collection<ISubmodelElement> getValue();
	
	public void setOrdered(boolean value);
	
	public boolean isOrdered();
	
	public void setAllowDuplicates(boolean value);
	
	public boolean isAllowDuplicates();
	
	public void setElements(Map<String, ISubmodelElement> value);
	
	public Map<String, ISubmodelElement> getElements();
}