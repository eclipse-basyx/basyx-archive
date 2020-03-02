package org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IRange;
import org.eclipse.basyx.submodel.metamodel.map.modeltype.ModelType;

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
	
	public Range(String valueType) {
		this();
		put(VALUETYPE, valueType);
	}
	
	public Range(String valueType, Object min, Object max) {
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
	
	@Override
	public String getValueType() {
		return (String) get(VALUETYPE);
	}

	@Override
	public Object getMin() {
		return get(MIN);
	}

	@Override
	public Object getMax() {
		return get(MAX);
	}

}