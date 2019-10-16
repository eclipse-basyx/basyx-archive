package org.eclipse.basyx.submodel.metamodel.api.qualifier.qualifiable;

import java.util.Set;

/**
 * Interface for Qualifiable
 * @author rajashek
 *
*/

public interface IQualifiable {
	public Set<IConstraint> getQualifier();
}
