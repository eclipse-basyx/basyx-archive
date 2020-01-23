package org.eclipse.basyx.submodel.metamodel.api.parts;

import java.util.Set;

import org.eclipse.basyx.submodel.metamodel.api.qualifier.IHasDataSpecification;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.IIdentifiable;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;

/**
 * Interface for ConceptDescription. Contains a semantic identifier and can be referenced by elements that have
 * semantics.
 * 
 * @author rajashek
 *
 */
public interface IConceptDescription extends IHasDataSpecification, IIdentifiable {
	public Set<IReference> getIsCaseOf();
}
