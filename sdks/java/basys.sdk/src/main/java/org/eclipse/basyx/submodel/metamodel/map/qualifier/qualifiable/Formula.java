package org.eclipse.basyx.submodel.metamodel.map.qualifier.qualifiable;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.submodel.metamodel.api.qualifier.qualifiable.IFormula;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.map.modeltype.ModelType;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.reference.ReferenceHelper;

/**
 * Forumla class as defined by DAAS document
 * 
 * @author schnicke
 *
 */
public class Formula extends Constraint implements IFormula {

	public static final String DEPENDSON = "dependsOn";

	public static final String MODELTYPE = "Formula";

	/**
	 * Constructor
	 */
	public Formula() {
		// Add model type
		putAll(new ModelType(MODELTYPE));

		put(DEPENDSON, new HashSet<Reference>());
	}

	/**
	 * 
	 * @param dependsOn
	 *            set of References the formula depends on
	 */
	public Formula(Set<IReference> dependsOn) {
		putAll(new ModelType(MODELTYPE));
		put(DEPENDSON, dependsOn);
	}

	/**
	 * Creates a Formula object from a map
	 * 
	 * @param obj
	 *            a Formula object as raw map
	 * @return a Formula object, that behaves like a facade for the given map
	 */
	public static Formula createAsFacade(Map<String, Object> map) {
		if (map == null) {
			return null;
		}

		Formula ret = new Formula();
		ret.setMap(map);
		return ret;
	}

	public void setDependsOn(Set<IReference> dependsOn) {
		put(Formula.DEPENDSON, dependsOn);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<IReference> getDependsOn() {
		// Transform set of maps to set of IReference
		Set<Map<String, Object>> set = (Set<Map<String, Object>>) get(Formula.DEPENDSON);
		return ReferenceHelper.transform(set);
	}


}
