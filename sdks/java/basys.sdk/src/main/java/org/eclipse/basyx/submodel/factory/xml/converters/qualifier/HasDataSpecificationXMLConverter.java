package org.eclipse.basyx.submodel.factory.xml.converters.qualifier;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.submodel.factory.xml.XMLHelper;
import org.eclipse.basyx.submodel.factory.xml.converters.reference.ReferenceXMLConverter;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.IHasDataSpecification;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.HasDataSpecification;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Handles the conversion between an IHasDataSpecification object and the XML tag<br>
 * &lt;aas:embeddedDataSpecification&gt; in both directions
 * 
 * @author conradi
 *
 */
public class HasDataSpecificationXMLConverter {
	
	public static final String EMBEDDED_DATA_SPECIFICATION = "aas:embeddedDataSpecification";
	public static final String HAS_DATA_SPECIFICATION = "aas:hasDataSpecification";
	
	
	/**
	 * Populates a given IHasDataSpecification object with the data form the given XML
	 * 
	 * @param xmlObject the XML map containing the &lt;aas:embeddedDataSpecification&gt; tag
	 * @param hasDataSpecification the IHasDataSpecification object to be populated -treated as Map here-
	 */
	public static void populateHasDataSpecification(Map<String, Object> xmlObject, Map<String, Object> hasDataSpecification) {
		//The IHasDataSpecification object has to be treated as Map here, as the Interface has no Setters
		
		if(xmlObject == null || hasDataSpecification == null) return;
		
		Object xmlDataSpecObj = xmlObject.get(EMBEDDED_DATA_SPECIFICATION);
		
		HashSet<IReference> refSet = new HashSet<>();
		
		List<Map<String, Object>> xmlSpecList = XMLHelper.getList(xmlDataSpecObj);
		for (Map<String, Object> xmlSpec : xmlSpecList) {
			refSet.add(parseReference(xmlSpec));
		}
		hasDataSpecification.put(HasDataSpecification.HASDATASPECIFICATION, refSet);
	}

	
	/**
	 * Parses a Reference Object from the &lt;aas:hasDataSpecification&gt; XML tag
	 * 
	 * @param xmlObject the XML map containing the &lt;aas:hasDataSpecification&gt; XML tag
	 * @return the parsed Reference object
	 */
	@SuppressWarnings("unchecked")
	private static Reference parseReference(Map<String, Object> xmlObject) {
		Map<String, Object> xmlSemanticIdObj = (Map<String, Object>) xmlObject.get(HAS_DATA_SPECIFICATION);
		return ReferenceXMLConverter.parseReference(xmlSemanticIdObj);
	}
	
	
	
	
	

	/**
	 * Populates a given XML map with the data form a given IHasDataSpecification object<br>
	 * Creates the &lt;aas:embeddedDataSpecification&gt; tag in the given root
	 * 
	 * @param document the XML document
	 * @param root the XML root Element to be populated
	 * @param hasDataSpecification the IHasDataSpecification object to be converted to XML
	 */
	public static void populateHasDataSpecificationXML(Document document, Element root, IHasDataSpecification hasDataSpecification) {
		Set<IReference> references = hasDataSpecification.getDataSpecificationReferences();
		
		if(references != null && references.size() > 0) {
			Element embeddedDataSpecRoot = document.createElement(EMBEDDED_DATA_SPECIFICATION);
			Element dataSpecRoot = document.createElement(HAS_DATA_SPECIFICATION);
			embeddedDataSpecRoot.appendChild(dataSpecRoot);
			dataSpecRoot.appendChild(ReferenceXMLConverter.buildReferencesXML(document, references)); 
			root.appendChild(embeddedDataSpecRoot);
		}
	}

}
