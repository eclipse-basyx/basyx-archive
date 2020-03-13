package org.eclipse.basyx.aas.factory.xml.converters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.aas.factory.xml.api.parts.ViewXMLConverter;
import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.api.parts.IConceptDictionary;
import org.eclipse.basyx.aas.metamodel.api.parts.IView;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.aas.metamodel.map.parts.ConceptDictionary;
import org.eclipse.basyx.submodel.factory.xml.XMLHelper;
import org.eclipse.basyx.submodel.factory.xml.converters.qualifier.HasDataSpecificationXMLConverter;
import org.eclipse.basyx.submodel.factory.xml.converters.qualifier.IdentifiableXMLConverter;
import org.eclipse.basyx.submodel.factory.xml.converters.qualifier.ReferableXMLConverter;
import org.eclipse.basyx.submodel.factory.xml.converters.reference.ReferenceXMLConverter;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Handles the conversion between IAssetAdministrationShell objects and the XML tag &lt;aas:assetAdministrationShells&gt; in both directions
 * 
 * @author conradi
 *
 */
public class AssetAdministrationShellXMLConverter {
	
	public static final String ASSET_ADMINISTRATION_SHELLS = "aas:assetAdministrationShells";
	public static final String ASSET_ADMINISTRATION_SHELL = "aas:assetAdministrationShell";
	public static final String DERIVED_FROM = "aas:derivedFrom";
	public static final String ASSET_REF = "aas:assetRef";
	public static final String SUBMODEL_REFS = "aas:submodelRefs";
	public static final String SUBMODEL_REF = "aas:submodelRef";
	public static final String CONCEPT_DICTIONARIES = "aas:conceptDictionaries";
	public static final String CONCEPT_DICTIONARY = "aas:conceptDictionary";
	public static final String CONCEPT_DESCRIPTION_REFS = "aas:conceptDescriptionRefs";
	public static final String CONCEPT_DESCRIPTION_REF = "aas:conceptDescriptionRef";
	
	
	/**
	 * Parses &lt;aas:assetAdministrationShells&gt; and builds the AssetAdministrationShell objects from it
	 * 
	 * @param xmlAASObject a Map containing the content of the XML tag &lt;aas:assetAdministrationShells&gt;
	 * @return a List of IAssetAdministrationShell objects parsed form the given XML Map
	 */
	@SuppressWarnings("unchecked")
	public static List<IAssetAdministrationShell> parseAssetAdministrationShells(Map<String, Object> xmlAASObject) {
		
		List<Map<String, Object>> xmlAASs = XMLHelper.getList(xmlAASObject.get(ASSET_ADMINISTRATION_SHELL));
		List<IAssetAdministrationShell> AASs = new ArrayList<>();
		
		for(Map<String, Object> xmlAAS: xmlAASs) {
			AssetAdministrationShell adminShell = new AssetAdministrationShell();
			
			IdentifiableXMLConverter.populateIdentifiable(xmlAAS, adminShell);
			HasDataSpecificationXMLConverter.populateHasDataSpecification(xmlAAS, adminShell);
			
			Set<IView> views = ViewXMLConverter.parseViews(xmlAAS);
			Set<IConceptDictionary> conceptDictionary = parseConceptDictionaries(xmlAAS);
			
			Map<String, Object> xmlAssetRef = (Map<String, Object>) xmlAAS.get(ASSET_REF);
			Asset assetRef =  new Asset(ReferenceXMLConverter.parseReference(xmlAssetRef));
			
			//FIXME DataSpecificationIEC61360 has no equivalent in AAS Object
			/*Map<String, Object> xmlEmbeddedDataSpec = (Map<String, Object>) xmlAAS.get(EMBEDDED_DATA_SPECIFICATION);
			if (xmlEmbeddedDataSpec != null) {
				DataSpecificationIEC61360 specification = TransformDataSpecification.
						parseDataSpecification((Map<String, Object>) xmlEmbeddedDataSpec.get(DATA_SPECIFICATION_CONTENT));
			}*/
			
			Map<String, Object> xmlDerivedFrom = (Map<String, Object>) xmlAAS.get(DERIVED_FROM);
			IReference derivedFrom =  ReferenceXMLConverter.parseReference(xmlDerivedFrom);
			adminShell.setDerivedFrom(derivedFrom);
			
			adminShell.setViews(views);
			adminShell.setConceptDictionary(conceptDictionary);
			adminShell.setAsset(assetRef);
			
			Set<IReference> submodelRefs = parseSubmodelRefs(xmlAAS);
			adminShell.setSubmodelReferences(submodelRefs);
			
			AASs.add(adminShell);
		}
		return AASs;
	}
	
	
	/**
	 * Parses &lt;aas:submodelRefs&gt; and builds {@link Reference} objects from it
	 * 
	 * @param xmlObject
	 *            a Map containing the XML tag &lt;aas:submodelRefs&gt;
	 * @return a Set of {@link IReference} objects parsed form the given XML Map
	 */
	@SuppressWarnings("unchecked")
	private static Set<IReference> parseSubmodelRefs(Map<String, Object> xmlObject) {
		Set<IReference> refSet = new HashSet<>();
		
		Map<String, Object> refMap = (Map<String, Object>) xmlObject.get(SUBMODEL_REFS);

		if (refMap == null) {
			return new HashSet<IReference>();
		}

		List<Map<String, Object>> xmlKeyList = XMLHelper.getList(refMap.get(SUBMODEL_REF));
		for (Map<String, Object> xmlKey : xmlKeyList) {
			refSet.add(ReferenceXMLConverter.parseReference(xmlKey));
		}
	
		return refSet;
	}
	
	
	/**
	 * Parses &lt;aas:conceptDictionaries&gt; and builds IConceptDictionary objects from it
	 * 
	 * @param xmlConceptDescriptionRefsObject a Map containing the XML tag &lt;aas:conceptDictionaries&gt;
	 * @return a Set of IConceptDictionary objects parsed form the given XML Map
	 */
	@SuppressWarnings("unchecked")
	private static Set<IConceptDictionary> parseConceptDictionaries(Map<String, Object> xmlConceptDescriptionRefsObject) {
		Set<IConceptDictionary> conceptDictionarySet = new HashSet<>();
		if(xmlConceptDescriptionRefsObject == null) return conceptDictionarySet;
		
		Map<String, Object> xmlConceptDictionaries = (Map<String, Object>) xmlConceptDescriptionRefsObject.get(CONCEPT_DICTIONARIES);
		if(xmlConceptDictionaries == null) return conceptDictionarySet;
		
		List<Map<String, Object>> xmlConceptDictionaryList = XMLHelper.getList(xmlConceptDictionaries.get(CONCEPT_DICTIONARY));
		for (Map<String, Object> xmlConceptDictionary : xmlConceptDictionaryList) {
			ConceptDictionary conceptDictionary = new ConceptDictionary();
			ReferableXMLConverter.populateReferable(xmlConceptDictionary, conceptDictionary);
			
			Map<String, Object> xmlConceptDescriptionRefs = (Map<String, Object>) xmlConceptDictionary.get(CONCEPT_DESCRIPTION_REFS);
			
			HashSet<IReference> referenceSet = new HashSet<>();
			
			List<Map<String, Object>> xmlConceptDescriptionRefsList = XMLHelper.getList(xmlConceptDescriptionRefs.get(CONCEPT_DESCRIPTION_REF));
			for (Map<String, Object> xmlConceptDescriptionRef : xmlConceptDescriptionRefsList) {
				referenceSet.add(ReferenceXMLConverter.parseReference(xmlConceptDescriptionRef));
			}
			
			conceptDictionary.setConceptDescription(referenceSet);
			conceptDictionarySet.add(conceptDictionary);
		}
		
		return conceptDictionarySet;
	}
	
	
	
	
	/**
	 * Builds &lt;aas:assetAdministrationShells&gt; from a given Collection of IAssetAdministrationShell objects
	 * 
	 * @param document the XML document
	 * @param assetAdministrationShells a Collection of IAssetAdministrationShell objects to build the XML for
	 * @return the &lt;aas:assetAdministrationShells&gt; XML tag for the given IAssetAdministrationShell objects
	 */
	public static Element buildAssetAdministrationShellsXML(Document document, Collection<IAssetAdministrationShell> assetAdministrationShells) {
		Element root = document.createElement(ASSET_ADMINISTRATION_SHELLS);
		
		List<Element> xmlAASList = new ArrayList<Element>();
		for (IAssetAdministrationShell aas : assetAdministrationShells) {
			Element aasRoot = document.createElement(ASSET_ADMINISTRATION_SHELL);

			IdentifiableXMLConverter.populateIdentifiableXML(document, aasRoot, aas);
			HasDataSpecificationXMLConverter.populateHasDataSpecificationXML(document, aasRoot, aas);
			
			buildDerivedFrom(document, aasRoot, aas);
			buildAssetRef(document, aasRoot, aas);
			buildSubmodelRef(document, aasRoot, aas);
			Set<IView> views = aas.getViews();
			
			Element buildViews = ViewXMLConverter.buildViewsXML(document, views);
			aasRoot.appendChild(buildViews);
			aasRoot.appendChild(buildConceptDictionary(document, aas));

			xmlAASList.add(aasRoot);
		}
		
		for (Element element : xmlAASList) {
			root.appendChild(element);
		}
		return root;
	}
	
	
	/**
	 * Builds &lt;aas:derivedFrom&gt; from a given IAssetAdministrationShell object
	 * 
	 * @param document the XML document
	 * @param root the XML tag to be populated
	 * @param aas the IAssetAdministrationShell object to build the XML for
	 */
	private static void buildDerivedFrom(Document document, Element root, IAssetAdministrationShell aas) {
		IReference derivedFrom = aas.getDerivedFrom();
		if(derivedFrom != null) {
			Element derivedFromRoot = document.createElement(DERIVED_FROM);
			derivedFromRoot.appendChild(ReferenceXMLConverter.buildReferenceXML(document, derivedFrom)); 
			root.appendChild(derivedFromRoot);
		}
	}
	

