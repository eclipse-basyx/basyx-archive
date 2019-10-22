package org.eclipse.basyx.submodel.metamodel.facade.qualifier;

import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.submodel.metamodel.api.qualifier.IHasDataSpecification;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.facade.reference.ReferenceHelper;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.HasDataSpecification;

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
		Set<Map<String, Object>> set = (Set<Map<String, Object>>) map.get(HasDataSpecification.HASDATASPECIFICATION);
		return ReferenceHelper.transform(set);
	}

	public void setDataSpecificationReferences(Set<IReference> ref) {
		map.put(HasDataSpecification.HASDATASPECIFICATION, ref);
	}

}
