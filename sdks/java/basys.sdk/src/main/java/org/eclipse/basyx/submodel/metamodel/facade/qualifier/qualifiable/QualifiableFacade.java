package org.eclipse.basyx.submodel.metamodel.facade.qualifier.qualifiable;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.submodel.metamodel.api.qualifier.qualifiable.IConstraint;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.qualifiable.IQualifiable;
import org.eclipse.basyx.submodel.metamodel.facade.modeltype.ModelTypeFacade;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.qualifiable.Formula;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.qualifiable.Qualifiable;

/**
 * Facade providing access to a map containing the Qualifiable structure
 * 
 * @author rajashek
 *
 */
public class QualifiableFacade implements IQualifiable {

	private Map<String, Object> map;

	public QualifiableFacade(Map<String, Object> map) {
		super();
		this.map = map;
	}

	public void setQualifier(Set<IConstraint> qualifiers) {
		map.put(Qualifiable.CONSTRAINTS, qualifiers);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<IConstraint> getQualifier() {
		// Transform set of maps to set of IConstraints
		Set<Map<String, Object>> set = (Set<Map<String, Object>>) map.get(Qualifiable.CONSTRAINTS);
		Set<IConstraint> ret = new HashSet<>();
		for (Map<String, Object> m : set) {
			if (ModelTypeFacade.getModelTypeName(m).equals(Formula.MODELTYPE)) {
				ret.add(new FormulaFacade(m));
			} else {
				ret.add(new QualifierFacade(m));
			}
		}

		return ret;
	}

}
