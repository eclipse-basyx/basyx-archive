package org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property;

import org.eclipse.basyx.aas.api.resources.IProperty;
import org.eclipse.basyx.aas.api.resources.PropertyType;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.qualifiable.Qualifier;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.DataElement;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property.valuetypedef.PropertyValueTypeDef;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property.valuetypedef.PropertyValueTypeDefHelper;

/**
 * Property class
 * 
 * @author kuhn, schnicke
 *
 */
public class Property extends DataElement implements IProperty {

	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public Property() {
		// Put attributes
		put("value", null);
		put("valueId", null);
	}

	/**
	 * Constructor that creates a property with data
	 * 
	 * @param value
	 *            the value of the property instance <b>!! Is defined in standard as
	 *            String, but does not make sense in this context !!</b>
	 * @param valueType
	 *            type of the value TODO: Macht String sinn?
	 */
	public Property(Object value) {
		// Put attributes
		put("valueId", null);
		setValue(value);
	}

	public void setValue(Object value) {
		put("value", value);
		put("valueType", PropertyValueTypeDefHelper.fromObject(value).toString());

	}

	public void setSemantics(String value) {
		put("semanticId", value);
	}

	public String getSemantics() {
		return (String) get("semanticId");
	}

	public void setQualifier(Qualifier qualifier) {
		put("qualifier", qualifier);
	}

	@Override
	public PropertyType getPropertyType() {
		PropertyValueTypeDef type = PropertyValueTypeDefHelper.fromName((String) get("valueType"));
		if (type == PropertyValueTypeDef.Collection) {
			return PropertyType.Collection;
		} else if (type == PropertyValueTypeDef.Map) {
			return PropertyType.Map;
		} else {
			return PropertyType.Single;
		}
	}

	@Override
	public String getId() {
		return (String) get("idShort");
	}

	@Override
	public void setId(String id) {
		put("idShort", id);
	}

}
