package org.eclipse.basyx.aas.impl.metamodel.facades;

import java.util.Map;

import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IHasSemantics;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.qualifier.HasSemantics;

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

	@Override
	public IReference getSemanticId() {
		return (IReference)map.get(HasSemantics.SEMANTICID);
	}

	@Override
	public void setSemanticID(IReference ref) {
		map.put(HasSemantics.SEMANTICID, ref);
		
	}

}
