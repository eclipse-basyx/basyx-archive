package org.eclipse.basyx.submodel.metamodel.api.qualifier.qualifiable;

import org.eclipse.basyx.submodel.metamodel.api.qualifier.IHasSemantics;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;

/**
 * Interface for Qualifier
 * 
 * @author rajashek
 *
 */

public interface IQualifier extends IHasSemantics, IConstraint {
	public String getType();

	public String getValue();

	public IReference getValueId();
	
	public String getValueType();
}
