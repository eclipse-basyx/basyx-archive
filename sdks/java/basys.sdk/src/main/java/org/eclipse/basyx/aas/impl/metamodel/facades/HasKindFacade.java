package org.eclipse.basyx.aas.impl.metamodel.facades;

import java.util.Map;

import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.haskind.IHasKind;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.qualifier.haskind.HasKind;

/**
 * Facade providing access to a map containing the  HasKind structure
 * the Overridden function names are self explanatory 
 * @author rajashek
 *
 */

public class HasKindFacade implements IHasKind {
	private Map<String, Object> map;

	public HasKindFacade(Map<String, Object> map) {
		//super();
		this.map = map;
	}

	@Override
	public String getHasKindReference() {
		return (String) map.get(HasKind.KIND);
	}

	@Override
	public void setHasKindReference(String kind) {
		map.put(HasKind.KIND, kind);
		
	}

}
