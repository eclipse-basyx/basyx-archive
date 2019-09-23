package org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.submodelelement.property;

import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.property.ISingleProperty;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.qualifier.HasSemantics;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.qualifier.Referable;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.qualifier.qualifiable.Qualifier;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.submodelelement.property.valuetypedef.PropertyValueTypeDefHelper;

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

	/**
	 * Constructor
	 */
	public SingleProperty() {
		// Put attributes
		put(SingleProperty.VALUE, null);
		put(SingleProperty.VALUEID, null);
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

	@Override
	public void set(Object value) {
		put(SingleProperty.VALUE, value);
		put(SingleProperty.VALUETYPE, PropertyValueTypeDefHelper.fromObject(value));

	}

	@Override
	public String getId() {
		return (String) get(Referable.IDSHORT);
	}

	@Override
	public void setId(String id) {
		put(Referable.IDSHORT, id);
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
