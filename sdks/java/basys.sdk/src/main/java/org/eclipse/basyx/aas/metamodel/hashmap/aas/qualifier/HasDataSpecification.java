package org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier;

import java.util.HashMap;
import java.util.HashSet;

import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IHasDataSpecification;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.metamodel.facades.HasDataSpecificationFacade;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.reference.Reference;

/**
 * HasDataSpecification class
 * 
 * @author elsheikh, schnicke
 *
 */
public class HasDataSpecification extends HashMap<String, Object> implements IHasDataSpecification {
	
	public static final String HASDATASPECIFICATION="hasDataSpecification";

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

	@Override
	public HashSet<IReference> getDataSpecificationReferences() {
		return new HasDataSpecificationFacade(this).getDataSpecificationReferences();
	}

	@Override
	public void setDataSpecificationReferences(HashSet<IReference> ref) {
		new HasDataSpecificationFacade(this).setDataSpecificationReferences(ref);
		
	}
}
