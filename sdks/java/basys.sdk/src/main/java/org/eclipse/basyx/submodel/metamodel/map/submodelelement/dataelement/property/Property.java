package org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.parts.IConceptDescription;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.property.ISingleProperty;
import org.eclipse.basyx.submodel.metamodel.map.modeltype.ModelType;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.HasSemantics;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.qualifiable.Qualifier;
import org.eclipse.basyx.submodel.metamodel.map.reference.Key;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetypedef.PropertyValueTypeDef;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetypedef.PropertyValueTypeDefHelper;

/**
 * Property class
 * 
 * @author kuhn, schnicke
 *
 */
public class Property extends AbstractProperty implements ISingleProperty {
	public static final String VALUE = "value";
	public static final String VALUEID = "valueId";
	public static final String VALUETYPE = "valueType";
	public static final String MODELTYPE = "Property";

	/**
	 * Constructor
	 */
	public Property() {
		// Add model type
		putAll(new ModelType(MODELTYPE));

		// Put attributes
		put(Property.VALUE, null);
		put(Property.VALUEID, null);
	}

	/**
	 * Creates a Property object from a map
	 * 
	 * @param obj a Property object as raw map
	 * @return a Property object, that behaves like a facade for the given map
	 */
	public static Property createAsFacade(Map<String, Object> obj) {
		Property facade = new Property();
		facade.setMap(obj);
		return facade;
	}

	/**
	 * Constructor that creates a property with data
	 * 
	 * @param value
	 *            the value of the property instance <b>!! Is defined in standard as
	 *            String, but does not make sense in this context !!</b>
	 */
	public Property(Object value) {
		// Put attributes
		put(Property.VALUEID, null);
		set(value);
	}

	public Property(Object value, Referable referable, String semanticId, Qualifier qualifier) {
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
		put(Property.VALUETYPE, PropertyValueTypeDefHelper.getWrapper(type));
	}

	@Override
	public void set(Object value) {
		put(Property.VALUE, value);
		put(Property.VALUETYPE, PropertyValueTypeDefHelper.fromObject(value));

	}

	@Override
	public Object get() {
		return get(Property.VALUE);
	}

	@Override
	public String getValueType() {
		PropertyValueTypeDef def = PropertyValueTypeDefHelper.readTypeDef(get(Property.VALUETYPE));
		return def!=null ? def.toString() : "";
	}

	/**
	 * QoL method that allows adding a reference to a concept description to a
	 * property
	 * 
	 * @param description
	 *            the description to refer
	 */
	public void addConceptDescription(IConceptDescription description) {
		IIdentifier id = description.getIdentification();
		Key key = new Key(KeyElements.CONCEPTDESCRIPTION, true, id.getId(), id.getIdType());
		Reference ref = new Reference(key);
		setSemanticID(ref);
	}
}
