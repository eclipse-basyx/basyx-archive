package org.eclipse.basyx.regression.AASServer;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.basyx.aas.aggregator.restapi.AASAggregatorProvider;
import org.eclipse.basyx.components.aas.AASServerComponent;
import org.eclipse.basyx.components.aas.configuration.AASServerBackend;
import org.eclipse.basyx.components.aas.configuration.BaSyxAASServerConfiguration;
import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

public class TestXMLAAS extends XMLAASSuite {
	private static Logger logger = LoggerFactory.getLogger(TestXMLAAS.class);
	private static AASServerComponent component;


	@BeforeClass
	public static void setUpClass() throws ParserConfigurationException, SAXException, IOException {
		// Setup component's test configuration
		BaSyxContextConfiguration contextConfig = new BaSyxContextConfiguration();
		contextConfig.loadFromResource(BaSyxContextConfiguration.DEFAULT_CONFIG_PATH);
		BaSyxAASServerConfiguration aasConfig = new BaSyxAASServerConfiguration(AASServerBackend.INMEMORY, "xml/aas.xml");

		// Start component
		component = new AASServerComponent(contextConfig, aasConfig);
		component.startComponent();

		String rootEndpoint = "http://" + contextConfig.getHostname() + ":" + contextConfig.getPort() + "/"
				+ contextConfig.getContextPath() + "/";
		aasEndpoint = rootEndpoint + "/" + AASAggregatorProvider.PREFIX + "/" + aasId.getEncodedURN() + "/aas";
		smEndpoint = aasEndpoint + "/submodels/" + smShortId + "/submodel";
		logger.info("AAS URL for servlet test: " + aasEndpoint);
	}

	@AfterClass
	public static void tearDownClass() {
		component.stopComponent();
	}
}
