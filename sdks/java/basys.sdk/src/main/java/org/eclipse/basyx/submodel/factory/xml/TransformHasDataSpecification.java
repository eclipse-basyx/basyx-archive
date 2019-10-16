package org.eclipse.basyx.submodel.factory.xml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.submodel.metamodel.api.reference.IKey;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.HasDataSpecification;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;

/**
 * Returns HasDataSpecification Object for the Map with
 * <aas:hasDataSpecification>
 * 
 * @author rajashek
 *
 */
public class TransformHasDataSpecification {

	/**
	 * The function accepts the Map object of hasDataSpecification tag and returns
	 * the object of class HasDataSpecification
	 */
	@SuppressWarnings("unchecked")
	public static HasDataSpecification transformHasDataSpecification(Map<String, Object> object) {
		Map<String, Object> semanticIDObj = (Map<String, Object>) object.get("aas:hasDataSpecification");
		Map<String, Object> keysObject = (Map<String, Object>) semanticIDObj.get("aas:keys");
		Object keyObject = keysObject.get("aas:key");
		ArrayList<IKey> Keyset = new ArrayList<IKey>();
		// We need to check if keyObject is instance of MAP object or Collection and
		// process them accordingly
		if (keyObject instanceof Collection<?>) {
			ArrayList<Object> keylist = (ArrayList<Object>) keyObject;
			for (Object Object : keylist) {
				Keyset.add(TransformKey.transformKey((Map<String, Object>) Object));
			}
		} else {
			Keyset.add(TransformKey.transformKey((Map<String, Object>) keyObject));
		}
		IReference ref = new Reference(Keyset);
		Set<IReference> refSet = new HashSet<>();
		refSet.add(ref);
		return new HasDataSpecification(refSet);
	}

}
