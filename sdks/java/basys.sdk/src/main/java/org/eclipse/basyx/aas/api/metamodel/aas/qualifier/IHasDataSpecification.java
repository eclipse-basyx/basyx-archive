package org.eclipse.basyx.aas.api.metamodel.aas.qualifier;

import java.util.HashSet;

import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;

/**
 * Interface for HasDataSpecification
 *
 * @author rajashek
 *
*/

public interface IHasDataSpecification {
	public HashSet<IReference> getDataSpecificationReferences();
}
