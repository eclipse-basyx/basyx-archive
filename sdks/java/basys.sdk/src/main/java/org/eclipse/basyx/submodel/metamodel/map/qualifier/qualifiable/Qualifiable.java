package org.eclipse.basyx.submodel.metamodel.map.qualifier.qualifiable;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.submodel.metamodel.api.qualifier.qualifiable.IConstraint;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.qualifiable.IQualifiable;
import org.eclipse.basyx.submodel.metamodel.map.modeltype.ModelType;
import org.eclipse.basyx.vab.model.VABModelMap;

/**
 * Qualifiable class
 * 
 * @author kuhn
 *
 */
public class Qualifiable extends VABModelMap<Object> implements IQualifiable {
	public static final String CONSTRAINTS = "constraints";

	/**
	 * Constructor
	 */
	public Qualifiable() {
		// The instance of an element may be further qualified by one or more
		// qualifiers.
		put(CONSTRAINTS, null);
	}

	/**
	 * Constructor
	 */
	public Qualifiable(Constraint qualifier) {
		// Create collection with qualifiers
		Set<Constraint> qualifiers = new HashSet<Constraint>();
		// - Add qualifier
		qualifiers.add(qualifier);

		// The instance of an element may be further qualified by one or more
		// qualifiers.
		put(CONSTRAINTS, qualifiers);
	}

	/**
	 * Constructor
	 */
	public Qualifiable(Collection<Constraint> qualifier) {
		// The instance of an element may be further qualified by one or more
		// qualifiers.
		put(CONSTRAINTS, qualifier);
	}

	/**
	 * Creates a Qualifiable object from a map
	 * 
	 * @param obj
	 *            a Qualifiable object as raw map
	 * @return a Qualifiable object, that behaves like a facade for the given map
	 */
	public static Qualifiable createAsFacade(Map<String, Object> map) {
		if (map == null) {
			return null;
		}

		Qualifiable ret = new Qualifiable();
		ret.setMap(map);
		return ret;
	}

	public void setQualifier(Set<IConstraint> qualifiers) {
		put(Qualifiable.CONSTRAINTS, qualifiers);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<IConstraint> getQualifier() {
		// Transform set of maps to set of IConstraints
		Set<Map<String, Object>> set = (Set<Map<String, Object>>) get(Qualifiable.CONSTRAINTS);
		Set<IConstraint> ret = new HashSet<>();
		if (set != null) {
			for (Map<String, Object> m : set) {
				if (ModelType.getModelTypeName(m).equals(Formula.MODELTYPE)) {
					ret.add(Formula.createAsFacade(m));
				} else {
					ret.add(Qualifier.createAsFacade(m));
				}
			}
		}

		return ret;
	}
}
