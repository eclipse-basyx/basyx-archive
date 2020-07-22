package org.eclipse.basyx.components.executable;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.servlet.ServletException;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.basyx.components.AASXComponent;
import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

/**
 * Starts an HTTP server providing multiple AAS and submodels as described in
 * the AASX package file specified in the properties file <br />
 * They are made available at <i>localhost:4000/aasx/$aasId/aas</i><br />
 * <br />
 * <b>Please note:</b> Neither the AASs nor the Submodels are added to the
 * registry. Additionally, the Submodel descriptors inside the AAS are missing.
 * <br />
 * There reason for this is, that the executable does not know about the outside
 * context (e.g. docker, ...)!
 * 
 * @author zhang
 */
public class AASXExecutable {
	private static Logger logger = LoggerFactory.getLogger(AASXExecutable.class);

	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, URISyntaxException, ServletException {
		logger.info("Starting BaSyx AASX component");

		// Load configuration
		BaSyxContextConfiguration config = new BaSyxContextConfiguration();
		if (args.length > 0 && args[0] instanceof String) {
			// file path available? => load configs from file
			config.loadFromFile(args[0]);
		} else {
			// fallback: load default configs (in resources)
			config.loadFromResource(BaSyxContextConfiguration.DEFAULT_CONFIG_PATH);
		}

		// In addition to the context for the AAS, also the registryUrl can be specified
		String registryUrl = config.getProperty("registry");

		String rootPath = new File(AASXExecutable.class.getProtectionDomain().getCodeSource().getLocation().toURI())
				.getParentFile().getPath();
		String docPath = rootPath + config.getDocBasePath();
		// Get the path to the doc base path
		AASXComponent component = new AASXComponent(config.getHostname(), config.getPort(), config.getContextPath(),
				docPath, config.getProperty("aasxPath"), registryUrl);
		component.startComponent();
	}
}
