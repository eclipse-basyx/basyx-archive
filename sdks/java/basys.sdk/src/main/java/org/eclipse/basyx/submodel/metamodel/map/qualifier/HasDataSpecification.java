package org.eclipse.basyx.submodel.metamodel.map.qualifier;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.submodel.metamodel.api.qualifier.IHasDataSpecification;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.reference.ReferenceHelper;
import org.eclipse.basyx.vab.model.VABModelMap;

/**
 * HasDataSpecification class
 * 
 * @author elsheikh, schnicke
 *
 */
public class HasDataSpecification extends VABModelMap<Object> implements IHasDataSpecification {

	public static final String HASDATASPECIFICATION = "hasDataSpecification";
	/**
	 * Constructor
	 */
	public HasDataSpecification() {
		// Default values
		put(HASDATASPECIFICATION, new HashSet<Reference>());
	}

	public HasDataSpecification(Set<IReference> ref) {
		// Default values
		put(HASDATASPECIFICATION, ref);
	}

	/**
	 * Creates a DataSpecificationIEC61360 object from a map
	 * 
	 * @param obj
	 *            a DataSpecificationIEC61360 object as raw map
	 * @return a DataSpecificationIEC61360 object, that behaves like a facade for
	 *         the given map
	 */
	public static HasDataSpecification createAsFacade(Map<String, Object> map) {
		if (map == null) {
			return null;
		}

		HasDataSpecification ret = new HasDataSpecification();
		ret.setMap(map);
		return ret;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<IReference> getDataSpecificationReferences() {
		Set<Map<String, Object>> set = (Set<Map<String, Object>>) get(HasDataSpecification.HASDATASPECIFICATION);
		return ReferenceHelper.transform(set);
	}

	public void setDataSpecificationReferences(Set<IReference> ref) {
		put(HasDataSpecification.HASDATASPECIFICATION, ref);
	}

}
