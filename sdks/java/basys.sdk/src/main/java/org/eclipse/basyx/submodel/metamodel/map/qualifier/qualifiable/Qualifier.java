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

	public static final String QUALIFIERTYPE = "qualifierType";

	public static final String QUALIFIERVALUE = "qualifierValue";

	public static final String QUALIFIERVALUEID = "qualifierValueId";

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
		put(QUALIFIERTYPE, "");
		put(QUALIFIERVALUE, null);
		put(QUALIFIERVALUEID, null);
	}

	public Qualifier(String type, String value, Reference valueId) {
		// Add all attributes from HasSemantics
		this.putAll(new HasSemantics());

		// Default values
		put(QUALIFIERTYPE,type);
		put(QUALIFIERVALUE, value);
		put(QUALIFIERVALUEID, valueId);
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

	public void setQualifierType(String obj) {
		put(Qualifier.QUALIFIERTYPE, obj);
	}

	@Override
	public String getQualifierType() {
		return (String) get(Qualifier.QUALIFIERTYPE);
	}

	public void setQualifierValue(String obj) {
		put(Qualifier.QUALIFIERVALUE, obj);
	}

	@Override
	public String getQualifierValue() {
		return (String) get(Qualifier.QUALIFIERVALUE);
	}

	public void setQualifierValueId(IReference obj) {
		put(Qualifier.QUALIFIERVALUEID, obj);
	}

	@SuppressWarnings("unchecked")
	@Override
	public IReference getQualifierValueId() {
		return Reference.createAsFacade((Map<String, Object>) get(Qualifier.QUALIFIERVALUEID));
	}

	@Override
	public IReference getSemanticId() {
		return HasSemantics.createAsFacade(this).getSemanticId();
	}

	public void setSemanticID(IReference ref) {
		HasSemantics.createAsFacade(this).setSemanticID(ref);
	}
}
