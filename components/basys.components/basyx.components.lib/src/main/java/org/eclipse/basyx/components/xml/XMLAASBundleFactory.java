package org.eclipse.basyx.components.xml;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.basyx.aas.factory.xml.XMLToMetamodelConverter;
import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.api.reference.IKey;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.support.AASBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

/**
 * Creates multiple {@link AASBundle} from an XML containing several AAS and
 * Submodels <br />
 * TODO: Assets, ConceptDescriptions
 * 
 * @author schnicke
 *
 */
public class XMLAASBundleFactory {
	private static Logger logger = LoggerFactory.getLogger(XMLAASBundleFactory.class);

	private String content;

	/**
	 * Internal exception used to indicate that the Submodel was not found
	 * 
	 * @author schnicke
	 *
	 */
	private class SubmodelNotFoundException extends Exception {
		private static final long serialVersionUID = -1247012719907370743L;
	}

	/**
	 * 
	 * @param xmlContent
	 *            the content of the XML
	 */
	public XMLAASBundleFactory(String xmlContent) {
		this.content = xmlContent;
	}

	public XMLAASBundleFactory(Path xmlFile) throws IOException {
		content = new String(Files.readAllBytes(xmlFile));
	}

	/**
	 * Creates the set of {@link AASBundle} contained in the XML string.
	 * 
	 * @return
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	public Set<AASBundle> create() throws ParserConfigurationException, SAXException, IOException {
		XMLToMetamodelConverter converter = new XMLToMetamodelConverter(content);

		List<IAssetAdministrationShell> shells = converter.parseAAS();
		List<ISubModel> submodels = converter.parseSubmodels();

		// TODO: Asset, ConceptDescription

		Set<AASBundle> bundles = new HashSet<>();

		for (IAssetAdministrationShell shell : shells) {
			Set<ISubModel> currentSM = new HashSet<>();

			for (IReference submodelRef : shell.getSubmodelReferences()) {
				try {
					ISubModel sm = getSubmodelByReference(submodelRef, submodels);
					currentSM.add(sm);
					logger.debug("Found Submodel " + sm.getIdShort() + " for AAS " + shell.getIdShort());
				} catch (SubmodelNotFoundException e) {
					// If there's no match, the submodel is assumed to be provided by different
					// means, e.g. it is already being hosted
					logger.warn("Could not find Submodel " + submodelRef.getKeys().get(0).getValue() + " for AAS " + shell.getIdShort() + "; If it is not hosted elsewhere this is an error!");
				}
			}
			bundles.add(new AASBundle(shell, currentSM));
		}

		return bundles;
	}

	private ISubModel getSubmodelByReference(IReference submodelRef, List<ISubModel> submodels) throws SubmodelNotFoundException {
		// It may be that only one key fits to the Submodel contained in the XML
		for (IKey key : submodelRef.getKeys()) {
			// There will only be a single submodel matching the identification at max
			Optional<ISubModel> match = submodels.stream().filter(s -> s.getIdentification().getId().equals(key.getValue())).findFirst();
			if (match.isPresent()) {
				return match.get();
			}
		}

		// If no submodel is found, indicate it by throwing an exception
		throw new SubmodelNotFoundException();
	}
}