	/**
	 * Builds &lt;aas:assetRef&gt; from a given IAssetAdministrationShell object
	 * 
	 * @param document the XML document
	 * @param root the XML tag to be populated
	 * @param aas the IAssetAdministrationShell object to build the XML for
	 */
	private static void buildAssetRef(Document document, Element root, IAssetAdministrationShell aas) {
		IReference assetRef = aas.getAsset().getAssetIdentificationModel();
		if(assetRef!=null) {
			Element assetrefRoot = document.createElement(ASSET_REF);
			HashSet<IReference> set = new HashSet<IReference>();
			set.add(assetRef);
			assetrefRoot.appendChild(ReferenceXMLConverter.buildReferencesXML(document, set)); 
			root.appendChild(assetrefRoot);
		}
	}
	

	/**
	 * Builds &lt;aas:submodelRefs&gt; from a given IAssetAdministrationShell object
	 * 
	 * @param document the XML document
	 * @param root the XML tag to be populated
	 * @param aas the IAssetAdministrationShell object to build the XML for
	 */
	private static void buildSubmodelRef(Document document, Element root, IAssetAdministrationShell aas) {
		Set<IReference> submodelRef = aas.getSubmodelReferences();
		
		
		if (submodelRef != null && submodelRef.size() > 0) {
			Element submodelRefsRoot = document.createElement(SUBMODEL_REFS);
			Element submodelRefRoot = document.createElement(SUBMODEL_REF);
			submodelRefsRoot.appendChild(submodelRefRoot);
			submodelRefRoot.appendChild(ReferenceXMLConverter.buildReferencesXML(document, submodelRef)); 
			root.appendChild(submodelRefsRoot);
		}
	}
	

