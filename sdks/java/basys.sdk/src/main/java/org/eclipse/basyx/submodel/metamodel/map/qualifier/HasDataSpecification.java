package org.eclipse.basyx.submodel.metamodel.map.qualifier;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.basyx.submodel.metamodel.api.qualifier.IHasDataSpecification;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.HasDataSpecificationFacade;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;

/**
 * HasDataSpecification class
 * 
 * @author elsheikh, schnicke
 *
 */
public class HasDataSpecification extends HashMap<String, Object> implements IHasDataSpecification {

	public static final String HASDATASPECIFICATION = "hasDataSpecification";

	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

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

	@Override
	public Set<IReference> getDataSpecificationReferences() {
		return new HasDataSpecificationFacade(this).getDataSpecificationReferences();
	}

	public void setDataSpecificationReferences(Set<IReference> ref) {
		new HasDataSpecificationFacade(this).setDataSpecificationReferences(ref);
	}
}
