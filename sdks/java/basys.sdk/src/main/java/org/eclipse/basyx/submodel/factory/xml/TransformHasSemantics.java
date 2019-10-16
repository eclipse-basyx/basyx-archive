package org.eclipse.basyx.submodel.factory.xml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.reference.IKey;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.HasSemantics;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;

/**
 * Returns HasSemantics Object for the Map with <aas:semanticId>
 * 
 * @author rajashek
 *
 */
public class TransformHasSemantics {

	/**
	 * The function accepts the Map object of semanticId tag and returns the object
	 * of class HasSemantics
	 */
	@SuppressWarnings("unchecked")
	public static HasSemantics transformHasSemantics(Map<String, Object> object) {
		Map<String, Object> semanticIDObj = (Map<String, Object>) object.get("aas:semanticId");
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
		return new HasSemantics(ref);
	}

}
