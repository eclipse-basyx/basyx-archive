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
	 * Populates a given IReferable object with the data form the given XML
	 * 
	 * @param xmlObject the XML map containing the tag relevant for IReferable
	 * @param referable the IReferable object to be populated -treated as Map here-
	 */
	@SuppressWarnings("unchecked")
	public static void populateReferable(Map<String, Object> xmlObject, Map<String, Object> referable) {
		//The IReferable object has to be treated as Map here, as the Interface has no Setters

		String idShort = XMLHelper.getString(xmlObject.get(ID_SHORT));
		String category = XMLHelper.getString(xmlObject.get(CATEGORY));
		
		LangStrings description = parseDescription(xmlObject);
		
		//FIXME Parent in xml schema is currently a String, but it should be a Reference
		Reference parent = ReferenceXMLConverter.parseReference((Map<String, Object>) xmlObject.get(PARENT));
		
		referable.put(Referable.IDSHORT, idShort);
		referable.put(Referable.CATEGORY, category);
		referable.put(Referable.DESCRIPTION, description);
		referable.put(Referable.PARENT, parent);
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
			return new LangStrings();
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
		
		//FIXME Parent in xml schema is currently a String, but it should be a Reference
		if(referable.getParent() != null) {
			Element xmlParent = ReferenceXMLConverter.buildReferenceXML(document, referable.getParent());
			Element parentElem = document.createElement(PARENT);
			parentElem.appendChild(xmlParent);
			root.appendChild(parentElem);
		}
		Element descriptionRoot = document.createElement(DESCRIPTION);
		LangStringsXMLConverter.buildLangStringsXML(document, descriptionRoot, referable.getDescription());
		root.appendChild(descriptionRoot);
	}	

}