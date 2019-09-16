package org.eclipse.basyx.aas.api.metamodel.aas.qualifier.qualifiable;

import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IHasSemantics;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;

/**
 * Interface for Qualifier
 * 
 * @author rajashek
 *
 */

public interface IQualifier extends IHasSemantics {
	public String getQualifierType();

	public Object getQualifierValue();

	public IReference getQualifierValueId();
}
