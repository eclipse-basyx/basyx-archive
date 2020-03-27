package org.eclipse.basyx.submodel.metamodel.map.reference;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

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
	 * IReference -> Assumes the given object is a Collection<Map<String, Object>>
	 * 
	 * @param set
	 * @return
	 */
	public static Collection<IReference> transform(Object obj) {
		// Transform set of maps to set of IReference
		@SuppressWarnings("unchecked")
		Collection<Map<String, Object>> collection = (Collection<Map<String, Object>>) obj;
		return collection.stream().map(Reference::createAsFacade).collect(Collectors.toSet());
	}
}
