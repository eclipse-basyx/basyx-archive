package org.eclipse.basyx.submodel.metamodel.facade.qualifier.qualifiable;

import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.submodel.metamodel.api.qualifier.qualifiable.IFormula;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.qualifiable.Formula;

/**
 * Facade providing access to a map containing the Formula structure
 * @author rajashek
 *
 */

public class FormulaFacade implements IFormula {
	private Map<String, Object> map;

	public FormulaFacade(Map<String, Object> map) {
		super();
		this.map = map;
	}

	public void setDependsOn(Set<IReference> dependsOn) {
		map.put(Formula.DEPENDSON, dependsOn);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<IReference> getDependsOn() {
		return (Set<IReference>)map.get(Formula.DEPENDSON);
	}
	

}
