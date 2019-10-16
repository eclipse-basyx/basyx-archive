package org.eclipse.basyx.submodel.factory.xml;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.map.reference.Key;
/**
 * Returns Key Object for the Map with <aas:Key>
 * 
 * @author rajashek
 *
 */
public class TransformKey {
	/**
	 * The function accepts the Map object of key tag  and returns the object of class Key
	 */
	public static Key transformKey(Map<String, Object> object) {
		
		String text =(String) object.get("#text");
		String idType=(String)object.get("idType");
		String type =(String)object.get("type");
		boolean local=Boolean.parseBoolean((String) object.get("local"));
		
	
	return new Key(type, local, text, idType);
	}

}
