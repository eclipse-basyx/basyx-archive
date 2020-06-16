package org.eclipse.basyx.submodel.factory.xml.converters.submodelelement.dataelement;

import java.util.Map;

import org.eclipse.basyx.submodel.factory.xml.XMLHelper;
import org.eclipse.basyx.submodel.factory.xml.converters.submodelelement.SubmodelElementXMLConverter;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IProperty;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetypedef.PropertyValueTypeDefHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Parses &lt;aas:property&gt; and builds the Property object from it <br>
 * Builds &lt;aas:property&gt; form a given Property object
 * 
 * @author conradi
 *
 */
public class PropertyXMLConverter extends SubmodelElementXMLConverter {
	
	private static Logger logger = LoggerFactory.getLogger(PropertyXMLConverter.class);
	
	public static final String PROPERTY = "aas:property";
	

	/**
	 * Parses a Map containing the content of XML tag &lt;aas:property&gt;
	 * 
	 * @param xmlObject the Map with the content of XML tag &lt;aas:property&gt;
	 * @return the parsed Property
	 */
	public static Property parsePropery(Map<String, Object> xmlObject) {
		
		Property property = new Property();
		
		populateSubmodelElement(xmlObject, property);
		
		String valueType = XMLHelper.getString(xmlObject.get(SubmodelElementXMLConverter.VALUE_TYPE));
		String value = XMLHelper.getString(xmlObject.get(SubmodelElementXMLConverter.VALUE));
		
		property.set(value, PropertyValueTypeDefHelper.fromName(valueType));
		
		//FIXME the XML-ELement valueId has no corresponding field in Property
		return property;
	}
	
	
	
	
	/**
	 * Builds the &lt;aas:property&gt; XML tag for a Property
	 * 
	 * @param document the XML document
	 * @param prop the ISingleProperty to build the XML for
	 * @return the &lt;aas:property&gt; XML tag for the given Property
	 */
	public static Element buildProperty(Document document, IProperty prop) {
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
		
		if (value != null) {
			Element valueEle = document.createElement(SubmodelElementXMLConverter.VALUE);
			valueEle.appendChild(document.createTextNode(value));
			propertyRoot.appendChild(valueEle);
		}
		
		String valueType = prop.getValueType();
		if (valueType != null) {
			Element valueTypeElem = document.createElement(SubmodelElementXMLConverter.VALUE_TYPE);
			valueTypeElem.appendChild(document.createTextNode(valueType));
			propertyRoot.appendChild(valueTypeElem);
		}
		
		return propertyRoot;
	}
}