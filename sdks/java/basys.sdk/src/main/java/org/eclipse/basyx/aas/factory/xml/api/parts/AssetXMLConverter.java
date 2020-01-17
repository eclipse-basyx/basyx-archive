package org.eclipse.basyx.aas.factory.xml.api.parts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.eclipse.basyx.aas.metamodel.api.parts.IAsset;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.submodel.factory.xml.XMLHelper;
import org.eclipse.basyx.submodel.factory.xml.converters.qualifier.HasDataSpecificationXMLConverter;
import org.eclipse.basyx.submodel.factory.xml.converters.qualifier.IdentifiableXMLConverter;
import org.eclipse.basyx.submodel.factory.xml.converters.qualifier.haskind.HasKindXMLConverter;
import org.eclipse.basyx.submodel.factory.xml.converters.reference.ReferenceXMLConverter;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Handles the conversion between IAsset objects and the XML tag &lt;aas:assets&gt; in both directions
 * 
 * @author conradi
 *
 */
public class AssetXMLConverter {

	public static final String ASSETS = "aas:assets";
	public static final String ASSET = "aas:asset";
	public static final String ASSET_IDENTIFICATION_MODEL_REF = "aas:assetIdentificationModelRef";
	
	/**
	 * Parses &lt;aas:assets&gt; and builds the Asset objects from it
	 * 
	 * @param xmlAssetObject a Map containing the content of the XML tag &lt;aas:assets&gt;
	 * @return a List of IAsset objects parsed form the given XML Map
	 */
	public static List<IAsset> parseAssets(Map<String, Object> xmlAssetObject) {
		List<Map<String, Object>> xmlAssets = XMLHelper.getList(xmlAssetObject.get(ASSET));
		List<IAsset> assets = new ArrayList<>();
		
		for (Map<String, Object> xmlAsset : xmlAssets) {
			Asset asset = new Asset();
			
			IdentifiableXMLConverter.populateIdentifiable(xmlAsset, asset);
			HasDataSpecificationXMLConverter.populateHasDataSpecification(xmlAsset, asset);
			HasKindXMLConverter.populateHasKind(xmlAsset, asset);
			
			if(xmlAsset.containsKey(ASSET_IDENTIFICATION_MODEL_REF)) {
				asset.setAssetIdentificationModel(parseAssetIdentificationModelRef(xmlAsset));
			}
			
			assets.add(asset);
		}
		return assets;
	}
	

	/**
	 * Parses &lt;aas:assetIdentificationModelRef&gt; and builds an IReference object from it
	 * 
	 * @param xmlObject a Map containing the XML tag &lt;aas:assetIdentificationModelRef&gt;
	 * @return an IReference object parsed form the given XML Map
	 */
	@SuppressWarnings("unchecked")
	private static IReference parseAssetIdentificationModelRef(Map<String, Object> xmlObject) {
		Map<String, Object> semanticIDObj = (Map<String, Object>) xmlObject.get(ASSET_IDENTIFICATION_MODEL_REF);
		return ReferenceXMLConverter.parseReference(semanticIDObj);
	}
	
	
	
	/**
	 * Builds &lt;aas:assets&gt; from a given Collection of IAsset objects
	 * 
	 * @param document the XML document
	 * @param assets a Collection of IAsset objects to build the XML for
	 * @return the &lt;aas:assets&gt; XML tag for the given IAsset objects
	 */
	public static Element buildAssetsXML(Document document, Collection<IAsset> assets) {
		Element root = document.createElement(ASSETS);
		
		List<Element> xmlAssetList = new ArrayList<>();
		for(IAsset asset: assets) {
			Element assetRoot = document.createElement(ASSET);
			IdentifiableXMLConverter.populateIdentifiableXML(document, assetRoot, asset);
			HasDataSpecificationXMLConverter.populateHasDataSpecificationXML(document, assetRoot, asset);
			HasKindXMLConverter.populateHasKindXML(document, assetRoot, asset);
			buildAssetIdentificationModelRef(document, assetRoot, asset);
			xmlAssetList.add(assetRoot);
		}
		
		for(Element element: xmlAssetList) {
			root.appendChild(element);
		}
		return root;
	}
	
	
	/**
	 * Builds &lt;aas:assetIdentificationModelRef&gt; from a given IAsset object
	 * 
	 * @param document the XML document
	 * @param assetRoot the XML tag to be populated
	 * @param asset the IAsset object to build the XML for
	 */
	private static void buildAssetIdentificationModelRef(Document document, Element assetRoot, IAsset asset) {
		IReference assetIdentificationModel = asset.getAssetIdentificationModel();
		if(assetIdentificationModel != null) {
			Element assetIdentificationroot = document.createElement(ASSET_IDENTIFICATION_MODEL_REF);
			HashSet<IReference> set = new HashSet<IReference>();
			set.add(assetIdentificationModel);
			assetIdentificationroot.appendChild(ReferenceXMLConverter.buildReferencesXML(document, set)); 
			assetRoot.appendChild(assetIdentificationroot);
		}
	}
}
