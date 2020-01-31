package org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.property.IProperty;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.property.PropertyType;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.DataElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetypedef.PropertyValueTypeDef;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetypedef.PropertyValueTypeDefHelper;

public abstract class AbstractProperty extends DataElement implements IProperty {
	public AbstractProperty() {}

	@Override
	public PropertyType getPropertyType() {
		PropertyValueTypeDef type = PropertyValueTypeDefHelper.fromName((String) get(Property.VALUETYPE));
		if (type == PropertyValueTypeDef.Collection) {
			return PropertyType.Collection;
		} else if (type == PropertyValueTypeDef.Map) {
			return PropertyType.Map;
		} else {
			return PropertyType.Single;
		}
	}

	public void setValueId(String obj) {
		put(Property.VALUEID, obj);
	}

	@Override
	public String getValueId() {
		return (String) get(Property.VALUEID);
	}

}
