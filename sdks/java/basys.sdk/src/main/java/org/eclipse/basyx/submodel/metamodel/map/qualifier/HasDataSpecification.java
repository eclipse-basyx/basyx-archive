package org.eclipse.basyx.submodel.metamodel.map.qualifier;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

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
		put(HASDATASPECIFICATION, new HashSet<Reference>());
	}

	public HasDataSpecification(Collection<IReference> ref) {
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

	@Override
	public Collection<IReference> getDataSpecificationReferences() {
		return ReferenceHelper.transform(get(HasDataSpecification.HASDATASPECIFICATION));
	}

	public void setDataSpecificationReferences(Collection<IReference> ref) {
		put(HasDataSpecification.HASDATASPECIFICATION, ref);
	}

}
