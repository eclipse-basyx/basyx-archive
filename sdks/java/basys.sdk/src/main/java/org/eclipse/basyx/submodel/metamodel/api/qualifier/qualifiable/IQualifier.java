package org.eclipse.basyx.submodel.metamodel.api.qualifier.qualifiable;

import org.eclipse.basyx.submodel.metamodel.api.qualifier.IHasSemantics;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetypedef.PropertyValueTypeDef;

/**
 * Interface for Qualifier
 * 
 * @author rajashek
 *
 */

public interface IQualifier extends IHasSemantics, IConstraint {
	public String getType();

	public Object getValue();

	public IReference getValueId();
	
	public PropertyValueTypeDef  getValueType();
}
