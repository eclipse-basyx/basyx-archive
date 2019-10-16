package org.eclipse.basyx.submodel.factory.xml;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.map.qualifier.haskind.HasKind;

/**
 * Returns HasKind Object for t he Map with <aas:kind>
 * 
 * @author rajashek
 *
 */
public class TransformHasKind {
	/**
	 * The function accepts the Map object of kind tag and returns the object of
	 * class HasKind
	 */
	public static HasKind transformHasKind(Map<String, Object> object) {
		String hasKindVlaue = (String) object.get("aas:kind");
		return new HasKind(hasKindVlaue);
	}

}
