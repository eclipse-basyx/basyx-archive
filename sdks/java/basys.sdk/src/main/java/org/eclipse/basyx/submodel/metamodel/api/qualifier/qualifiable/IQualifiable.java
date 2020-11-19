package org.eclipse.basyx.submodel.metamodel.api.qualifier.qualifiable;

import java.util.Collection;

/**
 * Interface for Qualifiable
 * @author rajashek
 *
*/

public interface IQualifiable {
	public Collection<IConstraint> getQualifiers();
}
