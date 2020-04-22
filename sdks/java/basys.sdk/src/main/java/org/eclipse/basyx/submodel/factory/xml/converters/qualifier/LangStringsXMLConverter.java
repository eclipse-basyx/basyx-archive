package org.eclipse.basyx.submodel.factory.xml.converters.qualifier;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.submodel.factory.xml.XMLHelper;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Handles the conversion between a LangStrings object and the XML tag &lt;aas:langString&gt; in both directions
 * 
 * @author conradi
 *
 */
public class LangStringsXMLConverter {
	
	public static final String LANG = "lang";
	public static final String LANG_STRING = "aas:langString";
	
	
	/**
	 * Parses the LangStrings object from XML
	 * 
	 * @param xmlObject the XML map containing the &lt;aas:langString&gt; tags
	 * @return the parsed LangStrings object
	 */
	public static LangStrings parseLangStrings(Map<String, Object> xmlObject) {
		return parseLangStrings(xmlObject, LANG_STRING);
	}

	/**
	 * Parses the LangStrings object from XML with a custom lang string xml tag
	 * 
	 * @param xmlObject the XML map containing the &lt;langString&gt; tags
	 * @param tagName   the custom &lt;*langString*&gt; tagName
	 * @return the parsed LangStrings object
	 */
	public static LangStrings parseLangStrings(Map<String, Object> xmlObject, String tagName) {
		LangStrings langStrings = new LangStrings();
		
		if(xmlObject != null) {
			List<Map<String, Object>> xmlLangStrings = XMLHelper.getList(xmlObject.get(tagName));
			for (Map<String, Object> xmlLangString : xmlLangStrings) {
				String text = XMLHelper.getString(xmlLangString.get(XMLHelper.TEXT));
				String lang = XMLHelper.getString(xmlLangString.get(LANG));
				langStrings.add(lang, text);
			}
		}
		return langStrings;
	}
	
	
	
	
	/**
	 * Builds XML form a given LangStrings object
	 * 
	 * @param document the XML document
	 * @param root the root Element where the &lt;aas:langString&gt; tags should be in
	 * @param langStrings the LangStrings object to be converted to XML
	 */
	public static void buildLangStringsXML(Document document, Element root, LangStrings langStrings) {
		buildLangStringsXML(document, root, LANG_STRING, langStrings);
	}

	/**
	 * Builds XML form a given LangStrings object with a custom langString tagName
	 * 
	 * @param document the XML document
	 * @param root the root Element where the &lt;langString&gt; tags should be in
	 * @param tagName the custom &lt;*langString*&gt; tagName
	 * @param langStrings the LangStrings object to be converted to XML
	 */
	public static void buildLangStringsXML(Document document, Element root, String tagName, LangStrings langStrings) {
		if (langStrings != null) {
			Set<String> languages = langStrings.getLanguages();
			for(String language: languages) {
				Element langStringRoot = document.createElement(tagName);
				String text = langStrings.get(language);

				langStringRoot.setAttribute(LANG, language);
				langStringRoot.appendChild(document.createTextNode(text));
					
				root.appendChild(langStringRoot);
			}
		}
	}
}
