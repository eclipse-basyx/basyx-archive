package org.eclipse.basyx.aas.factory.xml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.aas.metamodel.map.parts.ConceptDictionary;
import org.eclipse.basyx.submodel.factory.xml.TransformKey;
import org.eclipse.basyx.submodel.metamodel.api.reference.IKey;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;

/**
 * Returns HashSet<ConceptDictionary> Object for the Map with
 * <aas:conceptDictionaries>
 * 
 * @author rajashek
 *
 */
public class TransformConceptDictionary {

	/**
	 * The function accepts the Map object of conceptDescriptionRef tag and returns
	 * HashSet of ConceptDictionary
	 */
	@SuppressWarnings({ "unchecked" })
	public static HashSet<ConceptDictionary> transformConceptDictionary(Map<String, Object> object) {
		HashSet<ConceptDictionary> ConceptDictionarySet = new HashSet<ConceptDictionary>();
		Map<String, Object> conceptDictionariesObject = (Map<String, Object>) object.get("aas:conceptDictionaries");
		Object conceptDictionary = conceptDictionariesObject.get("aas:conceptDictionary");
		// We need to check if conceptDictionary is instance of MAP object or Collection
		// and process them accordingly
		if (conceptDictionary instanceof Collection<?>) {
			ArrayList<Object> conceptDictionaryArrayList = (ArrayList<Object>) conceptDictionary;
			for (Object object2 : conceptDictionaryArrayList) {
				handleConceptDictionaryObject(ConceptDictionarySet, object2);
			}
		} else {
			handleConceptDictionaryObject(ConceptDictionarySet, conceptDictionary);
		}
		return ConceptDictionarySet;

	}

	/**
	 * The function accepts the Map object of conceptDictionary tag and populates
	 * the HashSet of ConceptDictionary
	 */
	@SuppressWarnings("unchecked")
	private static void handleConceptDictionaryObject(HashSet<ConceptDictionary> ConceptDictionarySet, Object object2) {
		Map<String, Object> conceptDictionaryMapObject = (Map<String, Object>) object2;
		Map<String, Object> conceptDescriptionRefsObj = (Map<String, Object>) conceptDictionaryMapObject
				.get("aas:conceptDescriptionRefs");
		Object conceptDescriptionRefObj = conceptDescriptionRefsObj.get("aas:conceptDescriptionRef");
		Set<IReference> referenceset = new HashSet<IReference>();
		// We need to check if conceptDescriptionRefObj is instance of MAP object or
		// Collection and process them accordingly
		if (conceptDescriptionRefObj instanceof Collection<?>) {
			ArrayList<Object> conceptDescriptionReflist = (ArrayList<Object>) conceptDescriptionRefObj;
			for (Object object3 : conceptDescriptionReflist) {
				getReferenceSetFromMapObj(referenceset, object3);
			}

		} else {
			getReferenceSetFromMapObj(referenceset, conceptDescriptionRefObj);
		}

		ConceptDictionary obj = new ConceptDictionary(referenceset);
		obj.setIdShort((String) conceptDictionaryMapObject.get("aas:idShort"));
		ConceptDictionarySet.add(obj);
	}

	@SuppressWarnings("unchecked")
	/**
	 * The function accepts the Map object of conceptDescriptionRef tag and
	 * populates the HashSet of Reference
	 */
	private static void getReferenceSetFromMapObj(Set<IReference> referenceset, Object obj) {
		Map<String, Object> conceptDescriptionRefMapObject = (Map<String, Object>) obj;
		Map<String, Object> keysObject = (Map<String, Object>) conceptDescriptionRefMapObject.get("aas:keys");
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

		Reference ref = new Reference(Keyset);
		referenceset.add(ref);
	}
}
