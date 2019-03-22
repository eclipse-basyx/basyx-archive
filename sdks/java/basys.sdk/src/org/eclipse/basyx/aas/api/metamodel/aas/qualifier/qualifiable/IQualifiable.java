package org.eclipse.basyx.aas.api.metamodel.aas.qualifier.qualifiable;

import java.util.Set;

/**
 * Interface for Qualifiable
 * @author rajashek
 *
*/

public interface IQualifiable {
	
	public void setQualifier(Set<IConstraint> qualifiers);
	
	public Set<IConstraint> getQualifier();

}
