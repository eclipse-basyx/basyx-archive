package org.eclipse.basyx.aas.impl.metamodel.facades;

import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IHasDataSpecification;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.qualifier.HasDataSpecification;

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
	public Set<IReference> getDataSpecificationReferences() {
		return (Set<IReference>) map.get(HasDataSpecification.HASDATASPECIFICATION);
	}

	public void setDataSpecificationReferences(Set<IReference> ref) {
		map.put(HasDataSpecification.HASDATASPECIFICATION, ref);
	}

}
