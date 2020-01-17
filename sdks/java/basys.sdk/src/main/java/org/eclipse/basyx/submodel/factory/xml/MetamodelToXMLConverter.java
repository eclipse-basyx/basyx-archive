package org.eclipse.basyx.submodel.factory.xml;

import java.util.Collection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;

import org.eclipse.basyx.aas.factory.xml.api.parts.AssetXMLConverter;
import org.eclipse.basyx.aas.factory.xml.api.parts.ConceptDescriptionXMLConverter;
import org.eclipse.basyx.aas.factory.xml.converters.AssetAdministrationShellXMLConverter;
import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.api.parts.IAsset;
import org.eclipse.basyx.aas.metamodel.api.parts.IConceptDescription;
import org.eclipse.basyx.submodel.factory.xml.converters.SubmodelXMLConverter;
import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * This class can be used to build XML from Metamodel Objects
 * 
 * @author conradi
 *
 */
public class MetamodelToXMLConverter {
	
	/**
	 * Builds the XML for the given metamodel Objects
	 * 
	 * @param aasList the AASs to build the XML for
	 * @param assetList the Assets to build the XML for
	 * @param conceptDescriptionList the ConceptDescriptions to build the XML for
	 * @param submodelList the SubModels to build the XML for
	 * @param result a Result object to write the XML to e.g. ResultStream
	 * @throws TransformerException
	 * @throws ParserConfigurationException
	 */
	public static void convertToXML(Collection<IAssetAdministrationShell> aasList, Collection<IAsset> assetList, 
			Collection<IConceptDescription> conceptDescriptionList, Collection<ISubModel> submodelList, Result result)
					throws TransformerException, ParserConfigurationException {
		
		DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
		Document document = documentBuilder.newDocument();
		
		//creating the root tag <aas:aasenv>
		Element root = document.createElement(XMLHelper.AASENV);
		
		//creating the Header information
		root.setAttribute("xmlns:aas", "http://www.admin-shell.io/aas/1/0");
		root.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		root.setAttribute("xmlns:IEC", "http://www.admin-shell.io/IEC61360/1/0");
		root.setAttribute("xsi:schemaLocation",
				"http://www.admin-shell.io/aas/1/0 AAS.xsd http://www.admin-shell.io/IEC61360/1/0 IEC61360.xsd");
		document.appendChild(root);

		
		Element buildAssetadminsroot = AssetAdministrationShellXMLConverter.buildAssetAdministrationShellsXML(document, aasList);
		root.appendChild(buildAssetadminsroot);
		
		Element assetsObj = AssetXMLConverter.buildAssetsXML(document, assetList);
		root.appendChild(assetsObj);
		
		Element conceptDescriptionObj = ConceptDescriptionXMLConverter.buildConceptDescriptionsXML(document, conceptDescriptionList);
		root.appendChild(conceptDescriptionObj);
		
		Element subModelsroot = SubmodelXMLConverter.buildSubmodelsXML(document, submodelList);
		root.appendChild(subModelsroot);
		
		
		//create the xml file
		//transform the DOM Object to an XML File
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		DOMSource domSource = new DOMSource(document);

		transformer.transform(domSource, result);
	}
}
