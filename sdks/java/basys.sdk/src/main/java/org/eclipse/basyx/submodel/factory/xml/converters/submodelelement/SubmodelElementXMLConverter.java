package org.eclipse.basyx.submodel.factory.xml.converters.submodelelement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.basyx.submodel.factory.xml.XMLHelper;
import org.eclipse.basyx.submodel.factory.xml.converters.qualifier.HasDataSpecificationXMLConverter;
import org.eclipse.basyx.submodel.factory.xml.converters.qualifier.HasSemanticsXMLConverter;
import org.eclipse.basyx.submodel.factory.xml.converters.qualifier.ReferableXMLConverter;
import org.eclipse.basyx.submodel.factory.xml.converters.qualifier.haskind.HasKindXMLConverter;
import org.eclipse.basyx.submodel.factory.xml.converters.qualifier.qualifiable.QualifiableXMLConverter;
import org.eclipse.basyx.submodel.factory.xml.converters.submodelelement.dataelement.BlobXMLConverter;
import org.eclipse.basyx.submodel.factory.xml.converters.submodelelement.dataelement.FileXMLConverter;
import org.eclipse.basyx.submodel.factory.xml.converters.submodelelement.dataelement.MultiLanguagePropertyXMLConverter;
import org.eclipse.basyx.submodel.factory.xml.converters.submodelelement.dataelement.PropertyXMLConverter;
import org.eclipse.basyx.submodel.factory.xml.converters.submodelelement.dataelement.RangeXMLConverter;
import org.eclipse.basyx.submodel.factory.xml.converters.submodelelement.dataelement.ReferenceElementXMLConverter;
import org.eclipse.basyx.submodel.factory.xml.converters.submodelelement.entity.EntityXMLConverter;
import org.eclipse.basyx.submodel.factory.xml.converters.submodelelement.event.BasicEventXMLConverter;
import org.eclipse.basyx.submodel.factory.xml.converters.submodelelement.operation.OperationXMLConverter;
import org.eclipse.basyx.submodel.factory.xml.converters.submodelelement.relationship.RelationshipElementXMLConverter;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IBlob;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IFile;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IMultiLanguageProperty;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IProperty;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IRange;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IReferenceElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.entity.IEntity;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.event.IBasicEvent;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.relationship.IRelationshipElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.Blob;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.File;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.MultiLanguageProperty;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.ReferenceElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.range.Range;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.entity.Entity;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.event.BasicEvent;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.relationship.RelationshipElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Parses &lt;aas:submodelElements&gt; and builds the SubmodelElement objects from it <br>
 * Builds &lt;aas:submodelElements&gt; from a given Collection of SubmodelElement
 * 
 * @author conradi
 *
 */
public class SubmodelElementXMLConverter {
	
	public static final String SUBMODEL_ELEMENTS = "aas:submodelElements";
	public static final String SUBMODEL_ELEMENT = "aas:submodelElement";
	public static final String VALUE = "aas:value";
	public static final String VALUE_TYPE = "aas:valueType";
	public static final String VALUE_ID = "aas:valueId";
	public static final String MIME_TYPE = "aas:mimeType";

	
	/**
	 * Parses a given Map containing the XML tag &lt;aas:submodelElements&gt;
	 * 
	 * @param xmlSubmodelElements a Map of the XML tag &lt;aas:submodelElements&gt;
	 * @return a List with the ISubmodelElement Objects parsed from the XML  
	 */
	@SuppressWarnings("unchecked")
	public static List<ISubmodelElement> parseSubmodelElements(Map<String, Object> xmlSubmodelElements) {
		Map<String, Object> xmlSubmodelElementsMap = (Map<String, Object>) xmlSubmodelElements.get(SUBMODEL_ELEMENTS);
		return getSubmodelElements(xmlSubmodelElementsMap);
	}
	

