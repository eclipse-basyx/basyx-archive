package org.eclipse.basyx.vab.provider.xml.transformers;

import java.util.HashSet;
import java.util.Map;

import org.eclipse.basyx.aas.api.metamodel.aas.parts.IView;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.AssetAdministrationShell;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.parts.ConceptDictionary;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.qualifier.Identifiable;

/**
 * Returns AssetAdministrationShell Object for the Map with
 * <aas:assetAdministrationShell>
 * 
 * @author rajashek
 *
 */
public class TransformAssetAdministrationShell {
	/**
	 * The function accepts the Map object of assetAdministrationShell tag and
	 * returns the object of AssetAdministrationShell
	 * 
	 */

	public static AssetAdministrationShell transformAssetAdministrationShell(Map<String, Object> object) {
		Identifiable transformIdentifier = TransformIdentifiable.transformIdentifier(object);
		HashSet<IView> transformView = TransformView.transformView(object);
		HashSet<ConceptDictionary> transformConceptDictionary = TransformConceptDictionary
				.transformConceptDictionary(object);
		AssetAdministrationShell obj = new AssetAdministrationShell();
		obj.putAll(transformIdentifier);
		obj.put(AssetAdministrationShell.VIEWS, transformView);
		obj.put(AssetAdministrationShell.CONCEPTDICTIONARY, transformConceptDictionary);
		return obj;
	}

	/**
	 * Helper Function to get the respective object from the root object created by
	 * xmlparser
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getAssetAdminShellsFromRootObj(Map<String, Object> rootObj) {
		try {
			return (Map<String, Object>) ((Map<String, Object>) rootObj.get("aas:aasenv"))
					.get("aas:assetAdministrationShells");
		} catch (Exception e) {
			System.out.println("Error with Maps");
		}
		return null;
	}

}
