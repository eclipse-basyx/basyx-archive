package org.eclipse.basyx.aas.metamodel.facades;

import java.util.HashSet;
import java.util.Map;

import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IHasDataSpecification;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.HasDataSpecification;


/**
 * Facade providing access to a map containing the DataSpecification structure
 *
 * @author rajashek
 *
 */

public class HasDataSpecificationFacade implements IHasDataSpecification {
	
	

	private Map<String, Object> map;

	public HasDataSpecificationFacade(Map<String, Object> map) {
		this.map = map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashSet<IReference> getDataSpecificationReferences() {
		return (HashSet<IReference>) map.get(HasDataSpecification.HASDATASPECIFICATION);
	}

	@Override
	public void setDataSpecificationReferences(HashSet<IReference> ref) {
		map.put(HasDataSpecification.HASDATASPECIFICATION, ref);
		
	}

}
