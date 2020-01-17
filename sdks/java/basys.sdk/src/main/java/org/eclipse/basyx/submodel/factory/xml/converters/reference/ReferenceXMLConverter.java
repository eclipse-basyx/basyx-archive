package org.eclipse.basyx.submodel.factory.xml.converters.reference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.eclipse.basyx.submodel.factory.xml.XMLHelper;
import org.eclipse.basyx.submodel.metamodel.api.reference.IKey;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.map.reference.Key;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Handles the conversion between an IReference object and the XML tag &lt;aas:keys&gt; in both directions
 * 
 * @author conradi
 *
 */
public class ReferenceXMLConverter {
	
	public static final String LOCAL = "local";
	public static final String TYPE = "type";
	public static final String KEYS = "aas:keys";
	public static final String KEY = "aas:key";
	
	
	/**
	 * Parses the Reference object from XML
	 * 
	 * @param xmlObject the XML map containing the &lt;aas:keys&gt; tag
	 * @return the parsed Reference object
	 */
	public static Reference parseReference(Map<String, Object> xmlObject) {
		Reference reference = new Reference();
		if(xmlObject == null) return reference;
		reference.setKeys(parseKeys(xmlObject));
		return reference;
	}
	
	
	/**
	 * Parses the Key objects from XML
	 * 
	 * @param xmlObject the XML map containing the &lt;aas:keys&gt; tag
	 * @return a List of the parsed Key objects
	 */
	@SuppressWarnings("unchecked")
	private static List<IKey> parseKeys(Map<String, Object> xmlObject) {
		List<IKey> keyList = new ArrayList<>();
		if(xmlObject == null) return keyList;
		
		Map<String, Object> keysObject = (Map<String, Object>) xmlObject.get(KEYS);
		if(keysObject == null) return keyList;
		
		List<Map<String, Object>> xmlKeys = XMLHelper.getList(keysObject.get(KEY));
		for (Map<String, Object> xmlKey: xmlKeys) {
			keyList.add(parseKey(xmlKey));
		}
		
		return keyList;
	}
	
	
	/**
	 * Parses a single Key object from XML
	 * 
	 * @param xmlObject the XML map containing the contents of the &lt;aas:key&gt; tag
	 * @return the parsed Key object
	 */
	private static Key parseKey(Map<String, Object> xmlObject) {
		String text = XMLHelper.getString(xmlObject.get(XMLHelper.TEXT));
		String idType = XMLHelper.getString(xmlObject.get(XMLHelper.ID_TYPE));
		String type = XMLHelper.getString(xmlObject.get(TYPE));
		boolean local = Boolean.parseBoolean(XMLHelper.getString(xmlObject.get(LOCAL)));
	
		return new Key(type, local, text, idType);
	}
	
	
	
	

	/**
	 * Builds XML form a given Collection of IReference objects
	 * 
	 * @param document the XML document
	 * @param references a Collection of IReference objects to be converted to XML
	 * @return the &lt;aas:keys&gt; XML tag build from the IReference objects
	 */
	public static Element buildReferencesXML(Document document, Collection<IReference> references) {
		Element xmlKeys = document.createElement(KEYS);
		if(references == null) return xmlKeys;
		
		for(IReference reference: references) {	
			if(reference != null) {
				for(IKey key: reference.getKeys()) {
					xmlKeys.appendChild(buildKey(document, key));
				}
			}
		}
		return xmlKeys;
	}
	
	
	/**
	 * Builds XML form a given single IReference object
	 * 
	 * @param document the XML document
	 * @param reference the IReference object to be converted to XML
	 * @return the &lt;aas:keys&gt; XML tag build from the IReference object
	 */
	public static Element buildReferenceXML(Document document, IReference reference) {
		if(reference == null) return document.createElement(KEYS);
		return buildReferencesXML(document, Arrays.asList(reference));
	}
	

	/**
	 * Builds XML form a given IKey object
	 * 
	 * @param document the XML document
	 * @param key the IKey object to be converted to XML
	 * @return the &lt;aas:key&gt; XML tag build from the IKey object
	 */
	private static Element buildKey(Document document, IKey key) {
		Element xmlKey = document.createElement(KEY);	
		xmlKey.appendChild(document.createTextNode(key.getValue()));
		xmlKey.setAttribute(XMLHelper.ID_TYPE, key.getidType());
		xmlKey.setAttribute(LOCAL, String.valueOf(key.isLocal()));
		xmlKey.setAttribute(TYPE, key.getType());
		
		return xmlKey;
	}
}