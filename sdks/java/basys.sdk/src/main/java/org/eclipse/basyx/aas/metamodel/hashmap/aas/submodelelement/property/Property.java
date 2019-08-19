package org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property;

import org.eclipse.basyx.aas.api.resources.IProperty;
import org.eclipse.basyx.aas.api.resources.PropertyType;
import org.eclipse.basyx.aas.metamodel.facades.PropertyFacade;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.HasSemantics;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Referable;
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
	public static final String VALUE = "value";
	public static final String VALUEID = "valueId";
	public static final String VALUETYPE = "valueType";

	/**
	 * Constructor
	 */
	public Property() {
		// Put attributes
		put(Property.VALUE, null);
		put(Property.VALUEID, null);
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
		put(Property.VALUEID, null);
		setValue(value);
	}
	
	public Property(Object value, Referable referable, String semanticId, Qualifier qualifier) {
		this(value);
		putAll(referable);
		put(HasSemantics.SEMANTICID, value);
		putAll(qualifier);
	}

	@Override
	public void setValue(Object value) {
		put(Property.VALUE, value);
		put(Property.VALUETYPE, PropertyValueTypeDefHelper.fromObject(value));

	}

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

	@Override
	public String getId() {
	return new PropertyFacade(this).getId();
	}

	@Override
	public void setId(String id) {
		put(Referable.IDSHORT, id);
	}

	@Override
	public Object getValue() {
		return new PropertyFacade(this).getValue();
	}

	@Override
	public void setValueId(Object obj) {
		 new PropertyFacade(this).setValueId(obj);
		
	}

	@Override
	public Object getValueId() {
		return new PropertyFacade(this).getValueId();
	}


}
