package org.eclipse.basyx.aas.metamodel.facades;

import java.util.Map;

import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.IReferenceElement;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.reference.Reference;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.ReferenceElement;
/**
 * Facade providing access to a map containing the ReferenceElement structure
 * @author rajashek
 *
 */
public class ReferenceElementFacade implements IReferenceElement{
	private Map<String, Object> map;
	public ReferenceElementFacade(Map<String, Object> map) {
		super();
		this.map = map;
	}

	@Override
	public void setValue(Reference ref) {
		map.put(ReferenceElement.VALUE, ref);
		
	}

	@Override
	public Reference getValue() {
		return (Reference)map.get(ReferenceElement.VALUE);
	}

}
