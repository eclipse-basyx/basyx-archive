package org.eclipse.basyx.submodel.metamodel.map.submodelelement.property;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.property.ISingleProperty;
import org.eclipse.basyx.submodel.metamodel.map.modeltype.ModelType;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.HasSemantics;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.qualifiable.Qualifier;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.property.valuetypedef.PropertyValueTypeDef;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.property.valuetypedef.PropertyValueTypeDefHelper;

/**
 * Property class
 * 
 * @author kuhn, schnicke
 *
 */
public class SingleProperty extends AbstractProperty implements ISingleProperty {

	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;
	public static final String VALUE = "value";
	public static final String VALUEID = "valueId";
	public static final String VALUETYPE = "valueType";
	public static final String MODELTYPE = "Property";

	/**
	 * Constructor
	 */
	public SingleProperty() {
		// Add model type
		putAll(new ModelType(MODELTYPE));

		// Put attributes
		put(SingleProperty.VALUE, null);
		put(SingleProperty.VALUEID, null);
	}

	public static SingleProperty createAsFacade(Map<String, Object> obj) {
		SingleProperty facade = new SingleProperty();
		facade.putAll(obj);
		return facade;
	}

	/**
	 * Constructor that creates a property with data
	 * 
	 * @param value
	 *            the value of the property instance <b>!! Is defined in standard as
	 *            String, but does not make sense in this context !!</b>
	 */
	public SingleProperty(Object value) {
		// Put attributes
		put(SingleProperty.VALUEID, null);
		set(value);
	}

	public SingleProperty(Object value, Referable referable, String semanticId, Qualifier qualifier) {
		this(value);
		putAll(referable);
		put(HasSemantics.SEMANTICID, value);
		putAll(qualifier);
	}

	/**
	 * Overrides the orignal value type that has been determined by inspecting the given value.
	 * You can use PropertyValueTypeDefHelper
	 * 
	 * @param type
	 *            manually determined type of the value
	 */
	public void setValueType(PropertyValueTypeDef type) {
		put(SingleProperty.VALUETYPE, PropertyValueTypeDefHelper.getWrapper(type));
	}

	@Override
	public void set(Object value) {
		put(SingleProperty.VALUE, value);
		put(SingleProperty.VALUETYPE, PropertyValueTypeDefHelper.fromObject(value));

	}

	@Override
	public Object get() {
		return get(SingleProperty.VALUE);
	}

	@Override
	public String getValueType() {
		return (String) get(SingleProperty.VALUETYPE);
	}
}
