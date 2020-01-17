package org.eclipse.basyx.submodel.factory.xml.converters.qualifier.qualifiable;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	public static final String FORMULA = "aas:formula";
	public static final String DEPENDS_ON = "aas:dependsOn";
	public static final String REFERENCE = "aas:reference";
	public static final String QUALIFIER_TYPE = "aas:qualifierType";
	public static final String QUALIFIER_VALUE = "aas:qualifierValue";
	public static final String QUALIFIER_VALUE_ID = "aas:qualifierValueId";
	

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
		qualifiable.put(Qualifiable.CONSTRAINTS, parseConstraints(qualifierObj));
	}
	
	
	/**
	 * Parses the IConstraint objects form XML
	 * 
	 * @param xmlConstraints the XML map containing the &lt;aas:formula&gt; and &lt;aas:qualifier&gt; tags
	 * @return the Set of IConstraint objects parsed
	 */
	private static Set<IConstraint> parseConstraints(Map<String, Object> xmlConstraints) {
		Set<IConstraint> constraints = new HashSet<>();
		
		if(xmlConstraints == null) return constraints;
		
		List<Map<String, Object>> xmlFormulaList = XMLHelper.getList(xmlConstraints.get(FORMULA));
		for (Map<String, Object> xmlFormula : xmlFormulaList) {
			constraints.add(parseFormula(xmlFormula));
		}
		
		List<Map<String, Object>> xmlQualifierList = XMLHelper.getList(xmlConstraints.get(QUALIFIER));
		for (Map<String, Object> xmlQualifier : xmlQualifierList) {
			constraints.add(parseQualifier(xmlQualifier));
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
		Map<String, Object> dependsOnObj = (Map<String, Object>) xmlFormula.get(DEPENDS_ON);
		Set<IReference> referenceList = new HashSet<IReference>();
				
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
		String type = (String) xmlQualifier.get(QUALIFIER_TYPE);
		String value = (String) xmlQualifier.get(QUALIFIER_VALUE);
		Map<String, Object> qualifierValueIdObj = (Map<String, Object>) xmlQualifier.get(QUALIFIER_VALUE_ID);
		
		Reference ref = ReferenceXMLConverter.parseReference(qualifierValueIdObj);
		
		Qualifier qualifier = new Qualifier();
		HasSemanticsXMLConverter.populateHasSemantics(xmlQualifier, qualifier);
		
		qualifier.setQualifierType(type);
		qualifier.setQualifierValue(value);
		qualifier.setQualifierValueId(ref);
		
		return qualifier;
	}

	
	

	/**
	 * Populates a given XML map with the data form a given IQualifiable object<br>
	 * Creates the &lt;aas:qualifier&gt; tag in the given root
	 * 
	 * @param document the XML document
	 * @param root the XML root Element to be populated
	 * @param qualifiable the IQualifiable object to be converted to XML
	 */
	public static void populateQualifiableXML(Document document, Element root, IQualifiable qualifiable) {
		if(qualifiable.getQualifier() == null || qualifiable.getQualifier().size() == 0) return;
		
		Element qualifierRoot = document.createElement(QUALIFIER);
		
		Set<IConstraint> constraints = qualifiable.getQualifier();
		
		//the constraints can be IFormula or IQualifier
		for (IConstraint constraint : constraints) {
			if(constraint instanceof IFormula) {
				qualifierRoot.appendChild(buildFormulaXML(document, (IFormula) constraint));
			}
			else if(constraint instanceof IQualifier) {
				qualifierRoot.appendChild(buildQualifierXML(document, (IQualifier) constraint));
			}			
		}
		
		root.appendChild(qualifierRoot);
	}
	
	
	/**
	 * Builds XML form a given IFormula object
	 * 
	 * @param document the XML document
	 * @param formula the IFormula to be converted to XML
	 * @return the &lt;aas:formula&gt; XML tag build from the IFormula
	 */
	private static Element buildFormulaXML(Document document, IFormula formula) {
		Element formulaRoot = document.createElement(FORMULA);
		Element dependsOnRoot = document.createElement(DEPENDS_ON);
		Set<IReference> ref = formula.getDependsOn();
		Element refrenceRoot = document.createElement(REFERENCE);
		dependsOnRoot.appendChild(refrenceRoot);
		formulaRoot.appendChild(dependsOnRoot);
		refrenceRoot.appendChild(ReferenceXMLConverter.buildReferencesXML(document, ref)); 
		return formulaRoot;
	}
	
	
	/**
	 * Builds XML form a given IQualifier object
	 * 
	 * @param document the XML document
	 * @param qualifier the IQualifier to be converted to XML
	 * @return the &lt;aas:qualifier&gt; XML tag build from the IQualifier
	 */
	private static Element buildQualifierXML(Document document, IQualifier qualifier) {
		IReference qualId = qualifier.getQualifierValueId();
		String type = qualifier.getQualifierType();
		String value = qualifier.getQualifierValue();
		Element qualifierRoot = document.createElement(QUALIFIER);
		Element qualifierType = document.createElement(QUALIFIER_TYPE);
		qualifierType.appendChild(document.createTextNode(type));
		
		HasSemanticsXMLConverter.populateHasSemanticsXML(document, qualifierRoot, qualifier);
		
		qualifierRoot.appendChild(qualifierType);
		
		Element qualifierValue = document.createElement(QUALIFIER_VALUE);
		qualifierValue.appendChild(document.createTextNode(value));
		qualifierRoot.appendChild(qualifierValue);
		
		Element qualifierValueId = document.createElement(QUALIFIER_VALUE_ID);
		qualifierRoot.appendChild(qualifierValueId);
		HashSet<IReference> set = new HashSet<IReference>();
		set.add(qualId);
		 
		Element keysElement = ReferenceXMLConverter.buildReferencesXML(document, set);
		if(keysElement != null) {
			qualifierValueId.appendChild(keysElement);
		}
		
		return qualifierRoot;
	}
}
