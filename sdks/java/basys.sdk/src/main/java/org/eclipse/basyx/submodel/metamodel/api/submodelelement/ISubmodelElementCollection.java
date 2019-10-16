package org.eclipse.basyx.submodel.metamodel.api.submodelelement;

import java.util.List;
import java.util.Map;

/**
 * Interface for SubmodelElementCollection
 * @author rajashek
 *
*/
public interface ISubmodelElementCollection extends ISubmodelElement {

	public void setValue(List<Object> value);
	
	public List<Object> getValue();
	
	public void setOrdered(boolean value);
	
	public boolean isOrdered();
	
	public void setAllowDuplicates(boolean value);
	
	public boolean isAllowDuplicates();
	
	public void setElements(Map<String, ISubmodelElement> value);
	
	public Map<String, ISubmodelElement> getElements();
}
