package org.eclipse.basyx.aas.api.metamodel.aas.parts;

import java.util.Set;

import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IReferable;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;

/**
 * Interface for ConceptDictionary
 * 
 * @author rajashek
 *
*/

public interface IConceptDictionary extends IReferable {
	public Set<IReference> getConceptDescription();
}
