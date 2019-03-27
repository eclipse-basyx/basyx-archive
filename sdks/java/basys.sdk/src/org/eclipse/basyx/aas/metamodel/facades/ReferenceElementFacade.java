package org.eclipse.basyx.aas.metamodel.facades;

import java.util.Map;

import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.IReferenceElement;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.ReferenceElement;
/**
 * Facade providing access to a map containing the ReferenceElement structure
 * @author rajashek
 *
 */
public class ReferenceElementFacade implements IReferenceElement,org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.property.IReferenceElement{
	private Map<String, Object> map;
	public ReferenceElementFacade(Map<String, Object> map) {
		super();
		this.map = map;
	}

	@Override
	public void setValue(IReference ref) {
		map.put(ReferenceElement.VALUE, ref);
		
	}

	@Override
	public IReference getValue() {
		return (IReference)map.get(ReferenceElement.VALUE);
	}

}
