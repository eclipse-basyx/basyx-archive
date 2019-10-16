package org.eclipse.basyx.submodel.metamodel.api.qualifier;

import java.util.Set;

import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;

/**
 * Interface for HasDataSpecification
 *
 * @author rajashek
 *
*/

public interface IHasDataSpecification {
	public Set<IReference> getDataSpecificationReferences();
}
