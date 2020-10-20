package org.eclipse.basyx.submodel.factory.xml.converters.qualifier.qualifiable;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.eclipse.basyx.submodel.factory.xml.XMLHelper;
import org.eclipse.basyx.submodel.factory.xml.converters.qualifier.HasSemanticsXMLConverter;
import org.eclipse.basyx.submodel.factory.xml.converters.reference.ReferenceXMLConverter;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.qualifiable.IConstraint;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.qualifiable.IFormula;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.qualifiable.IQualifiable;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.qualifiable.IQualifier;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.qualifiable.Formula;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.qualifiable.Qualifiable;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.qualifiable.Qualifier;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetypedef.PropertyValueTypeDefHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Handles the conversion between an IQualifiable object and the XML tag &lt;aas:qualifier&gt; in both directions
 * 
 * @author conradi
 *
 */
public class QualifiableXMLConverter {
	
	public static final String QUALIFIER = "aas:qualifier";
	public static final String QUALIFIERS = "aas:qualifiers";
	public static final String FORMULA = "aas:formula";
	public static final String DEPENDS_ON_REFS = "aas:dependsOnRefs";
	public static final String REFERENCE = "aas:reference";
	public static final String TYPE = "aas:type";
	public static final String VALUE = "aas:value";
	public static final String VALUE_TYPE = "aas:valueType";
	public static final String VALUE_ID = "aas:valueId";
	

	/**
	 * Populates a given IQualifiable object with the data form the given XML
	 * 
	 * @param xmlObject the XML map containing the &lt;aas:qualifier&gt; tag
	 * @param qualifiable the IQualifiable object to be populated -treated as Map here-
	 */
	@SuppressWarnings("unchecked")
	public static void populateQualifiable(Map<String, Object> xmlObject, Map<String, Object> qualifiable) {
		//The IQualifiable object has to be treated as Map here, as the Interface has no Setters
		
		Map<String, Object> qualifierObj = (Map<String, Object>) xmlObject.get(QUALIFIER);
		qualifiable.put(Qualifiable.QUALIFIERS, parseConstraints(qualifierObj));
	}
	
	
	/**
	 * Parses the IConstraint objects form XML
	 * 
	 * @param xmlConstraints the XML map containing the &lt;aas:formula&gt; and &lt;aas:qualifier&gt; tags
	 * @return the Set of IConstraint objects parsed
	 */
	@SuppressWarnings("unchecked")
	private static Collection<IConstraint> parseConstraints(Map<String, Object> xmlConstraints) {
		Collection<IConstraint> constraints = new HashSet<>();
		
		if(xmlConstraints == null) return constraints;
		
		List<Map<String, Object>> xmlQualifiersList = XMLHelper.getList(xmlConstraints.get(QUALIFIERS));
		
		for (Map<String, Object> xmlQualifiers : xmlQualifiersList) {
			
			Map<String, Object> xmlFormula = (Map<String, Object>) xmlQualifiers.get(FORMULA);
			if(xmlFormula != null) {
				constraints.add(parseFormula(xmlFormula));
			}
			
			Map<String, Object> xmlQualifier = (Map<String, Object>) xmlQualifiers.get(QUALIFIER);
			if(xmlQualifier != null) {
				constraints.add(parseQualifier(xmlQualifier));
			}
		}
		
		return constraints;
	}
	
	
	/**
	 * Parses a Formula object form XML
	 * 
	 * @param xmlFormula the XML map containing the content of the &lt;aas:formula&gt; tag
	 * @return the parsed Formula object
	 */
	@SuppressWarnings("unchecked")
	private static Formula parseFormula(Map<String, Object> xmlFormula) {
		Map<String, Object> dependsOnObj = (Map<String, Object>) xmlFormula.get(DEPENDS_ON_REFS);
		Collection<IReference> referenceList = new HashSet<>();
				
		List<Map<String, Object>> xmlReferenceList = XMLHelper.getList(dependsOnObj.get(REFERENCE));
		for (Map<String, Object> xmlReference : xmlReferenceList) {
			referenceList.add(ReferenceXMLConverter.parseReference(xmlReference));
		}
		
		return new Formula(referenceList);
	}
	
	
	/**
	 * Parses a Qualifier object form XML
	 * 
	 * @param xmlQualifier the XML map containing the content of the &lt;aas:qualifier&gt; tag
	 * @return the parsed Qualifier object
	 */
	@SuppressWarnings("unchecked")
	private static Qualifier parseQualifier(Map<String, Object> xmlQualifier) {
		String type = XMLHelper.getString(xmlQualifier.get(TYPE));
		String value = XMLHelper.getString(xmlQualifier.get(VALUE));
		String valueType = XMLHelper.getString(xmlQualifier.get(VALUE_TYPE));
		Map<String, Object> qualifierValueIdObj = (Map<String, Object>) xmlQualifier.get(VALUE_ID);
		
		Reference ref = ReferenceXMLConverter.parseReference(qualifierValueIdObj);
		
		Qualifier qualifier = new Qualifier();
		HasSemanticsXMLConverter.populateHasSemantics(xmlQualifier, qualifier);
		
		qualifier.setType(type);
		qualifier.setValue(value);
		qualifier.setValueId(ref);
		qualifier.setValueType(PropertyValueTypeDefHelper.fromName(valueType));
		
		return qualifier;
	}

	
	