	/**
	 * Parses the individual &lt;aas:submodelElement&gt; tags from the Map
	 * 
	 * @param xmlObject a Map of &lt;aas:submodelElement&gt; tags
	 * @return a List with the ISubmodelElement Objects parsed from the XML 
	 */
	protected static List<ISubmodelElement> getSubmodelElements(Map<String, Object> xmlObject) {
		List<ISubmodelElement> submodelElemList = new ArrayList<>();
		if(xmlObject == null) return submodelElemList;
		
		List<Map<String, Object>> xmlSubmodelElemList = XMLHelper.getList(xmlObject.get(SUBMODEL_ELEMENT));
		for (Map<String, Object> xmlSubmodelElem: xmlSubmodelElemList) {
			submodelElemList.add(getSubmodelElement(xmlSubmodelElem));
		}
		
		return submodelElemList;
	}
	
	
	/**
	 * Parses the one SubmodelElement
	 * 
	 * @param xmlObject a Map of the SubmodelElement XML
	 * @return a List with the ISubmodelElement Objects parsed from the XML 
	 */
	@SuppressWarnings("unchecked")
	protected static SubmodelElement getSubmodelElement(Map<String, Object> xmlObject) {
		
		if (xmlObject.containsKey(PropertyXMLConverter.PROPERTY)) {
			xmlObject = (Map<String, Object>) xmlObject.get(PropertyXMLConverter.PROPERTY);
			return PropertyXMLConverter.parsePropery(xmlObject);
		}
		else if (xmlObject.containsKey(BasicEventXMLConverter.BASIC_EVENT)) {
			xmlObject = (Map<String, Object>) xmlObject.get(BasicEventXMLConverter.BASIC_EVENT);
			return BasicEventXMLConverter.parseBasicEvent(xmlObject);
		}
		else if (xmlObject.containsKey(EntityXMLConverter.ENTITY)) {
			xmlObject = (Map<String, Object>) xmlObject.get(EntityXMLConverter.ENTITY);
			return EntityXMLConverter.parseEntity(xmlObject);
		}
		else if (xmlObject.containsKey(MultiLanguagePropertyXMLConverter.MULTI_LANGUAGE_PROPERTY)) {
			xmlObject = (Map<String, Object>) xmlObject.get(
					MultiLanguagePropertyXMLConverter.MULTI_LANGUAGE_PROPERTY);
			return MultiLanguagePropertyXMLConverter.parseMultiLanguageProperty(xmlObject);
		}
		else if (xmlObject.containsKey(RangeXMLConverter.RANGE)) {
			xmlObject = (Map<String, Object>) xmlObject.get(RangeXMLConverter.RANGE);
			return RangeXMLConverter.parseRange(xmlObject);
		}
		else if (xmlObject.containsKey(FileXMLConverter.FILE)) {
			xmlObject = (Map<String, Object>) xmlObject.get(FileXMLConverter.FILE);
			return FileXMLConverter.parseFile(xmlObject);
		}
		else if(xmlObject.containsKey(BlobXMLConverter.BLOB)) {
			xmlObject = (Map<String, Object>) xmlObject.get(BlobXMLConverter.BLOB);
			return BlobXMLConverter.parseBlob(xmlObject);
		}
		else if(xmlObject.containsKey(ReferenceElementXMLConverter.REFERENCE_ELEMENT)) {
			xmlObject = (Map<String, Object>) xmlObject.get(
					ReferenceElementXMLConverter.REFERENCE_ELEMENT);
			return ReferenceElementXMLConverter.parseReferenceElement(xmlObject);
		}
		else if(xmlObject.containsKey(SubmodelElementCollectionXMLConverter.SUBMODEL_ELEMENT_COLLECTION)) {
			xmlObject = (Map<String, Object>) xmlObject.get(
					SubmodelElementCollectionXMLConverter.SUBMODEL_ELEMENT_COLLECTION);
			return SubmodelElementCollectionXMLConverter.parseSubmodelElementCollection(xmlObject);
		}
		else if(xmlObject.containsKey(RelationshipElementXMLConverter.RELATIONSHIP_ELEMENT)) {
			xmlObject = (Map<String, Object>) xmlObject.get(
					RelationshipElementXMLConverter.RELATIONSHIP_ELEMENT);
			return RelationshipElementXMLConverter.parseRelationshipElement(xmlObject);
		}
		else if(xmlObject.containsKey(OperationXMLConverter.OPERATION)) {
			xmlObject = (Map<String, Object>) xmlObject.get(OperationXMLConverter.OPERATION);
			return OperationXMLConverter.parseOperation(xmlObject);
		}
		
		return null;
	}


	/**
	 * Populates a SubmodelElement with the standard information from a XML tag 
	 * 
	 * @param xmlObject the Map with the content of XML tag &lt;aas:blob&gt;
	 * @param submodelElement the SubmodelElement to be populated
	 */
	@SuppressWarnings("unchecked")
	protected static void populateSubmodelElement(Map<String, Object> xmlObject, ISubmodelElement submodelElement) {
		ReferableXMLConverter.populateReferable(xmlObject, (Map<String, Object>) submodelElement);
		QualifiableXMLConverter.populateQualifiable(xmlObject, (Map<String, Object>) submodelElement);
		HasDataSpecificationXMLConverter.populateHasDataSpecification(xmlObject, (Map<String, Object>) submodelElement);
		HasSemanticsXMLConverter.populateHasSemantics(xmlObject, (Map<String, Object>) submodelElement);
		HasKindXMLConverter.populateHasKind(xmlObject, (Map<String, Object>) submodelElement);
	}
	

	
	
