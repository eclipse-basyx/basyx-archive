package org.eclipse.basyx.aas.factory.xml;

import java.util.Map;

import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.submodel.factory.xml.TransformDescription;
import org.eclipse.basyx.submodel.factory.xml.TransformHasKind;
import org.eclipse.basyx.submodel.factory.xml.TransformIdentifiable;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Description;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Identifiable;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.haskind.HasKind;

/**
 * Returns Asset Object for the Map with <aas:asset>
 * 
 * @author rajashek
 *
 */
public class TransformAsset {
	/**
	 * The function accepts the Map object of asset tag and returns the object of
	 * class Asset
	 */
	public static Asset transformAsset(Map<String, Object> object) {
		Identifiable transformIdentifier = TransformIdentifiable.transformIdentifier(object);
		String idShort = (String) object.get("aas:idShort");
		HasKind hasKindObj = TransformHasKind.transformHasKind(object);
		Description descriptionObj = TransformDescription.transformDescription(object);
		String desc = (String) descriptionObj.get("text");
		Asset asset = new Asset();
		asset.putAll(hasKindObj);
		asset.putAll(transformIdentifier);
		asset.putAll(descriptionObj);
		asset.setDescription(desc);
		asset.setIdShort(idShort);

		return asset;
	}

	@SuppressWarnings("unchecked")
	/**
	 * Helper Function to get the respective object from the root object created by
	 * xmlparser
	 */
	public static Map<String, Object> getAssetFromRootObj(Map<String, Object> rootObj) {
		try {
			return (Map<String, Object>) ((Map<String, Object>) rootObj.get("aas:aasenv")).get("aas:assets");
		} catch (Exception e) {
			System.out.println("Error with Maps");
		}
		return null;
	}

}
