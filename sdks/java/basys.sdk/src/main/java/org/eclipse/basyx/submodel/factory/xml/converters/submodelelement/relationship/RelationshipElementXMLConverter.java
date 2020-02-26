package org.eclipse.basyx.submodel.factory.xml.converters.submodelelement.relationship;

import java.util.Map;

import org.eclipse.basyx.submodel.factory.xml.converters.reference.ReferenceXMLConverter;
import org.eclipse.basyx.submodel.factory.xml.converters.submodelelement.SubmodelElementXMLConverter;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.relationship.IRelationshipElement;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.relationship.RelationshipElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Parses &lt;aas:relationshipElement&gt; and builds the RelationshipElement object from it <br>
 * Builds &lt;aas:relationshipElement&gt; form a given RelationshipElement object
 * 
 * @author conradi
 *
 */
public class RelationshipElementXMLConverter extends SubmodelElementXMLConverter {
	
	public static final String RELATIONSHIP_ELEMENT = "aas:relationshipElement";
	public static final String FIRST = "aas:first";
	public static final String SECOND = "aas:second";
	
	
	/**
	 * Parses a Map containing the content of XML tag &lt;aas:relationshipElement&gt;
	 * 
	 * @param xmlObject the Map with the content of XML tag &lt;aas:relationshipElement&gt;
	 * @return the parsed RelationshipElement
	 */
	@SuppressWarnings("unchecked")
	public static RelationshipElement parseRelationshipElement(Map<String, Object> xmlObject) {
		Map<String, Object> firstMap = (Map<String, Object>) xmlObject.get(FIRST);
		Reference first = ReferenceXMLConverter.parseReference(firstMap);
		
		Map<String, Object> secondMap = (Map<String, Object>) xmlObject.get(SECOND);
		Reference second = ReferenceXMLConverter.parseReference(secondMap);
		
		RelationshipElement relElement = new RelationshipElement(first, second);
		populateSubmodelElement(xmlObject, relElement);
		return relElement;
	}
	
	
	
	
	/**
	 * Builds the &lt;aas:relationshipElement&gt; XML tag for a RelationshipElement
	 * 
	 * @param document the XML document
	 * @param relElem the IRelationshipElement to build the XML for
	 * @return the &lt;aas:relationshipElement&gt; XML tag for the given RelationshipElement
	 */
	public static Element buildRelationshipElement(Document document, IRelationshipElement relElem) {
		Element relElemRoot = document.createElement(RELATIONSHIP_ELEMENT);
		
		populateSubmodelElement(document, relElemRoot, relElem);

		IReference first = relElem.getFirst();
		if(first != null) {
			Element derivedFromRoot = document.createElement(FIRST);
			derivedFromRoot.appendChild(ReferenceXMLConverter.buildReferenceXML(document, first)); 
			relElemRoot.appendChild(derivedFromRoot);
		}		
		IReference second = relElem.getSecond();
		if(second != null) {
			Element derivedFromRoot = document.createElement(SECOND);
			derivedFromRoot.appendChild(ReferenceXMLConverter.buildReferenceXML(document, second)); 
			relElemRoot.appendChild(derivedFromRoot);
		}
		
		return relElemRoot;
	}
}
