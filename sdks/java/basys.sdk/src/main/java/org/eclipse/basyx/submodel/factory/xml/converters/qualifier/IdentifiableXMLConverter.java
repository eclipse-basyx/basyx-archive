package org.eclipse.basyx.submodel.factory.xml.converters.qualifier;

import java.util.Map;

import org.eclipse.basyx.submodel.factory.xml.XMLHelper;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.IIdentifiable;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.AdministrativeInformation;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Identifiable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Handles the conversion between an IIdentifiable object and the XML tags<br>
 * &lt;aas:administration&gt; and &lt;aas:identification&gt; in both directions
 * 
 * @author conradi
 *
 */
public class IdentifiableXMLConverter {

	public static final String ADMINISTRATION = "aas:administration";
	public static final String VERSION = "aas:version";
	public static final String REVISION = "aas:revision";
	public static final String IDENTIFICATION = "aas:identification";
	public static final String IDTYPE = "idType";
	
	
	/**
	 * Populates a given IIdentifiable object with the data form the given XML
	 * 
	 * @param xmlObject the XML map containing the &lt;aas:administration&gt; and &lt;aas:identification&gt; tags
	 * @param identifiable the IIdentifiable object to be populated -treated as Map here-
	 */
	@SuppressWarnings("unchecked")
	public static void populateIdentifiable(Map<String, Object> xmlObject, Map<String, Object> identifiable) {
		//The IIdentifiable object has to be treated as Map here, as the Interface has no Setters
		
		String id="";
		String idType="";
		String version="";
		String revision="";
		
		ReferableXMLConverter.populateReferable(xmlObject, identifiable);
		
		Map<String, Object> identierFromXML = (Map<String, Object>) xmlObject.get(IDENTIFICATION);
		if(identierFromXML != null) {
			id = XMLHelper.getString(identierFromXML.get(XMLHelper.TEXT));
			idType = XMLHelper.getString(identierFromXML.get(IDTYPE));
		}

		if(xmlObject.containsKey(ADMINISTRATION)) {
			Map<String, Object> administrationFromXML = (Map<String, Object>) xmlObject.get(ADMINISTRATION);
			version = XMLHelper.getString(administrationFromXML.get(VERSION));
			revision = XMLHelper.getString(administrationFromXML.get(REVISION));	
		}
		
		identifiable.put(Identifiable.ADMINISTRATION, new AdministrativeInformation(version, revision));
		identifiable.put(Identifiable.IDENTIFICATION, new Identifier(idType, id));
	}
	

	
	
	/**
	 * Populates a given XML map with the data form a given IIdentifiable object<br>
	 * Creates the &lt;aas:administration&gt; and &lt;aas:identification&gt; tags in the given root
	 * 
	 * @param document the XML document
	 * @param root the XML root Element to be populated
	 * @param identifiable the IIdentifiable object to be converted to XML
	 */
	public static void populateIdentifiableXML(Document document, Element root, IIdentifiable identifiable) {
		ReferableXMLConverter.populateReferableXML(document, root, identifiable);
		
		//Build the administration if present
		if(identifiable.getAdministration() != null) {
			Element administrationRoot = document.createElement(ADMINISTRATION);
			if(identifiable.getAdministration().getVersion() != null) {
				Element version = document.createElement(VERSION);
				version.appendChild(document.createTextNode(identifiable.getAdministration().getVersion()));
				administrationRoot.appendChild(version);
			}
			if(identifiable.getAdministration().getRevision() != null) {
				Element revision = document.createElement(REVISION);
				revision.appendChild(document.createTextNode(identifiable.getAdministration().getRevision()));
				administrationRoot.appendChild(revision);
			}
			root.appendChild(administrationRoot);
		}	
		
		//Build the identification if present
		if (identifiable.getIdentification() != null) {
			String id = identifiable.getIdentification().getId();
			Element identificationRoot = document.createElement(IDENTIFICATION);
			identificationRoot.appendChild(document.createTextNode(id));
			if(identifiable.getIdentification().getIdType() != null) {
				String idType = identifiable.getIdentification().getIdType();				
				identificationRoot.setAttribute(IDTYPE, idType);
			}
			root.appendChild(identificationRoot);
		}
	}
}