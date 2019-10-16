package org.eclipse.basyx.submodel.factory.xml;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.map.qualifier.Description;

/**
 * Returns Description Object for the Map with <aas:description>
 * 
 * @author rajashek
 *
 */
public class TransformDescription {

	/**
	 * The function accepts the Map object of description tag and returns the object
	 * of class Description
	 */
	@SuppressWarnings("unchecked")
	public static Description transformDescription(Map<String, Object> object) {
		Map<String, Object> descObj = (Map<String, Object>) object.get("aas:description");
		Map<String, Object> langStringObj = (Map<String, Object>) descObj.get("aas:langString");
		String text = (String) langStringObj.get("#text");
		String lang = (String) langStringObj.get("lang");
		return new Description(lang, text);
	}

}
