package org.eclipse.basyx.submodel.metamodel.map.submodelelement.property;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.property.IProperty;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.property.PropertyType;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.DataElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.property.valuetypedef.PropertyValueTypeDef;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.property.valuetypedef.PropertyValueTypeDefHelper;

public abstract class AbstractProperty extends DataElement implements IProperty {
	private static final long serialVersionUID = 1724792579215822224L;

	@Override
	public PropertyType getPropertyType() {
		PropertyValueTypeDef type = PropertyValueTypeDefHelper.fromName((String) get(SingleProperty.VALUETYPE));
		if (type == PropertyValueTypeDef.Collection) {
			return PropertyType.Collection;
		} else if (type == PropertyValueTypeDef.Map) {
			return PropertyType.Map;
		} else {
			return PropertyType.Single;
		}
	}

	@Override
	public void setValueId(String obj) {
		put(SingleProperty.VALUEID, obj);

	}

	@Override
	public String getValueId() {
		return (String) get(SingleProperty.VALUEID);
	}

}
