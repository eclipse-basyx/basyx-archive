package org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.range;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IRange;
import org.eclipse.basyx.submodel.metamodel.map.modeltype.ModelType;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.DataElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetypedef.PropertyValueTypeDef;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetypedef.PropertyValueTypeDefHelper;

/**
 * A range element as defined in DAAS document
 * 
 * @author conradi
 *
 */
public class Range extends DataElement implements IRange {

	public static final String MODELTYPE = "Range";
	public static final String VALUETYPE = "valueType";
	public static final String MIN = "min";
	public static final String MAX = "max";
	

	public Range() {
		// Add model type
		putAll(new ModelType(MODELTYPE));
	}
	
	public Range(PropertyValueTypeDef valueType) {
		this();
		setValueType(valueType);
	}

	/**
	 * Constructor accepting only mandatory attribute
	 * @param idShort
	 * @param valueType
	 */
	public Range(String idShort, PropertyValueTypeDef valueType) {
		super(idShort);
		// Add model type
		putAll(new ModelType(MODELTYPE));
		put(VALUETYPE, valueType);
	}
	
	public Range(PropertyValueTypeDef valueType, Object min, Object max) {
		this(valueType);
		put(MIN, min);
		put(MAX, max);
	}
	
	/**
	 * Creates a Range object from a map
	 * 
	 * @param obj a Range object as raw map
	 * @return a Range object, that behaves like a facade for the given map
	 */
	public static Range createAsFacade(Map<String, Object> obj) {
		Range facade = new Range();
		facade.setMap(obj);
		return facade;
	}
	
	/**
	 * Returns true if the given submodel element map is recognized as a Range element
	 */
	public static boolean isRange(Map<String, Object> map) {
		String modelType = ModelType.createAsFacade(map).getName();
		// Either model type is set or the element type specific attributes are contained (fallback)
		return MODELTYPE.equals(modelType)
				|| (modelType == null && (map.containsKey(MIN) && map.containsKey(MAX) && map.containsKey(VALUETYPE)));
	}

	private void setValueType(PropertyValueTypeDef valueType) {
		put(Range.VALUETYPE, PropertyValueTypeDefHelper.getWrapper(valueType));
	}

	@Override
	public PropertyValueTypeDef getValueType() {
		return PropertyValueTypeDefHelper.readTypeDef(get(Range.VALUETYPE));
	}

	@Override
	public Object getMin() {
		Object value = get(MIN);
		if(value instanceof String) {
			return PropertyValueTypeDefHelper.getJavaObject(value, getValueType());
		}else {
			return value;
		}
	}

	@Override
	public Object getMax() {
		Object value = get(MAX);
		if(value instanceof String) {
			return PropertyValueTypeDefHelper.getJavaObject(value, getValueType());
		}else {
			return value;
		}
	}
	
	@Override
	protected KeyElements getKeyElement() {
		return KeyElements.RANGE;
	}

	@Override
	public RangeValue getValue() {
		return new RangeValue(getMin(), getMax());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void setValue(Object value) {
		if(RangeValue.isRangeValue(value)) {
			RangeValue rv = RangeValue.createAsFacade((Map<String, Object>) value);
			Object minValue = rv.getMin();
			Object maxValue = rv.getMax();
			
			put(Range.MIN, PropertyValueTypeDefHelper.prepareForSerialization(minValue));
			put(Range.MAX, PropertyValueTypeDefHelper.prepareForSerialization(maxValue));
			if(getValueType() == null) { 
				setValueType(PropertyValueTypeDefHelper.getType(minValue));
			}
		} else {
			throw new IllegalArgumentException("Given Object is not a RangeValue");
		}
	}

}