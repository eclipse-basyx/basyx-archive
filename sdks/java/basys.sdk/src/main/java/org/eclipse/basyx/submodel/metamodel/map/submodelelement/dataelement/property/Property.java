package org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.parts.IConceptDescription;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IProperty;
import org.eclipse.basyx.submodel.metamodel.map.modeltype.ModelType;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.HasSemantics;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.qualifiable.Qualifiable;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.DataElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetypedef.PropertyValueTypeDef;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetypedef.PropertyValueTypeDefHelper;
import org.eclipse.basyx.vab.exception.provider.ProviderException;

/**
 * Property class
 * 
 * @author kuhn, schnicke
 *
 */
public class Property extends DataElement implements IProperty {
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
	 * Returns true if the given submodel element map is recognized as a property
	 */
	public static boolean isProperty(Map<String, Object> map) {
		String modelType = ModelType.createAsFacade(map).getName();
		// Either model type is set or the element type specific attributes are contained (fallback)
		return MODELTYPE.equals(modelType)
				|| (modelType == null && (map.containsKey(VALUE) && map.containsKey(VALUETYPE)));
	}

	/**
	 * Constructor that creates a property with data
	 * 
	 * @param value
	 *            the value of the property instance <b>!! Is defined in standard as
	 *            String, but does not make sense in this context !!</b>
	 */
	public Property(Object value) {
		this();
		// Put attributes
		put(Property.VALUEID, null);
		set(value);
	}

	public Property(Object value, Referable referable, Reference semanticId, Qualifiable qualifiable) {
		this(value);
		putAll(referable);
		put(HasSemantics.SEMANTICID, semanticId);
		putAll(qualifiable);
	}

	/**
	 * Overrides the orignal value type that has been determined by inspecting the given value.
	 * Only use this method, if there is no actual value for this property (e.g. when creating templates)
	 * 
	 * @param type
	 *             manually determined type of the value
	 */
	public void setValueType(PropertyValueTypeDef type) {
		put(Property.VALUETYPE, PropertyValueTypeDefHelper.getWrapper(type));
	}

	public void setValueId(IReference ref) {
		Reference refMap = new Reference();
		refMap.setKeys(ref.getKeys());
		put(Property.VALUEID, refMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public IReference getValueId() {
		return Reference.createAsFacade((Map<String, Object>) get(VALUEID));
	}

	@Override
	public void set(Object value) {
		put(Property.VALUE, value);
		put(Property.VALUETYPE, PropertyValueTypeDefHelper.getTypeWrapperFromObject(value));
	}

	/**
	 * Sets the value and explicitly specifies the type of this value.
	 * 
	 * @throws ProviderException
	 */
	public void set(Object newValue, PropertyValueTypeDef newType) throws ProviderException {
		put(Property.VALUE, newValue);
		setValueType(newType);
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
		Reference ref = new Reference(description, KeyElements.CONCEPTDESCRIPTION, true);
		setSemanticID(ref);
	}

	@Override
	protected KeyElements getKeyElement() {
		return KeyElements.PROPERTY;
	}
}
