package org.eclipse.basyx.aas.metamodel.facades;

import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.qualifiable.IFormula;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.qualifiable.Formula;

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

	@Override
	public void setDependsOn(Set<IReference> dependsOn) {
		map.put(Formula.DEPENDSON, dependsOn);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<IReference> getDependsOn() {
		return (Set<IReference>)map.get(Formula.DEPENDSON);
	}
	

}
