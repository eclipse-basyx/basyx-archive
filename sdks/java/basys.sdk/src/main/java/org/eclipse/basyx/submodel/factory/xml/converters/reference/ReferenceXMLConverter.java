/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.submodel.factory.xml.converters.reference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.basyx.submodel.factory.xml.XMLHelper;
import org.eclipse.basyx.submodel.metamodel.api.reference.IKey;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyType;
import org.eclipse.basyx.submodel.metamodel.map.reference.Key;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Handles the conversion between an IReference object and the XML tag &lt;aas:keys&gt; in both directions
 * 
 * @author conradi, espen
 *
 */
public class ReferenceXMLConverter {
	public static final String LOCAL = "local";
	public static final String TYPE = "type";
	public static final String IDTYPE = "idType";
	public static final String KEYS = "aas:keys";
	public static final String KEY = "aas:key";
	
	
	/**
	 * Parses the Reference object from XML
	 * 
	 * @param xmlObject the XML map containing the &lt;aas:keys&gt; tag
	 * @return the parsed Reference object
	 */
	public static Reference parseReference(Map<String, Object> xmlObject) {
		if (xmlObject == null)
			return null;
		Reference reference = new Reference();
		reference.setKeys(parseKeys(xmlObject, KEYS, KEY));
		return reference;
	}
	
	/**
	 * Parses the Reference object from XML with a custom keys tagName
	 * 
	 * @param xmlObject   the XML map containing the &lt;keys&gt; tag
	 * @param keysTagName the custom &lt;*keys*&gt; tagName
	 * @param keyTagName  the custom &lt;*key*&gt; tagName
	 * @return the parsed Reference object
	 */
	public static Reference parseReference(Map<String, Object> xmlObject, String keysTagName, String keyTagName) {
		Reference reference = new Reference();
		if (xmlObject == null)
			return reference;
		reference.setKeys(parseKeys(xmlObject, keysTagName, keyTagName));
		return reference;
	}
	
	/**
	 * Parses the Key objects from XML
	 * 
	 * @param xmlObject   the XML map containing the &lt;aas:keys&gt; tag
	 * @param keysTagName the custom &lt;*keys*&gt; tagName
	 * @param keyTagName  the custom &lt;*key*&gt; tagName
	 * @return a List of the parsed Key objects
	 */
	@SuppressWarnings("unchecked")
	private static List<IKey> parseKeys(Map<String, Object> xmlObject, String keysTagName, String keyTagName) {
		List<IKey> keyList = new ArrayList<>();
		if(xmlObject == null) return keyList;
		
		Map<String, Object> keysObject = (Map<String, Object>) xmlObject.get(keysTagName);
		if(keysObject == null) return keyList;
		
		List<Map<String, Object>> xmlKeys = XMLHelper.getList(keysObject.get(keyTagName));
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
		String idType = XMLHelper.getString(xmlObject.get(IDTYPE));
		String type = XMLHelper.getString(xmlObject.get(TYPE));
		boolean local = Boolean.parseBoolean(XMLHelper.getString(xmlObject.get(LOCAL)));
	
		return new Key(KeyElements.fromString(type), local, text, KeyType.fromString(idType));
	}
	
	/**
	 * Builds XML form a given Collection of IReference objects
	 * 
	 * @param document   the XML document
	 * @param references a Collection of IReference objects to be converted to XML
	 * @return the &lt;aas:keys&gt; XML tag build from the IReference objects
	 */
	public static Element buildReferencesXML(Document document, Collection<IReference> references) {
		return buildReferencesXML(document, references, KEYS, KEY);
	}

	/**
	 * Builds XML form a given Collection of IReference objects
	 * 
	 * @param document    the XML document
	 * @param references  a Collection of IReference objects to be converted to XML
	 * @param keysTagName the custom &lt;*keys*&gt; tagName
	 * @param keyTagName  the custom &lt;*key*&gt; tagName
	 * @return the &lt;aas:keys&gt; XML tag build from the IReference objects
	 */
	public static Element buildReferencesXML(Document document, Collection<IReference> references, String keysTagName,
			String keyTagName) {
		Element xmlKeys = document.createElement(keysTagName);
		if(references == null) return xmlKeys;
		
		for(IReference reference: references) {	
			if(reference != null) {
				for(IKey key: reference.getKeys()) {
					xmlKeys.appendChild(buildKey(document, key, keyTagName));
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
		return buildReferencesXML(document, Arrays.asList(reference), KEYS, KEY);
	}
	
	/**
	 * Builds XML form a given single IReference object
	 * 
	 * @param document    the XML document
	 * @param reference   the IReference object to be converted to XML
	 * @param keysTagName the custom &lt;*keys*&gt; tagName
	 * @param keyTagName  the custom &lt;*key*&gt; tagName
	 * @return the &lt;aas:keys&gt; XML tag build from the IReference object
	 */
	public static Element buildReferenceXML(Document document, IReference reference, String keysTagName,
			String keyTagName) {
		if (reference == null)
			return document.createElement(keysTagName);
		return buildReferencesXML(document, Arrays.asList(reference), keysTagName, keyTagName);
	}

	/**
	 * Builds XML form a given IKey object
	 * 
	 * @param document   the XML document
	 * @param key        the IKey object to be converted to XML
	 * @param keyTagName the custom &lt;*key*&gt; tagName
	 * @return the &lt;aas:key&gt; XML tag build from the IKey object
	 */
	private static Element buildKey(Document document, IKey key, String keyTagName) {
		Element xmlKey = document.createElement(keyTagName);
		xmlKey.appendChild(document.createTextNode(key.getValue()));
		xmlKey.setAttribute(IDTYPE, key.getIdType().toString());
		xmlKey.setAttribute(LOCAL, String.valueOf(key.isLocal()));
		xmlKey.setAttribute(TYPE, key.getType().toString());
		
		return xmlKey;
	}
}
