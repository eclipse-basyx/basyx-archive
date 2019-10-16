package org.eclipse.basyx.submodel.metamodel.facade.qualifier.haskind;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.qualifier.haskind.IHasKind;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.haskind.HasKind;

/**
 * Facade providing access to a map containing the HasKind structure the
 * Overridden function names are self explanatory
 * 
 * @author rajashek
 *
 */

public class HasKindFacade implements IHasKind {
	private Map<String, Object> map;

	public HasKindFacade(Map<String, Object> map) {
		this.map = map;
	}

	@Override
	public String getHasKindReference() {
		return (String) map.get(HasKind.KIND);
	}

	public void setHasKindReference(String kind) {
		map.put(HasKind.KIND, kind);
	}

}
