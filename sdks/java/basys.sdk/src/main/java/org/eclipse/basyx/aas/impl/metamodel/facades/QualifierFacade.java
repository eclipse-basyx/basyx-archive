package org.eclipse.basyx.aas.impl.metamodel.facades;

import java.util.Map;

import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.qualifiable.IQualifier;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.qualifier.HasSemantics;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.qualifier.qualifiable.Qualifier;

/**
 * Facade providing access to a map containing the Qualifier structure
 * 
 * @author rajashek
 *
 */

public class QualifierFacade implements IQualifier {
	private Map<String, Object> map;

	public QualifierFacade(Map<String, Object> map) {
		super();
		this.map = map;
	}

	public void setQualifierType(String obj) {
		map.put(Qualifier.QUALIFIERTYPE, obj);
	}

	@Override
	public String getQualifierType() {
		return (String) map.get(Qualifier.QUALIFIERTYPE);
	}

	public void setQualifierValue(Object obj) {
		map.put(Qualifier.QUALIFIERVALUE, obj);
	}

	@Override
	public Object getQualifierValue() {
		return map.get(Qualifier.QUALIFIERVALUE);
	}

	public void setQualifierValueId(IReference obj) {
		map.put(Qualifier.QUALIFIERVALUEID, obj);
	}

	@Override
	public IReference getQualifierValueId() {
		return (IReference) map.get(Qualifier.QUALIFIERVALUEID);
	}

	@Override
	public IReference getSemanticId() {
		return (IReference) map.get(HasSemantics.SEMANTICID);
	}

	public void setSemanticID(IReference ref) {
		map.put(HasSemantics.SEMANTICID, ref);
	}

}