	/**
	 * Builds the SubmodelElemensts XML tag &lt;aas:submodelElements&gt;
	 * 
	 * @param document the XML document
	 * @param submodelElements the SubmodelElements of the SubModel 
	 * @return XML Element with the root tag &lt;aas:submodelElements&gt;
	 */
	public static Element buildSubmodelElementsXML(Document document, Collection<ISubmodelElement> submodelElements) {
		Element xmlSubmodelElements = document.createElement(SUBMODEL_ELEMENTS);
		buildSubmodelElements(document, xmlSubmodelElements, submodelElements);
		return xmlSubmodelElements;
	}
	
	
	/**
	 * Builds the individual SubmodelElement XML tags from a List of SubmodelElements and <br>
	 * populates the given root Element with them
	 * 
	 * @param document the XML document
	 * @param root the XML Element &lt;aas:submodelElements&gt;
	 * @param submodelElements a List of SubmodelElements
	 */
	protected static void buildSubmodelElements(Document document, Element root, Collection<ISubmodelElement> submodelElements) {
		for (ISubmodelElement submodelElement : submodelElements) {
			Element xmlSubmodelElement = document.createElement(SUBMODEL_ELEMENT);
			Element elem = buildSubmodelElement(document, submodelElement);
			xmlSubmodelElement.appendChild(elem);
			root.appendChild(xmlSubmodelElement);
		}
	}

	
	/**
	 * Builds one SubmodelElement XML tag
	 * 
	 * @param document the XML document
	 * @param submodelElement the SubmodelElement to build the XML for
	 * @return the SubmodelElement XML tag
	 */
	protected static Element buildSubmodelElement(Document document, ISubmodelElement submodelElement) {
		String type = submodelElement.getModelType();	
		
		switch (type) {
			case Property.MODELTYPE:
				return PropertyXMLConverter.buildProperty(document, (IProperty) submodelElement);
			case BasicEvent.MODELTYPE:
				return BasicEventXMLConverter.buildBasicEvent(document, (IBasicEvent) submodelElement);
			case MultiLanguageProperty.MODELTYPE:
				return MultiLanguagePropertyXMLConverter.buildMultiLanguageProperty(
						document, (IMultiLanguageProperty) submodelElement);
			case Range.MODELTYPE:
				return RangeXMLConverter.buildRange(document, (IRange) submodelElement);
			case Entity.MODELTYPE:
				return EntityXMLConverter.buildEntity(document, (IEntity) submodelElement);
			case File.MODELTYPE:
				return FileXMLConverter.buildFile(document, (IFile) submodelElement);
			case Blob.MODELTYPE:
				return BlobXMLConverter.buildBlob(document, (IBlob) submodelElement);
			case ReferenceElement.MODELTYPE:
				return ReferenceElementXMLConverter.buildReferenceElement(
						document, (IReferenceElement) submodelElement);
			case SubmodelElementCollection.MODELTYPE:
				return SubmodelElementCollectionXMLConverter.buildSubmodelElementCollection(
						document, (ISubmodelElementCollection) submodelElement);
			case RelationshipElement.MODELTYPE:
				return RelationshipElementXMLConverter.buildRelationshipElement(
						document, (IRelationshipElement) submodelElement);
			case Operation.MODELTYPE:
				return OperationXMLConverter.buildOperation(document, (IOperation) submodelElement);
			default:
				return null;
		}
	}	


	/**
	 * Populates a SubmodelElement XML tag with the standard information of a SubmodelElement 
	 * 
	 * @param document the XML document
	 * @param root the root Element of the SubmodelElement
	 * @param submodelElement the SubmodelElement
	 */
	protected static void populateSubmodelElement(Document document, Element root, ISubmodelElement submodelElement) {
		ReferableXMLConverter.populateReferableXML(document, root, submodelElement);
		HasKindXMLConverter.populateHasKindXML(document, root, submodelElement);
		HasSemanticsXMLConverter.populateHasSemanticsXML(document, root, submodelElement);
		QualifiableXMLConverter.populateQualifiableXML(document, root, submodelElement);
		HasDataSpecificationXMLConverter.populateHasDataSpecificationXML(document, root, submodelElement);
	}
}