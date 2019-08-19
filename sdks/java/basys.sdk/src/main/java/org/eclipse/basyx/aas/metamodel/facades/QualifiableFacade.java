package org.eclipse.basyx.aas.metamodel.facades;

import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.qualifiable.IConstraint;
import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.qualifiable.IQualifiable;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.qualifiable.Qualifiable;
/**
 * Facade providing access to a map containing the Qualifiable structure
 * @author rajashek
 *
 */
public class QualifiableFacade implements IQualifiable {

	private Map<String, Object> map;

	public QualifiableFacade(Map<String, Object> map) {
		super();
		this.map = map;
	}

	@Override
	public void setQualifier(Set<IConstraint> qualifiers) {
		map.put(Qualifiable.CONSTRAINTS, qualifiers);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<IConstraint> getQualifier() {
		return (Set<IConstraint>)map.get(Qualifiable.CONSTRAINTS);
	}

}