	/**
	 * Builds &lt;aas:conceptDictionaries&gt; from a given IAssetAdministrationShell object
	 * 
	 * @param document the XML document
	 * @param aas the IAssetAdministrationShell object to build the XML for
	 * @return the &lt;aas:conceptDictionaries&gt; XML tag build from the IAssetAdministrationShell object
	 */
	private static Element buildConceptDictionary(Document document, IAssetAdministrationShell aas) {
		Set<IConceptDictionary> conceptDicionary = aas.getConceptDictionary();
		Element conceptDicts = document.createElement(CONCEPT_DICTIONARIES);
		for(IConceptDictionary iConceptDictionary: conceptDicionary) {
			Element conceptDict = document.createElement(CONCEPT_DICTIONARY);
			Element concDescRoot = document.createElement(CONCEPT_DESCRIPTION_REFS);
			if (iConceptDictionary.getIdShort() != null) {
				Element idShort = document.createElement(ReferableXMLConverter.ID_SHORT);
				idShort.appendChild(document.createTextNode(iConceptDictionary.getIdShort()));
				conceptDict.appendChild(idShort);
			}
			conceptDict.appendChild(concDescRoot);
			conceptDicts.appendChild(conceptDict);
			Set<IReference> conceptDescriptionRef = iConceptDictionary.getConceptDescription();
			for (IReference ref: conceptDescriptionRef) {
				if(ref != null) {
					Element conceptDescriptionRefRoot = document.createElement(CONCEPT_DESCRIPTION_REF);
					concDescRoot.appendChild(conceptDescriptionRefRoot);
					conceptDescriptionRefRoot.appendChild(ReferenceXMLConverter.buildReferenceXML(document, ref));
				}
			}
			
		}
		return conceptDicts;
	}

}