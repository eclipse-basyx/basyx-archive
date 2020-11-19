package org.eclipse.basyx.regression.AASServer;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.basyx.components.aas.AASServerComponent;
import org.eclipse.basyx.components.aas.configuration.AASServerBackend;
import org.eclipse.basyx.components.aas.configuration.BaSyxAASServerConfiguration;
import org.eclipse.basyx.components.aas.mongodb.MongoDBAASAggregator;
import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.eclipse.basyx.components.configuration.BaSyxMongoDBConfiguration;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.xml.sax.SAXException;

/**
 * Tests the component using the test suite
 * 
 * @author espen
 *
 */
public class TestMongoDBServer extends AASServerSuite {

	private static AASServerComponent component;

	@Override
	protected String getURL() {
		return component.getURL() + "/shells";
	}

	@BeforeClass
	public static void setUpClass() throws ParserConfigurationException, SAXException, IOException {
		// just reset the data with this default db configuration
		new MongoDBAASAggregator(BaSyxContextConfiguration.DEFAULT_CONFIG_PATH).reset();

		// Setup component configuration
		BaSyxContextConfiguration contextConfig = new BaSyxContextConfiguration();
		contextConfig.loadFromResource(BaSyxContextConfiguration.DEFAULT_CONFIG_PATH);
		BaSyxMongoDBConfiguration mongoDBConfig = new BaSyxMongoDBConfiguration();
		BaSyxAASServerConfiguration aasConfig = new BaSyxAASServerConfiguration(AASServerBackend.MONGODB, "");

		// Start component
		component = new AASServerComponent(contextConfig, aasConfig, mongoDBConfig);
		component.startComponent();
	}

	@AfterClass
	public static void tearDownClass() {
		component.stopComponent();
	}
}
