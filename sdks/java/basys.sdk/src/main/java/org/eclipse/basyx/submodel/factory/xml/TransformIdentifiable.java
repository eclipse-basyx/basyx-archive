package org.eclipse.basyx.submodel.factory.xml;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.map.qualifier.Identifiable;

/**
 * Returns Identifiable Object for the Map with <aas:identification> &
 * <aas:administration>
 * 
 * @author rajashek
 *
 */
public class TransformIdentifiable {

	/**
	 * The function accepts the Map object of identification & administration tag
	 * and returns the object of class Identifiable
	 */
	@SuppressWarnings({ "unchecked" })
	public static Identifiable transformIdentifier(Map<String, Object> object) {
		// String version, String revision, String idShort, String category, String
		// description, String idType, String id
		Map<String, Object> identierFromXML = (Map<String, Object>) object.get("aas:identification");
		String id = (String) identierFromXML.get("#text");
		String idType = (String) identierFromXML.get("idType");

		if (object.get("aas:administration") != null) {
			Map<String, Object> administrationFromXML = (Map<String, Object>) object.get("aas:administration");
			String version = (String) administrationFromXML.get("aas:version");
			String revision = (String) administrationFromXML.get("aas:revision");

			// TODO: The file needs to be updated for further extension of constructor
			// arguments
			return new Identifiable(version, revision, "", "", "", idType, id);
		} else {
			return new Identifiable("", "", "", "", "", idType, id);
		}
	}

}
