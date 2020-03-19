package org.eclipse.basyx.components.aasx;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.eclipse.basyx.components.configuration.BaSyxConfiguration;
import org.eclipse.basyx.components.xml.XMLAASBundleFactory;
import org.eclipse.basyx.support.AASBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * The AASX package converter converts a aasx package into a list of aas, a list
 * of submodels a list of assets, a list of Concept descriptions
 * 
 * The aas provides the references to the submodels and assets
 * 
 * @author zhangzai
 *
 */
public class AASXPackageManager {
	/**
	 * Input stream of the aasx package
	 */
	private ZipInputStream stream;


	/**
	 * Path to the AASX package
	 */
	private String aasxPath;

	/**
	 * AAS bundle factory
	 */
	private XMLAASBundleFactory bundleFactory;

	/**
	 * Logger
	 */
	private static Logger logger = LoggerFactory.getLogger(AASXPackageManager.class);

	/**
	 * Constructor
	 */
	public AASXPackageManager(String path) {
		aasxPath = path;
	}

	public Set<AASBundle> retrieveAASBundles() throws IOException, ParserConfigurationException, SAXException {
		bundleFactory = new XMLAASBundleFactory(getXMLResourceString(aasxPath));
		
		return bundleFactory.create();
	}

	/**
	 * Find the path of the aas-xml file
	 * 
	 * @param stream - Stream of the aasx package
	 * @return Path of the aas xml file, empty string if not found
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	private String findAASXml(ZipInputStream stream) throws IOException, ParserConfigurationException, SAXException {
		String path = "";
		// find the entry of the aasx
		for (ZipEntry entry; (entry = stream.getNextEntry()) != null;) {

			// get name of the entry
			String name = entry.getName();

			// find the relationship file in the directory /aasx/_rels/aas_origin.rels
			if (!entry.isDirectory() && name.startsWith("aasx/_rels")) {
				// find the file aasx-origin.rels
				if (name.endsWith("aasx-origin.rels")) {
					// Get path of the aas xml
					String aasXmlPath = findAASXMLAddress(stream);
					if (!aasXmlPath.isEmpty()) {
						path = aasXmlPath;
						break;
					}
				}
			}
		}
		return path;
	}

	/**
	 * Get entry of a file
	 * 
	 * @param filename - name of a file with path
	 * @return a file entry
	 * @throws IOException
	 */
	private ZipInputStream returnFileEntryStream(String filename) throws IOException {
		ZipInputStream str = null;
		if (filename.startsWith("/")) {
			filename = filename.substring(1);
		}

		// get all entries of the aasx
		for (ZipEntry e; (e = stream.getNextEntry()) != null;) {
			// get name of the entry
			String name = e.getName();
			if (name.equals(filename)) {
				str = stream;
				break;
			}
		}
		return str;
	}

	/**
	 * Parse the relationship file and find the path of the aas-XML file describing
	 * the aas
	 * 
	 * @param ins - input stream of this relationship file
	 * @return path of the aas-xml file
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	private String findAASXMLAddress(InputStream ins) throws ParserConfigurationException, SAXException, IOException {
		String path = "";

		// create the XML document parser
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(ins);
		doc.getDocumentElement().normalize();

		// Get the tag with "Relationships"
		logger.info("Root element :" + doc.getDocumentElement().getNodeName());
		NodeList relList = doc.getElementsByTagName("Relationship");

		// If there is only 1 relationship pointing to the aas-xml file, this should be
		// the case
		if (relList.getLength() == 1) {
			Node first = relList.item(0);

			if (first.getNodeType() == Node.ELEMENT_NODE) {
				logger.info("\nCurrent Element :" + first.getNodeName());
				// get the target file path
				String targetFile = ((Element) first).getAttribute("Target");
				String type = ((Element) first).getAttribute("Type");

				// validate the relationship type
				if (type.endsWith("aas-spec")) {
					logger.info("target file name : " + targetFile);
					path = targetFile;
				}
			}
		}
		return path;
	}

	/**
	 * Return the Content of the xml file in the aasx-package as String
	 * 
	 * @param filePath - path to the aasx package
	 * @return Content of XML as String
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	private String getXMLResourceString(String filePath) throws IOException, ParserConfigurationException, SAXException {
		String aasXmlPath;
		// Create the zip input stream
		try (ZipInputStream stream = new ZipInputStream(BaSyxConfiguration.getResourceStream(filePath))) {

			// find the path of the aas xml
			aasXmlPath = this.findAASXml(stream);
		}

		try (ZipInputStream stream = new ZipInputStream(BaSyxConfiguration.getResourceStream(filePath))) {
			this.stream = stream;
			// Find the entry of the aas xml
			ZipInputStream streamPointingToEntry = this.returnFileEntryStream(aasXmlPath);

			// create the xml-converter with the input stream
			String text = IOUtils.toString(streamPointingToEntry, StandardCharsets.UTF_8.name());
			return text;
		}
	}



}
