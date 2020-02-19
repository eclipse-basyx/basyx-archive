package org.eclipse.basyx.submodel.factory.xml.converters.submodelelement;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.basyx.submodel.factory.xml.XMLHelper;
import org.eclipse.basyx.submodel.factory.xml.converters.qualifier.HasDataSpecificationXMLConverter;
import org.eclipse.basyx.submodel.factory.xml.converters.qualifier.HasSemanticsXMLConverter;
import org.eclipse.basyx.submodel.factory.xml.converters.qualifier.LangStringsXMLConverter;
import org.eclipse.basyx.submodel.factory.xml.converters.qualifier.ReferableXMLConverter;
import org.eclipse.basyx.submodel.factory.xml.converters.qualifier.haskind.HasKindXMLConverter;
import org.eclipse.basyx.submodel.factory.xml.converters.qualifier.qualifiable.QualifiableXMLConverter;
import org.eclipse.basyx.submodel.factory.xml.converters.reference.ReferenceXMLConverter;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IBlob;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IFile;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IMultiLanguageProperty;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IRange;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IReferenceElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.property.ISingleProperty;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.entity.EntityType;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.entity.IEntity;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.event.IBasicEvent;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperationVariable;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.relationship.IRelationshipElement;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.Blob;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.File;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.MultiLanguageProperty;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.Range;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.ReferenceElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetypedef.PropertyValueTypeDefHelper;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.entity.Entity;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.event.BasicEvent;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.OperationVariable;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.relationship.RelationshipElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Parses &lt;aas:submodelElements&gt; and builds the SubmodelElement objects from it <br>
 * Builds &lt;aas:submodelElements&gt; form a given Collection of SubmodelElement
 * 
 * @author conradi
 *
 */
public class SubmodelElementXMLConverter {
	
	private static Logger logger = LoggerFactory.getLogger(SubmodelElementXMLConverter.class);
	
	public static final String SUBMODEL_ELEMENTS = "aas:submodelElements";
	public static final String SUBMODEL_ELEMENT = "aas:submodelElement";
	public static final String OPERATION = "aas:operation";
	public static final String INPUT_VARIABLE = "aas:inputVariable";
	public static final String OUTPUT_VARIABLE = "aas:outputVariable";
	public static final String INOUTPUT_VARIABLE = "aas:inoutputVariable";
	public static final String OPERATION_VARIABLE = "aas:operationVariable";
	public static final String VALUE = "aas:value";
	public static final String RELATIONSHIP_ELEMENT = "aas:relationshipElement";
	public static final String FIRST = "aas:first";
	public static final String SECOND = "aas:second";
	public static final String SUBMODEL_ELEMENT_COLLECTION = "aas:submodelElementCollection";
	public static final String ORDERED = "aas:ordered";
	public static final String ALLOW_DUPLICATES = "aas:allowDuplicates";
	public static final String PROPERTY = "aas:property";
	public static final String VALUE_TYPE = "aas:valueType";
	public static final String FILE = "aas:file";
	public static final String MIME_TYPE = "aas:mimeType";
	public static final String BLOB = "aas:blob";
	public static final String REFERENCE_ELEMENT = "aas:referenceElement";
	public static final String EVENT = "aas:event";
	public static final String BASIC_EVENT = "aas:basicEvent";
	public static final String OBSERVED = "aas:observed";
	public static final String ENTITY = "aas:entity";
	public static final String ENTITY_TYPE = "aas:entityType";
	public static final String STATEMENTS = "aas:statements";
	public static final String ASSET_REF = "aas:assetRef";
	public static final String MULTI_LANGUAGE_PROPERTY = "aas:multiLanguageProperty";
	public static final String VALUE_ID = "aas:valueId";
	public static final String RANGE = "aas:range";
	public static final String MIN = "aas:min";
	public static final String MAX = "aas:max";
	
	

	/**
	 * Parses a given Map containing the XML tag &lt;aas:submodelElements&gt;
	 * 
	 * @param xmlSubmodelElements a Map of the XML tag &lt;aas:submodelElements&gt;
	 * @return a List with the ISubmodelElement Objects parsed form the XML  
	 */
	@SuppressWarnings("unchecked")
	public static List<ISubmodelElement> parseSubmodelElements(Map<String, Object> xmlSubmodelElements) {
		Map<String, Object> xmlSubmodelElementsMap = (Map<String, Object>) xmlSubmodelElements.get(SUBMODEL_ELEMENTS);
		return getSubmodelElements(xmlSubmodelElementsMap);
	}
	

