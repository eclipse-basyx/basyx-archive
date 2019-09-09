package org.eclipse.basyx.vab.provider.xml.transformers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.aas.api.metamodel.aas.parts.IView;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IKey;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.parts.View;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.reference.Reference;

/**
 * Returns HashSet<View> Object for the Map with <aas:views>
 * 
 * @author rajashek
 *
 */
public class TransformView {

	/**
	 * The function accepts the Map object of view tag and returns the object of
	 * HashSet of View
	 */
	@SuppressWarnings({ "unchecked" })
	public static HashSet<IView> transformView(Map<String, Object> object) {

		HashSet<IView> viewSet = new HashSet<IView>();
		Map<String, Object> viewsobject = (Map<String, Object>) object.get("aas:views");
		Object viewObject = viewsobject.get("aas:view");
		// We need to check if viewObject is instance of MAP object or Collection and
		// process them accordingly
		if (viewObject instanceof Collection<?>) {
			ArrayList<Object> viewArrayList = (ArrayList<Object>) viewObject;
			for (Object object2 : viewArrayList) {
				handleViewObject(viewSet, object2);
			}
		} else {
			handleViewObject(viewSet, viewObject);
		}

		return viewSet;

	}

	/**
	 * The function accepts the Map object of view tag and returns the object of
	 * HashSet of View
	 */
	@SuppressWarnings("unchecked")
	private static void handleViewObject(HashSet<IView> viewSet, Object object2) {
		Map<String, Object> viewMapObject = (Map<String, Object>) object2;
		Map<String, Object> containedElementsObject = (Map<String, Object>) viewMapObject.get("aas:containedElements");
		Map<String, Object> containedElementObject = (Map<String, Object>) containedElementsObject
				.get("aas:containedElementRef");
		Map<String, Object> keysObject = (Map<String, Object>) containedElementObject.get("aas:keys");
		Object keyObject = keysObject.get("aas:key");
		ArrayList<IKey> Keyset = new ArrayList<IKey>();
		Set<IReference> referenceset = new HashSet<IReference>();
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
		View view = new View(referenceset);
		view.setIdshort((String) viewMapObject.get("aas:idShort"));
		viewSet.add(view);
	}

}
