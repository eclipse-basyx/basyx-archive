package org.eclipse.basyx.submodel.metamodel.facade.reference;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;

/**
 * Helper class for working with references
 * 
 * @author schnicke
 *
 */
public class ReferenceHelper {
	/**
	 * Helper method used e.g. by facades to transform a set of maps to a set of
	 * IReference
	 * 
	 * @param set
	 * @return
	 */
	public static Set<IReference> transform(Set<Map<String, Object>> set) {
		// Transform set of maps to set of IReference
		Set<IReference> ret = new HashSet<>();
		for (Map<String, Object> m : set) {
			ret.add(new ReferenceFacade(m));
		}

		return ret;
	}
}