	/**
	 * Parses the individual &lt;aas:submodelElement&gt; tags form the Map
	 * 
	 * @param xmlObject a Map of &lt;aas:submodelElement&gt; tags
	 * @return a List with the ISubmodelElement Objects parsed form the XML 
	 */
	private static List<ISubmodelElement> getSubmodelElements(Map<String, Object> xmlObject) {
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
	 * @return a List with the ISubmodelElement Objects parsed form the XML 
	 */
	@SuppressWarnings("unchecked")
	private static SubmodelElement getSubmodelElement(Map<String, Object> xmlObject) {
		if (xmlObject.containsKey(PROPERTY)) {
			xmlObject = (Map<String, Object>) xmlObject.get(PROPERTY);
			return parsePropery(xmlObject);
		}
		else if (xmlObject.containsKey(BASIC_EVENT)) {
			xmlObject = (Map<String, Object>) xmlObject.get(BASIC_EVENT);
			return parseBasicEvent(xmlObject);
		}
		else if (xmlObject.containsKey(ENTITY)) {
			xmlObject = (Map<String, Object>) xmlObject.get(ENTITY);
			return parseEntity(xmlObject);
		}
		else if (xmlObject.containsKey(MULTI_LANGUAGE_PROPERTY)) {
			xmlObject = (Map<String, Object>) xmlObject.get(MULTI_LANGUAGE_PROPERTY);
			return parseMultiLanguageProperty(xmlObject);
		}
		else if (xmlObject.containsKey(RANGE)) {
			xmlObject = (Map<String, Object>) xmlObject.get(RANGE);
			return parseRange(xmlObject);
		}
		else if (xmlObject.containsKey(FILE)) {
			xmlObject = (Map<String, Object>) xmlObject.get(FILE);
			return parseFile(xmlObject);
		}
		else if(xmlObject.containsKey(BLOB)) {
			xmlObject = (Map<String, Object>) xmlObject.get(BLOB);
			return parseBlob(xmlObject);
		}
		else if(xmlObject.containsKey(REFERENCE_ELEMENT)) {
			xmlObject = (Map<String, Object>) xmlObject.get(REFERENCE_ELEMENT);
			return parseReferenceElement(xmlObject);
		}
		else if(xmlObject.containsKey(SUBMODEL_ELEMENT_COLLECTION)) {
			xmlObject = (Map<String, Object>) xmlObject.get(SUBMODEL_ELEMENT_COLLECTION);
			return parseSubmodelElementCollection(xmlObject);
		}
		else if(xmlObject.containsKey(RELATIONSHIP_ELEMENT)) {
			xmlObject = (Map<String, Object>) xmlObject.get(RELATIONSHIP_ELEMENT);
			return parseRelationshipElement(xmlObject);
		}
		else if(xmlObject.containsKey(OPERATION)) {
			xmlObject = (Map<String, Object>) xmlObject.get(OPERATION);
			return parseOperation(xmlObject);
		}
		return null;
	}


	/**
	 * Parses a Map containing the content of XML tag &lt;aas:property&gt;
	 * 
	 * @param xmlObject the Map with the content of XML tag &lt;aas:property&gt;
	 * @return the parsed Property
	 */
	private static Property parsePropery(Map<String, Object> xmlObject) {
		
		Property property = new Property();
		populateSubmodelElement(xmlObject, property);
		
		String valueType = XMLHelper.getString(xmlObject.get(VALUE_TYPE));
		String value = XMLHelper.getString(xmlObject.get(VALUE));
		
		property.set(value);
		property.setValueType(PropertyValueTypeDefHelper.fromName(valueType));
		
		//FIXME the XML-ELement valueId has no corresponding field in Property
		return property;
	}
	
	
	/**
	 * Parses a Map containing the content of XML tag &lt;aas:file&gt;
	 * 
	 * @param xmlObject the Map with the content of XML tag &lt;aas:file&gt;
	 * @return the parsed File
	 */
	private static File parseFile(Map<String, Object> xmlObject) {
		String mimeType = XMLHelper.getString(xmlObject.get(MIME_TYPE));
		String value = XMLHelper.getString(xmlObject.get(VALUE));
		File file = new File(value, mimeType);
		populateSubmodelElement(xmlObject, file);
		return file;
	}
	
	
	/**
	 * Parses a Map containing the content of XML tag &lt;aas:blob&gt;
	 * 
	 * @param xmlObject the Map with the content of XML tag &lt;aas:blob&gt;
	 * @return the parsed Blob
	 */
	private static Blob parseBlob(Map<String, Object> xmlObject) {
		
		String mimeType = XMLHelper.getString(xmlObject.get(MIME_TYPE));
		String value = XMLHelper.getString(xmlObject.get(VALUE));
		byte[] valueBytes = Base64.getDecoder().decode(value.getBytes());
		
		Blob blob = new Blob(valueBytes, mimeType);
		populateSubmodelElement(xmlObject, blob);
		return blob;
	}
	
	
	/**
	 * Parses a Map containing the content of XML tag &lt;aas:referenceElement&gt;
	 * 
	 * @param xmlObject the Map with the content of XML tag &lt;aas:referenceElement&gt;
	 * @return the parsed ReferenceElement
	 */
	@SuppressWarnings("unchecked")
	private static ReferenceElement parseReferenceElement(Map<String, Object> xmlObject) {
		Map<String, Object> valueElementObj = (Map<String, Object>) xmlObject.get(VALUE);
		ReferenceElement refElement = new ReferenceElement(ReferenceXMLConverter.parseReference(valueElementObj));
		populateSubmodelElement(xmlObject, refElement);
		return refElement;
	}
	
	
	/**
	 * Parses a Map containing the content of XML tag &lt;aas:submodelElementCollection&gt;
	 * 
	 * @param xmlObject the Map with the content of XML tag &lt;aas:submodelElementCollection&gt;
	 * @return the parsed SubmodelElementCollection
	 */
	@SuppressWarnings("unchecked")
	private static SubmodelElementCollection parseSubmodelElementCollection(Map<String, Object> xmlObject) {
		boolean ordered = Boolean.valueOf(XMLHelper.getString(xmlObject.get(ORDERED)));
		boolean allowDuplicates = Boolean.valueOf(XMLHelper.getString(xmlObject.get(ALLOW_DUPLICATES)));
		Map<String, Object> valueMap = (Map<String, Object>) xmlObject.get(VALUE);
		List<ISubmodelElement> submodelElements = getSubmodelElements(valueMap);
		SubmodelElementCollection smElemColl = new SubmodelElementCollection(submodelElements, ordered, allowDuplicates);
		populateSubmodelElement(xmlObject, smElemColl);
		return smElemColl;
	}
	
	
	/**
	 * Parses a Map containing the content of XML tag &lt;aas:relationshipElement&gt;
	 * 
	 * @param xmlObject the Map with the content of XML tag &lt;aas:relationshipElement&gt;
	 * @return the parsed RelationshipElement
	 */
	@SuppressWarnings("unchecked")
	private static RelationshipElement parseRelationshipElement(Map<String, Object> xmlObject) {
		Map<String, Object> firstMap = (Map<String, Object>) xmlObject.get(FIRST);
		Reference first = ReferenceXMLConverter.parseReference(firstMap);
		
		Map<String, Object> secondMap = (Map<String, Object>) xmlObject.get(SECOND);
		Reference second = ReferenceXMLConverter.parseReference(secondMap);
		
		RelationshipElement relElement = new RelationshipElement(first, second);
		populateSubmodelElement(xmlObject, relElement);
		return relElement;
	}
	

	/**
	 * Parses a Map containing the content of XML tag &lt;aas:operation&gt;
	 * 
	 * @param xmlObject the Map with the content of XML tag &lt;aas:operation&gt;
	 * @return the parsed Operation
	 */
	@SuppressWarnings("unchecked")
	private static Operation parseOperation(Map<String, Object> xmlObject) {
		
		Map<String, Object> inObj = (Map<String, Object>) xmlObject.get(INPUT_VARIABLE);
		List<Map<String, Object>> xmlOpVars = XMLHelper.getList(inObj.get(OPERATION_VARIABLE));
		List<OperationVariable> inList = new ArrayList<>();
		for(Map<String, Object> map : xmlOpVars) {
			inList.add(parseOperationVariable(map));
		}
		
		Map<String, Object> outObj = (Map<String, Object>) xmlObject.get(OUTPUT_VARIABLE);
		xmlOpVars = XMLHelper.getList(outObj.get(OPERATION_VARIABLE));
		List<OperationVariable> outList = new ArrayList<>();
		for(Map<String, Object> map : xmlOpVars) {
			outList.add(parseOperationVariable(map));
		}
		
		Map<String, Object> inoutObj = (Map<String, Object>) xmlObject.get(INOUTPUT_VARIABLE);
		xmlOpVars = XMLHelper.getList(inoutObj.get(OPERATION_VARIABLE));
		List<OperationVariable> inoutList = new ArrayList<>();
		for(Map<String, Object> map : xmlOpVars) {
			inoutList.add(parseOperationVariable(map));
		}
	
		Operation operation = new Operation(inList, outList, inoutList, null);
		populateSubmodelElement(xmlObject, operation);
		return operation;
	}
	

	/**
	 * Parses a Map containing the content of XML tag &lt;aas:operationVariable&gt;
	 * 
	 * @param xmlObject the Map with the content of XML tag &lt;aas:operationVariable&gt;
	 * @return the parsed OperationVariable
	 */
	@SuppressWarnings("unchecked")
	private static OperationVariable parseOperationVariable(Map<String, Object> xmlObject) {
		SubmodelElement submodelElement = getSubmodelElement((Map<String, Object>) xmlObject.get(VALUE));
		OperationVariable operationVariable = new OperationVariable(submodelElement);
		populateSubmodelElement(xmlObject, operationVariable);
		return operationVariable;
	}
	
	
	/**
	 * Parses a Map containing the content of XML tag &lt;aas:basicEvent&gt;
	 * 
	 * @param xmlObject the Map with the content of XML tag &lt;aas:basicEvent&gt;
	 * @return the parsed BasicEvent
	 */
	@SuppressWarnings("unchecked")
	private static BasicEvent parseBasicEvent(Map<String, Object> xmlObject) {
		Map<String, Object> xmlObserved = (Map<String, Object>) xmlObject.get(OBSERVED);
		Reference observed = ReferenceXMLConverter.parseReference(xmlObserved);
		BasicEvent basicEvent = new BasicEvent(observed);		
		populateSubmodelElement(xmlObject, basicEvent);
		return basicEvent;
	}
	
	
	/**
	 * Parses a Map containing the content of XML tag &lt;aas:entity&gt;
	 * 
	 * @param xmlObject the Map with the content of XML tag &lt;aas:entity&gt;
	 * @return the parsed Entity
	 */
	@SuppressWarnings("unchecked")
	private static Entity parseEntity(Map<String, Object> xmlObject) {
		String entityTypeString = XMLHelper.getString(xmlObject.get(ENTITY_TYPE));
		EntityType entityType = EntityType.fromString(entityTypeString);
		Map<String, Object> xmlAssetRef = (Map<String, Object>) xmlObject.get(ASSET_REF);
		Reference assetRef = ReferenceXMLConverter.parseReference(xmlAssetRef);
		Map<String, Object> xmlStatement = (Map<String, Object>) xmlObject.get(STATEMENTS);
		List<ISubmodelElement> statements = getSubmodelElements(xmlStatement);
		Entity entity = new Entity(entityType, statements, assetRef);
		populateSubmodelElement(xmlObject, entity);
		return entity;
	}
	
	
	/**
	 * Parses a Map containing the content of XML tag &lt;aas:multiLanguageProperty&gt;
	 * 
	 * @param xmlObject the Map with the content of XML tag &lt;aas:multiLanguageProperty&gt;
	 * @return the parsed MultiLanguageProperty
	 */
	@SuppressWarnings("unchecked")
	private static MultiLanguageProperty parseMultiLanguageProperty(Map<String, Object> xmlObject) {
		Map<String, Object> xmlValueId = (Map<String, Object>) xmlObject.get(VALUE_ID);
		Reference valueId = ReferenceXMLConverter.parseReference(xmlValueId);
		Map<String, Object> xmlLangStrings = (Map<String, Object>) xmlObject.get(VALUE);
		LangStrings langStrings = LangStringsXMLConverter.parseLangStrings(xmlLangStrings);
		MultiLanguageProperty mLProperty = new MultiLanguageProperty(valueId, langStrings);
		populateSubmodelElement(xmlObject, mLProperty);
		return mLProperty;
	}
	
	
	/**
	 * Parses a Map containing the content of XML tag &lt;aas:range&gt;
	 * 
	 * @param xmlObject the Map with the content of XML tag &lt;aas:range&gt;
	 * @return the parsed Range
	 */
	private static Range parseRange(Map<String, Object> xmlObject) {
		String valueType = XMLHelper.getString(xmlObject.get(VALUE_TYPE));
		String min = XMLHelper.getString(xmlObject.get(MIN));
		String max = XMLHelper.getString(xmlObject.get(MAX));
		Range range = new Range(valueType, min, max);
		populateSubmodelElement(xmlObject, range);
		return range;
	}
	
	
	/**
	 * Populates a SubmodelElement with the standard information from a XML tag 
	 * 
	 * @param xmlObject the Map with the content of XML tag &lt;aas:blob&gt;
	 * @param submodelElement the SubmodelElement to be populated
	 */
	@SuppressWarnings("unchecked")
	private static void populateSubmodelElement(Map<String, Object> xmlObject, ISubmodelElement submodelElement) {
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
	 * Builds the individual SubmodelElement XML tags form a List of SubmodelElements and <br>
	 * populates the given root Element with them
	 * 
	 * @param document the XML document
	 * @param root the XML Element &lt;aas:submodelElements&gt;
	 * @param submodelElements a List of SubmodelElements
	 */
	private static void buildSubmodelElements(Document document, Element root, Collection<ISubmodelElement> submodelElements) {
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
	private static Element buildSubmodelElement(Document document, ISubmodelElement submodelElement) {
		String type = submodelElement.getModelType();		
		switch (type) {
			case Property.MODELTYPE:
				return buildProperty(document, (ISingleProperty) submodelElement);
			case BasicEvent.MODELTYPE:
				return buildBasicEvent(document, (IBasicEvent) submodelElement);
			case MultiLanguageProperty.MODELTYPE:
				return buildMultiLanguageProperty(document, (IMultiLanguageProperty) submodelElement);
			case Range.MODELTYPE:
				return buildRange(document, (IRange) submodelElement);
			case Entity.MODELTYPE:
				return buildEntity(document, (IEntity) submodelElement);
			case File.MODELTYPE:
				return buildFile(document, (IFile) submodelElement);
			case Blob.MODELTYPE:
				return buildBlob(document, (IBlob) submodelElement);
			case ReferenceElement.MODELTYPE:
				return buildReferenceElem(document, (IReferenceElement) submodelElement);
			case SubmodelElementCollection.MODELTYPE:
				return buildSubmodelElementCollection(document, (ISubmodelElementCollection) submodelElement);
			case RelationshipElement.MODELTYPE:
				return buildRelationshipElem(document, (IRelationshipElement) submodelElement);
			case Operation.MODELTYPE:
				return buildOperation(document, (IOperation) submodelElement);
			default:
				return null;
		}
	}	
	
	
	/**
	 * Builds the &lt;aas:operation&gt; XML tag for an Operation
	 * 
	 * @param document the XML document
	 * @param operation the IOperation to build the XML for
	 * @return the &lt;aas:operation&gt; XML tag for the given Operation
	 */
	private static Element buildOperation(Document document, IOperation operation) {
		Element operationRoot = document.createElement(OPERATION);
		
		populateSubmodelElement(document, operationRoot, operation);
		
		List<IOperationVariable> in = operation.getInputVariables();
		if(in != null) {
			Element valueRoot = document.createElement(INPUT_VARIABLE);
			operationRoot.appendChild(valueRoot);
			for(IOperationVariable operationVariable: in) {
				valueRoot.appendChild(buildOperationVariable(document, operationVariable));
			}
		}
		
		List<IOperationVariable> out = operation.getOutputVariables();
		if(out != null) {
			Element valueRoot = document.createElement(OUTPUT_VARIABLE);
			operationRoot.appendChild(valueRoot);
			for(IOperationVariable operationVariable: out) {
				valueRoot.appendChild(buildOperationVariable(document, operationVariable));
			}
		}
		
		List<IOperationVariable> inout = operation.getInOutputVariables();
		if(out != null) {
			Element valueRoot = document.createElement(INOUTPUT_VARIABLE);
			operationRoot.appendChild(valueRoot);
			for(IOperationVariable operationVariable: inout) {
				valueRoot.appendChild(buildOperationVariable(document, operationVariable));
			}
		}

		return operationRoot;
	}
	
	
	/**
	 * Builds the &lt;aas:operationVariable&gt; XML tag for an OperationVariable
	 * 
	 * @param document the XML document
	 * @param operationVariable the IOperationVariable to build the XML for
	 * @return the &lt;aas:operationVariable&gt; XML tag for the given OperationVariable
	 */
	private static Element buildOperationVariable(Document document, IOperationVariable operationVariable) {
		Element operationVariableRoot = document.createElement(OPERATION_VARIABLE);
		
		populateSubmodelElement(document, operationVariableRoot, operationVariable);
		
		ISubmodelElement value = operationVariable.getValue();
		
		if(value != null) {
			Element valueRoot = document.createElement(VALUE);
			valueRoot.appendChild(buildSubmodelElement(document, value));
			operationVariableRoot.appendChild(valueRoot);
		}

		return operationVariableRoot;
	}
	
	
	/**
	 * Builds the &lt;aas:relationshipElement&gt; XML tag for a RelationshipElement
	 * 
	 * @param document the XML document
	 * @param relElem the IRelationshipElement to build the XML for
	 * @return the &lt;aas:relationshipElement&gt; XML tag for the given RelationshipElement
	 */
	private static Element buildRelationshipElem(Document document, IRelationshipElement relElem) {
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
	
	
	/**
	 * Builds the &lt;aas:submodelElementCollection&gt; XML tag for a SubmodelElementCollection
	 * 
	 * @param document the XML document
	 * @param smElemCollection the ISubmodelElementCollection to build the XML for
	 * @return the &lt;aas:submodelElementCollection&gt; XML tag for the given SubmodelElementCollection
	 */
	private static Element buildSubmodelElementCollection(Document document, ISubmodelElementCollection smElemCollection) {
		Element smElemCollectionRoot = document.createElement(SUBMODEL_ELEMENT_COLLECTION);
		
		populateSubmodelElement(document, smElemCollectionRoot, smElemCollection);
		
		String isOrdered = Boolean.toString(smElemCollection.isOrdered());
		Element orderedElem = document.createElement(ORDERED);
		orderedElem.appendChild(document.createTextNode(isOrdered));
		smElemCollectionRoot.appendChild(orderedElem);
		
		String isAllowedDuplicates = Boolean.toString(smElemCollection.isAllowDuplicates());
		Element allowDuplicatesElem = document.createElement(ALLOW_DUPLICATES);
		allowDuplicatesElem.appendChild(document.createTextNode(isAllowedDuplicates));
		smElemCollectionRoot.appendChild(allowDuplicatesElem);
		
		Collection<ISubmodelElement> elems = smElemCollection.getValue();
		
		//recursively build the SubmodelElements contained in the ElementCollection
		if(elems != null) {
			Element valueRoot = document.createElement(VALUE);
			smElemCollectionRoot.appendChild(valueRoot);
			buildSubmodelElements(document, valueRoot, elems);
		}

		return smElemCollectionRoot;
	}
	
	
	/**
	 * Builds the &lt;aas:property&gt; XML tag for a Property
	 * 
	 * @param document the XML document
	 * @param prop the ISingleProperty to build the XML for
	 * @return the &lt;aas:property&gt; XML tag for the given Property
	 */
	private static Element buildProperty(Document document, ISingleProperty prop) {
		Element propertyRoot = document.createElement(PROPERTY);
		
		populateSubmodelElement(document, propertyRoot, prop);

		//FIXME the XML-ELement valueId has no corresponding field in Property
		String value = null;
		
		//for some reason, get() in ISingleProperty might throw an Exception
		try {
			Object valueObj = prop.get();
			value = valueObj == null ? null : valueObj.toString();
		} catch (Exception e) {
			logger.error("Exeption in buildProperty!", e);
		}
		
		String valueType = prop.getValueType();
		if (valueType != null) {
			Element valueTypeElem = document.createElement(VALUE_TYPE);
			valueTypeElem.appendChild(document.createTextNode(valueType));
			propertyRoot.appendChild(valueTypeElem);
		}
		
		if (value != null) {
			Element valueEle = document.createElement(VALUE);
			valueEle.appendChild(document.createTextNode(value));
			propertyRoot.appendChild(valueEle);
		}
		return propertyRoot;
	
	}
	
	
	/**
	 * Builds the &lt;aas:file&gt; XML tag for a File
	 * 
	 * @param document the XML document
	 * @param file the IFile to build the XML for
	 * @return the &lt;aas:file&gt; XML tag for the given File
	 */
	private static Element buildFile(Document document, IFile file) {
		Element fileRoot = document.createElement(FILE);
		
		populateSubmodelElement(document, fileRoot, file);
		
		String mimeType = file.getMimeType();
		String value = file.getValue();
		if(mimeType != null) {
			Element mimeTypeRoot = document.createElement(MIME_TYPE);
			mimeTypeRoot.appendChild(document.createTextNode(mimeType));
			fileRoot.appendChild(mimeTypeRoot);
		}
		if(value != null) {
			Element valueRoot = document.createElement(VALUE);
			valueRoot.appendChild(document.createTextNode(value));
			fileRoot.appendChild(valueRoot);
		}
		
		return fileRoot;
	}
	
	
	/**
	 * Builds the &lt;aas:blob&gt; XML tag for a Blob
	 * 
	 * @param document the XML document
	 * @param blob the IBlob to build the XML for
	 * @return the &lt;aas:blob&gt; XML tag for the given Blob
	 */
	private static Element buildBlob(Document document, IBlob blob) {
		Element blobRoot = document.createElement(BLOB);
		
		populateSubmodelElement(document, blobRoot, blob);
		
		String mimeType = blob.getMimeType();
		String value = Base64.getEncoder().encodeToString(blob.getValue());
		
		if(mimeType != null) {
			Element mimeTypeRoot = document.createElement(MIME_TYPE);
			mimeTypeRoot.appendChild(document.createTextNode(mimeType));
			blobRoot.appendChild(mimeTypeRoot);
		}
		if(value != null) {
			Element valueRoot = document.createElement(VALUE);
			valueRoot.appendChild(document.createTextNode(value));
			blobRoot.appendChild(valueRoot);
		}
		
		return blobRoot;
	}
	
	
	/**
	 * Builds the &lt;aas:referenceElement&gt; XML tag for a ReferenceElement
	 * 
	 * @param document the XML document
	 * @param refElem the IReferenceElement to build the XML for
	 * @return the &lt;aas:referenceElement&gt; XML tag for the given ReferenceElement
	 */
	private static Element buildReferenceElem(Document document, IReferenceElement refElem) {
		Element refElemRoot = document.createElement(REFERENCE_ELEMENT);
		
		populateSubmodelElement(document, refElemRoot, refElem);

		IReference value = refElem.getValue();
		if(value != null) {
			Element derivedFromRoot = document.createElement(VALUE);
			derivedFromRoot.appendChild(ReferenceXMLConverter.buildReferenceXML(document, value)); 
			refElemRoot.appendChild(derivedFromRoot);
		}		
		return refElemRoot;
	}
	
	
	/**
	 * Builds the &lt;aas:basicEvent&gt; XML tag for a BasicEvent
	 * 
	 * @param document the XML document
	 * @param basicEvent the IBasicEvent to build the XML for
	 * @return the &lt;aas:basicEvent&gt; XML tag for the given BasicEvent
	 */
	private static Element buildBasicEvent(Document document, IBasicEvent basicEvent) {
		Element basicEventRoot = document.createElement(BASIC_EVENT);
		populateSubmodelElement(document, basicEventRoot, basicEvent);
		
		IReference observed = basicEvent.getObserved();
		if(observed != null) {
			Element observedRoot = document.createElement(OBSERVED);
			observedRoot.appendChild(ReferenceXMLConverter.buildReferenceXML(document, observed));
			basicEventRoot.appendChild(observedRoot);
		}
		
		return basicEventRoot;
	}
	
	
	/**
	 * Builds the &lt;aas:entity&gt; XML tag for an Entity
	 * 
	 * @param document the XML document
	 * @param entity the IEntity to build the XML for
	 * @return the &lt;aas:entity&gt; XML tag for the given Entity
	 */
	private static Element buildEntity(Document document, IEntity entity) {
		Element entityRoot = document.createElement(ENTITY);
		
		populateSubmodelElement(document, entityRoot, entity);
		
		IReference assetRef = entity.getAsset();
		if(assetRef != null) {
			Element assetRefRoot = document.createElement(ASSET_REF);
			assetRefRoot.appendChild(ReferenceXMLConverter.buildReferenceXML(document, assetRef));
			entityRoot.appendChild(assetRefRoot);
		}
		
		Object entityTypeObj = entity.getEntityType();
		String entityType = entityTypeObj == null ? null : entityTypeObj.toString();
		if(entityType != null) {
			Element entityTypeRoot = document.createElement(ENTITY_TYPE);
			entityTypeRoot.appendChild(document.createTextNode(entityType));
			entityRoot.appendChild(entityTypeRoot);
		}
		
		Collection<ISubmodelElement> statement = entity.getStatements();
		
		//recursively build the SubmodelElements contained in the statement
		if(statement != null) {
			Element statementsRoot = document.createElement(STATEMENTS);
			entityRoot.appendChild(statementsRoot);
			buildSubmodelElements(document, statementsRoot, statement);
		}

		return entityRoot;
	}
	
	
	/**
	 * Builds the &lt;aas:multiLanguageProperty&gt; XML tag for a MultiLanguageProperty
	 * 
	 * @param document the XML document
	 * @param mLProperty the IMultiLanguageProperty to build the XML for
	 * @return the &lt;aas:multiLanguageProperty&gt; XML tag for the given MultiLanguageProperty
	 */
	private static Element buildMultiLanguageProperty(Document document, IMultiLanguageProperty mLProperty) {
		Element mLPropertyRoot = document.createElement(MULTI_LANGUAGE_PROPERTY);
		populateSubmodelElement(document, mLPropertyRoot, mLProperty);
		
		IReference valueId = mLProperty.getValueId();
		if(valueId != null) {
			Element valueIdRoot = document.createElement(VALUE_ID);
			valueIdRoot.appendChild(ReferenceXMLConverter.buildReferenceXML(document, valueId)); 
			mLPropertyRoot.appendChild(valueIdRoot);
		}	
		
		Element valueRoot = document.createElement(VALUE);
		LangStringsXMLConverter.buildLangStringsXML(document, valueRoot, mLProperty.getValue());
		mLPropertyRoot.appendChild(valueRoot);
		
		return mLPropertyRoot;
	}
	
	
	/**
	 * Builds the &lt;aas:range&gt; XML tag for a Range
	 * 
	 * @param document the XML document
	 * @param range the IRange to build the XML for
	 * @return the &lt;aas:range&gt; XML tag for the given Range
	 */
	private static Element buildRange(Document document, IRange range) {
		Element rangeRoot = document.createElement(RANGE);
		populateSubmodelElement(document, rangeRoot, range);
		
		String valueType = range.getValueType();
		if(valueType != null) {
			Element valueTypeRoot = document.createElement(VALUE_TYPE);
			valueTypeRoot.appendChild(document.createTextNode(valueType));
			rangeRoot.appendChild(valueTypeRoot);
		}
		
		Object minObj = range.getMin();
		String min = minObj == null ? null : minObj.toString();
		if(min != null) {
			Element minRoot = document.createElement(MIN);
			minRoot.appendChild(document.createTextNode(min));
			rangeRoot.appendChild(minRoot);
		}
		
		Object maxObj = range.getMax();
		String max = maxObj == null ? null : maxObj.toString();
		if(max != null) {
			Element maxRoot = document.createElement(MAX);
			maxRoot.appendChild(document.createTextNode(max));
			rangeRoot.appendChild(maxRoot);
		}
		
		return rangeRoot;
	}
	
	
	/**
	 * Populates a SubmodelElement XML tag with the standard information of a SubmodelElement 
	 * 
	 * @param document the XML document
	 * @param root the root Element of the SubmodelElement
	 * @param submodelElement the SubmodelElement
	 */
	private static void populateSubmodelElement(Document document, Element root, ISubmodelElement submodelElement) {
		ReferableXMLConverter.populateReferableXML(document, root, submodelElement);
		HasDataSpecificationXMLConverter.populateHasDataSpecificationXML(document, root, submodelElement);
		QualifiableXMLConverter.populateQualifiableXML(document, root, submodelElement);
		HasSemanticsXMLConverter.populateHasSemanticsXML(document, root, submodelElement);
		HasKindXMLConverter.populateHasKindXML(document, root, submodelElement);
	}
}