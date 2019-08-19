package org.eclipse.basyx.aas.metamodel.facades;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.ISubmodelElement;
import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.ISubmodelElementCollection;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property.Property;

/**
 * Facade providing access to a map containing the SubmodelElementCollection structure
 * @author rajashek
 *
 */

public class SubmodelElementCollectionFacade implements ISubmodelElementCollection {
	private Map<String, Object> map;
	
	public SubmodelElementCollectionFacade(Map<String, Object> map) {
		super();
		this.map = map;
	}

	@Override
	public void setValue(ArrayList<?> value) {
	map.put(Property.VALUE, value);
		
	}

	@Override
	public ArrayList<?> getValue() {
		return (ArrayList<?>)map.get(Property.VALUE);
	}

	@Override
	public void setOrdered(boolean value) {
		map.put(SubmodelElementCollection.ORDERED, value);
		
	}

	@Override
	public boolean isOrdered() {
	return (boolean)map.get(SubmodelElementCollection.ORDERED);
	}

	@Override
	public void setAllowDuplicates(boolean value) {
		map.put(SubmodelElementCollection.ALLOWDUPLICATES, value);
		
	}

	@Override
	public boolean isAllowDuplicates() {
		return (boolean)map.get(SubmodelElementCollection.ALLOWDUPLICATES);
	}

	@Override
	public void setElements(HashMap<String, ISubmodelElement> value) {
		map.put(SubmodelElementCollection.ELEMENTS, value);		
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, ISubmodelElement> getElements() {
		return (HashMap<String, ISubmodelElement>)map.get(SubmodelElementCollection.ELEMENTS);
	}
	
	

}
