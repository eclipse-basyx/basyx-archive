package org.eclipse.basyx.components.aas.aasx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.eclipse.basyx.components.configuration.BaSyxConfiguration;
import org.eclipse.basyx.components.xml.XMLAASBundleFactory;
import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IFile;
import org.eclipse.basyx.support.bundle.AASBundle;
import org.eclipse.basyx.vab.modelprovider.VABPathTools;
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
	 * Path to the AASX package
	 */
	private String aasxPath;

	/**
	 * AAS bundle factory
	 */
	private XMLAASBundleFactory bundleFactory;
	
	/**
	 * Cache for generated Bundles
	 */
	private Set<AASBundle> bundles;

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
		
		// If the XML was already parsed return cached Bundles
		if(bundles != null) {
			return bundles;
		}
		
		bundleFactory = new XMLAASBundleFactory(getXMLResourceString(aasxPath));
		
		bundles = bundleFactory.create();
		
		return bundles;
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
	private ZipInputStream returnFileEntryStream(String filename, ZipInputStream stream) throws IOException {
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
		try (ZipInputStream stream = getZipInputStream(filePath)) {

			// find the path of the aas xml
			aasXmlPath = this.findAASXml(stream);
		}

		try (ZipInputStream stream = getZipInputStream(filePath)) {
			// Find the entry of the aas xml
			ZipInputStream streamPointingToEntry = this.returnFileEntryStream(aasXmlPath, stream);

			// create the xml-converter with the input stream
			String text = IOUtils.toString(streamPointingToEntry, StandardCharsets.UTF_8.name());
			return text;
		}
	}

	/**
	 * Load the referenced filepaths in the submodels such as PDF, PNG files from
	 * the package
	 * 
	 * @return a map of the folder name and folder path, the folder holds the files
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 * 
	 */
	private List<String> parseReferencedFilePathsFromAASX()
			throws IOException, ParserConfigurationException, SAXException {
		
		Set<AASBundle> bundles = retrieveAASBundles();
		
		List<ISubModel> submodels = new ArrayList<>();
		
		// Get the Submodels from all AASBundles
		for(AASBundle bundle: bundles) {
			submodels.addAll(bundle.getSubmodels());
		}
		
		List<String> paths = new ArrayList<String>();

		for(ISubModel sm: submodels) {
			paths.addAll(parseElements(sm.getSubmodelElements().values()));
		}
		return paths;
	}
	
	/**
	 * Gets the paths from a collection of ISubmodelElement
	 * 
	 * @param elements
	 * @return the Paths from the File elements
	 */
	private List<String> parseElements(Collection<ISubmodelElement> elements) {
		List<String> paths = new ArrayList<String>();
		
		for(ISubmodelElement element: elements) {
			if(element instanceof IFile) {
				IFile file = (IFile) element;
				// If the path contains a "://", we can assume, that the Path is a link to an other server
				// e.g. http://localhost:8080/aasx/...
				if(!file.getValue().contains("://")) {
					paths.add(file.getValue());
				}
			}
			else if(element instanceof ISubmodelElementCollection) {
				ISubmodelElementCollection collection = (ISubmodelElementCollection) element;
				paths.addAll(parseElements(collection.getSubmodelElements().values()));
			}
		}
		return paths;
	}

	/**
	 * Unzips all files referenced by the aasx file according to its relationships
	 * 
	 * 
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 * @throws URISyntaxException
	 */
	public void unzipRelatedFiles()
			throws IOException, ParserConfigurationException, SAXException, URISyntaxException {
		// load folder which stores the files
		List<String> files = parseReferencedFilePathsFromAASX();
		for (String filePath : files) {
			// name of the folder
			unzipFile(filePath, aasxPath);
		}
	}

	/**
	 * Create a folder to hold the unpackaged files The folder has the path
	 * \target\classes\docs
	 * 
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	private Path getRootFolder() throws IOException, URISyntaxException {
		URI uri = AASXPackageManager.class.getProtectionDomain().getCodeSource().getLocation().toURI();
		URI parent = new File(uri).getParentFile().toURI();
		return Paths.get(parent);
	}

	/**
	 * unzip the file folders
	 * 
	 * @param filePath - path of the file in the aasx to unzip
	 * @param aasxPath    - aasx path
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	private void unzipFile(String filePath, String aasxPath)
			throws IOException, URISyntaxException {
		// Create destination directory
		if (filePath.startsWith("/")) {
			filePath = filePath.substring(1);
		}
		logger.info("Unzipping " + filePath + " to root folder:");
		String relativePath = "files/" + VABPathTools.getParentPath(filePath);
		Path rootPath = getRootFolder();
		Path destDir = rootPath.resolve(relativePath);
		logger.info("Unzipping to " + destDir);
		Files.createDirectories(destDir);

		// create buffer for the folder binary
		byte[] buffer = new byte[1024];

		// Find the file with the "filePath"
		try (ZipInputStream stream = getZipInputStream(aasxPath)) {
			ZipEntry zipEntry = stream.getNextEntry();
			while (zipEntry != null) {
				if (!zipEntry.isDirectory() && zipEntry.getName().contains(filePath)) {
					// Create the file object in the destination directory
					File newFile = newFile(destDir.toFile(), zipEntry);

					// Create the file output stream
					try (FileOutputStream fos = new FileOutputStream(newFile)) {
						int len;
						// Write the binary to the file
						while ((len = stream.read(buffer)) > 0) {
							fos.write(buffer, 0, len);
						}
					}
					return;
				}
				zipEntry = stream.getNextEntry();
			}
		}
	}

	/**
	 * Preventing Zip Slip, create a file
	 * 
	 * @param destinationDir
	 * @param zipEntry
	 * @return
	 * @throws IOException
	 */
	private File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
		String filename = VABPathTools.getLastElement(zipEntry.getName());

		File destFile = new File(destinationDir, filename);

		String destDirPath = destinationDir.getCanonicalPath();
		String destFilePath = destFile.getCanonicalPath();

		if (!destFilePath.startsWith(destDirPath + File.separator)) {
			throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
		}

		return destFile;
	}

	private ZipInputStream getZipInputStream(String aasxFilePath) throws IOException {
		try {
			return new ZipInputStream(BaSyxConfiguration.getResourceStream(aasxFilePath));
		} catch (NullPointerException ex) {
			// Alternativ, if resource has not been found: load from a file
			return new ZipInputStream(new FileInputStream(aasxFilePath));
		}
	}
}
