package org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation;

import org.eclipse.basyx.submodel.metamodel.api.qualifier.haskind.ModelingKind;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetypedef.PropertyValueTypeDef;

public class OperationHelper {
	public static Property createPropertyTemplate(PropertyValueTypeDef type) {
		Property prop = new Property();
		prop.setValueType(type);
		prop.setModelingKind(ModelingKind.TEMPLATE);
		return prop;
	}
}
