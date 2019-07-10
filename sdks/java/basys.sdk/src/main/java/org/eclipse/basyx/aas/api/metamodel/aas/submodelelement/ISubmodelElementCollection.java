package org.eclipse.basyx.aas.api.metamodel.aas.submodelelement;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Interface for SubmodelElementCollection
 * @author rajashek
 *
*/
public interface ISubmodelElementCollection {

	
	public void setValue(ArrayList<?> value);
	
	public ArrayList<?> getValue();
	
	public void setOrdered(boolean value);
	
	public boolean isOrdered();
	
	public void setAllowDuplicates(boolean value);
	
	public boolean isAllowDuplicates();
	
	public void setElements(HashMap<String, ISubmodelElement> value);
	
	public HashMap<String, ISubmodelElement> getElements();
}
