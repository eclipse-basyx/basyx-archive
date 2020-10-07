package org.eclipse.basyx.submodel.metamodel.map.qualifier.qualifiable;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.qualifier.qualifiable.IQualifier;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.map.modeltype.ModelType;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.HasSemantics;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;

/**
 * Qualifier class
 * 
 * @author kuhn
 *
 */
public class Qualifier extends Constraint implements IQualifier {
	public static final String QUALIFIER = "qualifier";

	public static final String TYPE = "type";

	public static final String VALUE = "value";

	public static final String VALUEID = "valueId";

	public static final String VALUETYPE = "valueType";

	public static final String MODELTYPE = "Qualifier";

	/**
	 * Constructor
	 */
	public Qualifier() {
		// Add model type
		putAll(new ModelType(MODELTYPE));

		// Add all attributes from HasSemantics
		this.putAll(new HasSemantics());

		// Default values
		put(TYPE, "");
		put(VALUE, null);
		put(VALUEID, null);
		put(VALUETYPE, "");
	}
	
	/**
	 * Constructor accepting mandatory attributes
	 * @param type
	 * @param valueType
	 */
	public Qualifier(String type, String valueType) {
		this(type, null, valueType, null);
	}

	public Qualifier(String type, String value, String valueType, Reference valueId) {
		// Add all attributes from HasSemantics
		this.putAll(new HasSemantics());

		// Default values
		put(TYPE,type);
		put(VALUE, value);
		put(VALUEID, valueId);
		put(VALUETYPE, valueType);
	}

	/**
	 * Creates a Qualifier object from a map
	 * 
	 * @param obj
	 *            a Qualifier object as raw map
	 * @return a Qualifier object, that behaves like a facade for the given map
	 */
	public static Qualifier createAsFacade(Map<String, Object> map) {
		if (map == null) {
			return null;
		}

		Qualifier ret = new Qualifier();
		ret.setMap(map);
		return ret;
	}

	public void setType(String obj) {
		put(Qualifier.TYPE, obj);
	}

	@Override
	public String getType() {
		return (String) get(Qualifier.TYPE);
	}

	public void setValue(String obj) {
		put(Qualifier.VALUE, obj);
	}

	@Override
	public String getValue() {
		return (String) get(Qualifier.VALUE);
	}

	public void setValueId(IReference obj) {
		put(Qualifier.VALUEID, obj);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public IReference getValueId() {
		return Reference.createAsFacade((Map<String, Object>) get(Qualifier.VALUEID));
	}

	public void setValueType(String obj) {
		put(Qualifier.VALUETYPE, obj);
	}
	
	@Override
	public String getValueType() {
		return (String) get(Qualifier.VALUETYPE);
	}

	@Override
	public IReference getSemanticId() {
		return HasSemantics.createAsFacade(this).getSemanticId();
	}

	public void setSemanticID(IReference ref) {
		HasSemantics.createAsFacade(this).setSemanticID(ref);
	}
}
