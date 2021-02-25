/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.submodel.factory.xml.converters.qualifier;

import java.util.Map;

import org.eclipse.basyx.submodel.factory.xml.XMLHelper;
import org.eclipse.basyx.submodel.factory.xml.converters.reference.ReferenceXMLConverter;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.IReferable;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.common.base.Strings;

/**
 * Handles the conversion between an IReferable object and the XML tags<br>
 * &lt;aas:idShort&gt;, &lt;aas:category&gt;, &lt;aas:parent&gt; and &lt;aas:description&gt; in both directions
 * 
 * @author conradi
 *
 */
public class ReferableXMLConverter {
	
	public static final String ID_SHORT = "aas:idShort";
	public static final String CATEGORY = "aas:category";
	public static final String PARENT = "aas:parent";
	public static final String DESCRIPTION = "aas:description";

	/**
	 * Populates a given Referable object with the data form the given XML
	 * 
	 * @param xmlObject the XML map containing the tag relevant for IReferable
	 * @param referable the Referable object to be populated
	 */
	@SuppressWarnings("unchecked")
	public static void populateReferable(Map<String, Object> xmlObject, Referable referable) {
		String idShort = XMLHelper.getString(xmlObject.get(ID_SHORT));
		String category = XMLHelper.getString(xmlObject.get(CATEGORY));
		
		LangStrings description = parseDescription(xmlObject);
		
		Reference parent = ReferenceXMLConverter.parseReference((Map<String, Object>) xmlObject.get(PARENT));
		if (Strings.isNullOrEmpty(idShort)) {
			throw new RuntimeException("Invalid XML of Referable. No valid idShort is present. " + xmlObject.toString());
		}
		referable.setIdShort(idShort);
		
		if (!Strings.isNullOrEmpty(category)) {
			referable.setCategory(category);
		}
		if (description != null) {
			referable.setDescription(description);
		}
		if (parent != null) {
			referable.setParent(parent);
		}
	}
	

	/**
	 * Parses the &lt;aas:description&gt; tag
	 * 
	 * @param xmlObject the XML map containing the &lt;aas:description&gt; tag
	 * @return a LangStrings Object parsed form the XML
	 */
	@SuppressWarnings("unchecked")
	private static LangStrings parseDescription(Map<String, Object> xmlObject) {
		
		Map<String, Object> descObj = (Map<String, Object>) xmlObject.get(DESCRIPTION);
		if (descObj == null) {
			return null;
		}
		
		return LangStringsXMLConverter.parseLangStrings(descObj);
	}
	
	
	
	
	
	/**
	 * Populates a given XML map with the data form a given IReferable object<br>
	 * Creates the relevant tags in the given root
	 * 
	 * @param document the XML document
	 * @param root the XML root Element to be populated
	 * @param referable the IReferable object to be converted to XML
	 */
	public static void populateReferableXML(Document document, Element root, IReferable referable) {
		if (referable.getIdShort() != null) {
			Element idShortElem = document.createElement(ID_SHORT);
			idShortElem.appendChild(document.createTextNode(referable.getIdShort()));
			root.appendChild(idShortElem);
		}
		
		if (referable.getCategory() != null) {
			Element categoryElem = document.createElement(CATEGORY);
			categoryElem.appendChild(document.createTextNode(referable.getCategory()));
			root.appendChild(categoryElem);
		}
		
		if(referable.getDescription() != null && !referable.getDescription().isEmpty()) {
			Element descriptionRoot = document.createElement(DESCRIPTION);
			LangStringsXMLConverter.buildLangStringsXML(document, descriptionRoot, referable.getDescription());
			root.appendChild(descriptionRoot);
		}
		
		if(referable.getParent() != null) {
			Element xmlParent = ReferenceXMLConverter.buildReferenceXML(document, referable.getParent());
			Element parentElem = document.createElement(PARENT);
			parentElem.appendChild(xmlParent);
			root.appendChild(parentElem);
		}
		
	}	

}
