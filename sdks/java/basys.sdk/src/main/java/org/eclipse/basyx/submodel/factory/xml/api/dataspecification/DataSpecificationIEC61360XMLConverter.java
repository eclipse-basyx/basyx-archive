package org.eclipse.basyx.submodel.factory.xml.api.dataspecification;

import java.util.Map;

import org.eclipse.basyx.submodel.factory.xml.XMLHelper;
import org.eclipse.basyx.submodel.factory.xml.converters.qualifier.LangStringsXMLConverter;
import org.eclipse.basyx.submodel.factory.xml.converters.reference.ReferenceXMLConverter;
import org.eclipse.basyx.submodel.metamodel.map.dataspecification.DataSpecificationIEC61360;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Handles the conversion between a DataSpecificationIEC61360 object and the XML tag &lt;aas:dataSpecificationIEC61360&gt; in both directions
 * 
 * @author conradi
 *
 */
public class DataSpecificationIEC61360XMLConverter {
	
	public static final String DATA_SPECIFICATION_IEC61360 = "aas:dataSpecificationIEC6136061360";
	public static final String IEC61360_PREFERRED_NAME = "IEC61360:preferredName";
	public static final String IEC61360_UNIT_ID = "IEC61360:unitId";
	public static final String IEC61360_SOURCE_OF_DEFINITION = "IEC61360:sourceOfDefinition";
	public static final String IEC61360_SHORT_NAME = "IEC61360:shortName";
	public static final String IEC61360_VALUE_FORMAT = "IEC61360:valueFormat";
	public static final String IEC61360_UNIT = "IEC61360:unit";
	public static final String IEC61360_SYMBOL = "IEC61360:symbol";
	public static final String IEC61360_DATA_TYPE = "IEC61360:dataType";
	public static final String IEC61360_DEFINITION = "IEC61360:definition";
	
	
	/**
	 * Parses the DataSpecificationIEC61360 object from XML
	 * 
	 * @param xmlDataSpecificationContentObject the XML map containing the &lt;aas:dataSpecificationIEC61360&gt; tag
	 * @return the parsed DataSpecificationIEC61360 object
	 */
	@SuppressWarnings("unchecked")
	public static DataSpecificationIEC61360 parseDataSpecification(Map<String, Object> xmlDataSpecificationContentObject) {

		DataSpecificationIEC61360 spec = new DataSpecificationIEC61360();
		
		if(xmlDataSpecificationContentObject == null) return spec;
		
		Map<String, Object> xmlDataSpecObj = (Map<String, Object>) xmlDataSpecificationContentObject.get(DATA_SPECIFICATION_IEC61360);
		
		LangStrings preferredName = LangStringsXMLConverter.parseLangStrings((Map<String, Object>) xmlDataSpecObj.get(IEC61360_PREFERRED_NAME));
		spec.setPreferredName(preferredName);
		
		Map<String, Object> xmlKeys = (Map<String, Object>) xmlDataSpecObj.get(IEC61360_UNIT_ID);
		spec.setUnitId(ReferenceXMLConverter.parseReference(xmlKeys));
		
		LangStrings sourceOfDefinition = LangStringsXMLConverter.parseLangStrings((Map<String, Object>) xmlDataSpecObj.get(IEC61360_SOURCE_OF_DEFINITION));
		spec.setSourceOfDefinition(sourceOfDefinition);
		
		LangStrings definition = LangStringsXMLConverter.parseLangStrings((Map<String, Object>) xmlDataSpecObj.get(IEC61360_DEFINITION));
		spec.setDefinition(definition);
		
		spec.setShortName(XMLHelper.getString(xmlDataSpecObj.get(IEC61360_SHORT_NAME)));
		spec.setValueFormat(XMLHelper.getString(xmlDataSpecObj.get(IEC61360_VALUE_FORMAT)));
		spec.setUnit(XMLHelper.getString(xmlDataSpecObj.get(IEC61360_UNIT)));
		spec.setSymbol(XMLHelper.getString(xmlDataSpecObj.get(IEC61360_SYMBOL)));
		spec.setDataType(XMLHelper.getString(xmlDataSpecObj.get(IEC61360_DATA_TYPE)));
		
		//FIXME Code and ValueList are Objects
		//FIXME valueType doesn't exist in Object
		
		return spec;
	}
	
	
	
	
	/**
	 * Builds XML form a given DataSpecificationIEC61360 object
	 * 
	 * @param document the XML document
	 * @param dataSpec the DataSpecificationIEC61360 object to be converted to XML
	 * @return the &lt;aas:dataSpecificationIEC61360&gt; XML tag build from the DataSpecificationIEC61360 object
	 */
	public static Element buildDataSpecificationContent(Document document, DataSpecificationIEC61360 dataSpec) {

		Element root = document.createElement(DATA_SPECIFICATION_IEC61360);
		
		Element xmlElement = document.createElement(IEC61360_PREFERRED_NAME);
		LangStringsXMLConverter.buildLangStringsXML(document, xmlElement, dataSpec.getPreferredName());
		root.appendChild(xmlElement);
		
		xmlElement = document.createElement(IEC61360_SHORT_NAME);
		xmlElement.appendChild(document.createTextNode(dataSpec.getShortName()));
		root.appendChild(xmlElement);
		
		xmlElement = document.createElement(IEC61360_UNIT);
		xmlElement.appendChild(document.createTextNode(dataSpec.getUnit()));
		root.appendChild(xmlElement);
		
		xmlElement = document.createElement(IEC61360_UNIT_ID);
		xmlElement.appendChild(ReferenceXMLConverter.buildReferenceXML(document, dataSpec.getUnitId()));
		root.appendChild(xmlElement);
		
		xmlElement = document.createElement(IEC61360_VALUE_FORMAT);
		xmlElement.appendChild(document.createTextNode(dataSpec.getValueFormat()));
		root.appendChild(xmlElement);
		
		xmlElement = document.createElement(IEC61360_SYMBOL);
		xmlElement.appendChild(document.createTextNode(dataSpec.getSymbol()));
		root.appendChild(xmlElement);
		
		xmlElement = document.createElement(IEC61360_DATA_TYPE);
		xmlElement.appendChild(document.createTextNode(dataSpec.getDataType()));
		root.appendChild(xmlElement);
		
		xmlElement = document.createElement(IEC61360_DEFINITION);
		LangStringsXMLConverter.buildLangStringsXML(document, xmlElement, dataSpec.getDefinition());
		root.appendChild(xmlElement);
		
		//FIXME Code and ValueList are Objects and can't be written to XML File
		//FIXME valueType doesn't exist in Object but in XML.
		
		root.appendChild(root);
		return root;
	}
	
}
