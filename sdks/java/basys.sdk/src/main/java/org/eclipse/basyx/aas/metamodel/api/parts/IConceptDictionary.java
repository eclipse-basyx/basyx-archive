package org.eclipse.basyx.aas.metamodel.api.parts;

import java.util.Set;

import org.eclipse.basyx.submodel.metamodel.api.qualifier.IReferable;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;

/**
 * Interface for ConceptDictionary
 * 
 * @author rajashek
 *
*/

public interface IConceptDictionary extends IReferable {
	public Set<IReference> getConceptDescription();
}
