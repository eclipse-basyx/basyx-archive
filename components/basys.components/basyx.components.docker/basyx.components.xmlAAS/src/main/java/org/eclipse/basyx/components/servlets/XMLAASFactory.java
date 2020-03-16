package org.eclipse.basyx.components.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.basyx.aas.factory.xml.XMLToMetamodelConverter;
import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.api.parts.asset.IAsset;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.aas.restapi.AASModelProvider;
import org.eclipse.basyx.aas.restapi.VABMultiSubmodelProvider;
import org.eclipse.basyx.components.configuration.BaSyxConfiguration;
import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.eclipse.basyx.submodel.metamodel.api.IElement;
import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.api.parts.IConceptDescription;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.restapi.SubModelProvider;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxContext;
import org.eclipse.basyx.vab.protocol.http.server.VABHTTPInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

/**
 * It imports AAS from given XML location provided in the context.properties and
 * maps the AAS to servlet. It also adds the submodels, assets and concept
 * descriptors to the AAS.
 * 
 * 
 * @author haque
 */
public class XMLAASFactory {
	// Create a logger instance for current class
	private static Logger logger = LoggerFactory.getLogger(XMLAASFactory.class);

	/**
	 * Generates a Basyx Context
	 * 
	 * @param config Configuration of the Basyx Context
	 * @return context
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	public static BaSyxContext createContext(BaSyxContextConfiguration config)
			throws ParserConfigurationException, SAXException, IOException {

		// Create a Basyx Context from the config
		BaSyxContext context = new BaSyxContext(config.getContextPath(), config.getDocBasePath(), config.getHostname(),
				config.getPort());

		// Converts the XML from location to Metamodel
		XMLToMetamodelConverter converter = convertXMLToMetamodel(config.getProperty("xmlPath"));

		String contextURL = getContextURL(config);
		addAAS(converter, context, contextURL);
		return context; 
	}

	/**
	 * Converts XML to Metamodel from XML location
	 * 
	 * @param xmlPath the location of the XML
	 * @return Converted Metamodel
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	private static XMLToMetamodelConverter convertXMLToMetamodel(String xmlPath)
			throws ParserConfigurationException, SAXException, IOException {
		String output = BaSyxConfiguration.getResourceString(xmlPath);
		return new XMLToMetamodelConverter(output);
	}

	/**
	 * Adds Asset Administration Shells from the XML to context
	 * 
	 * @param converter  Converted Metamodel
	 * @param context    Basyx Context
	 * @param contextURL URL of the context
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	private static void addAAS(XMLToMetamodelConverter converter, BaSyxContext context, String contextURL)
			throws ParserConfigurationException, SAXException, IOException {
		// Parse the XML and get all AAS
		List<IAssetAdministrationShell> AAShells = converter.parseAAS();

		if (AAShells.size() <= 0) {
			logger.debug("No AAS Found in the XML");
			return;
		}

		// Take the first AAS from the XML
		AssetAdministrationShell aas = (AssetAdministrationShell) AAShells.get(0);
		AASModelProvider aasProvider = new AASModelProvider(aas);
		VABMultiSubmodelProvider aasModelProvider = new VABMultiSubmodelProvider();
		aasModelProvider.setAssetAdministrationShell(aasProvider);

		// Add Submodels. Assets and Concept Descriptions to the AAS
		addSubmodels(converter, aas, aasModelProvider, contextURL);
		addAssets(converter, aas);
		addConceptDescriptions(converter, aas);

		// Create a servlet using the AAS
		HttpServlet aasServlet = new VABHTTPInterface<IModelProvider>(aasModelProvider);

		String subDomain = getSubDomain(aas);
		context.addServletMapping(subDomain, aasServlet);

		logger.info("AAS Server started at " + subDomain);
	}

	/**
	 * Adds Submodels from the XML to the given AAS
	 * 
	 * @param converter   converted Metamodel
	 * @param aas         AAS with which the Submodels will be connected
	 * @param aasProvider
	 * @param contextURL  URL of the context
	 */
	private static void addSubmodels(XMLToMetamodelConverter converter, AssetAdministrationShell aas,
			VABMultiSubmodelProvider aasProvider, String contextURL) {
		// Parse the XML to get the submodels
		List<ISubModel> subModels = converter.parseSubmodels();

		if (subModels.size() <= 0) {
			logger.debug("No Submodel found in the XML");
		}

		// Loops through all the submodels and add to AAS
		for (ISubModel subModel : subModels) {
			SubModelProvider subModelProvider = new SubModelProvider((SubModel) subModel);
			String idString = subModel.getIdShort();

			// Add the sub model to the AAS Provider
			aasProvider.addSubmodel(idString, subModelProvider);

			// Retrieve a submodel URL and connect it to the AAS
			String submodelUrlString = getSubModelUrl(contextURL, aas.getIdShort(), idString);
			aas.addSubModel(new SubmodelDescriptor(subModel, submodelUrlString));
		}
	}

	/**
	 * Adds Assets from the XML to the given AAS
	 * 
	 * @param converter converted Metamodel
	 * @param aas       AAS with which the assets will be connected
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	private static void addAssets(XMLToMetamodelConverter converter, AssetAdministrationShell aas)
			throws ParserConfigurationException, SAXException, IOException {
		// Parse the XML to get the assets
		List<IAsset> assets = converter.parseAssets();

		if (assets.size() <= 0) {
			logger.debug("No Assets found in the XML");
		}

		// Loops through all the assets and add to AAS
		for (IAsset asset : assets) {
			aas.setAsset((Asset) asset);
		}
	}

	/**
	 * Adds Concept Descriptions from the XML to the given AAS
	 * 
	 * @param converter converted Metamodel
	 * @param aas       AAS with which the concepts descriptions will be added
	 */
	private static void addConceptDescriptions(XMLToMetamodelConverter converter, AssetAdministrationShell aas) {
		// Parse the XML to get the concept descriptions
		List<IConceptDescription> descriptions = converter.parseConceptDescriptions();

		if (descriptions.size() <= 0) {
			logger.debug("No Concept Descriptions found in the XML");
		}

		// Loops through all the concept descriptions and add to AAS
		for (IConceptDescription description : descriptions) {
			aas.addConceptDescription(description);
		}
	}

	/**
	 * Generate a sub domain to access the Element
	 * 
	 * @param element the Element to be accessed by the generated subdomain
	 * @return the subdomain
	 */
	private static String getSubDomain(IElement element) {
		return "/" + element.getIdShort() + "/*";
	}

	/**
	 * Generate submodel api url
	 * 
	 * @param aasId      Id of the AAS it is connected
	 * @param subModelId id of the submodel
	 * 
	 * @return subModel URL
	 */
	private static String getSubModelUrl(String contextURL, String aasId, String subModelId) {
		return contextURL + "/" + aasId + "/aas/submodels/" + subModelId + "/submodel";
	}

	/**
	 * generates context URL
	 * 
	 * @param config
	 * @return Context URL
	 */
	private static String getContextURL(BaSyxContextConfiguration config) {
		return "http://" + config.getHostname() + ":" + config.getPort() + config.getContextPath();
	}
}