	/**
	 * Populates a given XML map with the data from a given IQualifiable object<br>
	 * Creates the &lt;aas:qualifier&gt; tag in the given root
	 * 
	 * @param document the XML document
	 * @param root the XML root Element to be populated
	 * @param qualifiable the IQualifiable object to be converted to XML
	 */
	public static void populateQualifiableXML(Document document, Element root, IQualifiable qualifiable) {
		if(qualifiable.getQualifiers() == null || qualifiable.getQualifiers().size() == 0) return;
		
		
		Collection<IConstraint> constraints = qualifiable.getQualifiers();
		
		Element qualifierRoot = document.createElement(QUALIFIER);
		
		for (IConstraint constraint : constraints) {
			qualifierRoot.appendChild(buildQualifiersXML(document, constraint));
		}
		
		root.appendChild(qualifierRoot);
	}
	
	
	/**
	 * Builds XML from a given IConstraint objcet
	 * 
	 * @param document the XML document
	 * @param constraint the IConstraint to be converted to XML
	 * @return the &lt;aas:qualifiers&gt; XML tag build from the IConstraint
	 */
	private static Element buildQualifiersXML(Document document, IConstraint constraint) {
		Element qualifiersRoot = document.createElement(QUALIFIERS);
		
		//the constraints can be IFormula or IQualifier
		if(constraint instanceof IFormula) {
			qualifiersRoot.appendChild(buildFormulaXML(document, (IFormula) constraint));
		} else if(constraint instanceof IQualifier) {
			qualifiersRoot.appendChild(buildQualifierXML(document, (IQualifier) constraint));
		}
		
		return qualifiersRoot;
	}
	
	
	/**
	 * Builds XML from a given IFormula object
	 * 
	 * @param document the XML document
	 * @param formula the IFormula to be converted to XML
	 * @return the &lt;aas:formula&gt; XML tag build from the IFormula
	 */
	private static Element buildFormulaXML(Document document, IFormula formula) {
		Element formulaRoot = document.createElement(FORMULA);
		Element dependsOnRoot = document.createElement(DEPENDS_ON_REFS);
		Collection<IReference> ref = formula.getDependsOn();
		Element refrenceRoot = document.createElement(REFERENCE);
		refrenceRoot.appendChild(ReferenceXMLConverter.buildReferencesXML(document, ref));
		dependsOnRoot.appendChild(refrenceRoot);
		formulaRoot.appendChild(dependsOnRoot);
		return formulaRoot;
	}
	
	
	/**
	 * Builds XML from a given IQualifier object
	 * 
	 * @param document the XML document
	 * @param qualifier the IQualifier to be converted to XML
	 * @return the &lt;aas:qualifier&gt; XML tag build from the IQualifier
	 */
	private static Element buildQualifierXML(Document document, IQualifier qualifier) {
		IReference qualId = qualifier.getValueId();
		String type = XMLHelper.getString(qualifier.getType());
		String value = XMLHelper.getString(qualifier.getValue());
		String valueType = qualifier.getValueType().toString();
		Element qualifierRoot = document.createElement(QUALIFIER);
		
		Element qualifierValueId = document.createElement(VALUE_ID);
		Element keysElement = ReferenceXMLConverter.buildReferenceXML(document, qualId);
		if(keysElement != null) {
			qualifierValueId.appendChild(keysElement);
		}
		qualifierRoot.appendChild(qualifierValueId);

		Element qualifierValue = document.createElement(VALUE);
		qualifierValue.appendChild(document.createTextNode(value));
		qualifierRoot.appendChild(qualifierValue);
		
		Element qualifierType = document.createElement(TYPE);
		qualifierType.appendChild(document.createTextNode(type));
		qualifierRoot.appendChild(qualifierType);
		
		Element qualifierValueType = document.createElement(VALUE_TYPE);
		qualifierValueType.appendChild(document.createTextNode(valueType));
		qualifierRoot.appendChild(qualifierValueType);
		
		HasSemanticsXMLConverter.populateHasSemanticsXML(document, qualifierRoot, qualifier);
		
		return qualifierRoot;
	}
}
