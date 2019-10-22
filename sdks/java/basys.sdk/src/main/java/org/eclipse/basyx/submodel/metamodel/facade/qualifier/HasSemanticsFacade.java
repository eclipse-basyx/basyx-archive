package org.eclipse.basyx.submodel.metamodel.facade.qualifier;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.qualifier.IHasSemantics;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.facade.reference.ReferenceFacade;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.HasSemantics;

/**
 * Facade providing access to a map containing the HasSemantics structure
 * 
 * @author rajashek
 *
 */

public class HasSemanticsFacade implements IHasSemantics {

	private Map<String, Object> map;

	public HasSemanticsFacade(Map<String, Object> map) {
		super();
		this.map = map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public IReference getSemanticId() {
		return new ReferenceFacade((Map<String, Object>) map.get(HasSemantics.SEMANTICID));
	}

	public void setSemanticID(IReference ref) {
		map.put(HasSemantics.SEMANTICID, ref);
	}

}
