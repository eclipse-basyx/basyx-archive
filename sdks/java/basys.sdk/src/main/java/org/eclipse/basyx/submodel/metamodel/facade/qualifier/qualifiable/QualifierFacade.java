package org.eclipse.basyx.submodel.metamodel.facade.qualifier.qualifiable;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.qualifier.qualifiable.IQualifier;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.HasSemantics;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.qualifiable.Qualifier;

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
