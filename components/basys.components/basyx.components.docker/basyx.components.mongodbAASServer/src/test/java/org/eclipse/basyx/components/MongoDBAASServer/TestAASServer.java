package org.eclipse.basyx.components.MongoDBAASServer;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.basyx.components.MongoDBAASServerComponent;
import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.eclipse.basyx.components.mongodb.MongoDBAASAggregator;
import org.junit.BeforeClass;
import org.xml.sax.SAXException;

/**
 * Tests the component using the test suite
 * 
 * @author espen
 *
 */
public class TestAASServer extends AASServerSuite {

	private static MongoDBAASServerComponent component;

	@Override
	protected String getURL() {
		return component.getURL();
	}

	@BeforeClass
	public static void setUpClass() throws ParserConfigurationException, SAXException, IOException {
		// just reset the data with this default db configuration
		new MongoDBAASAggregator(BaSyxContextConfiguration.DEFAULT_CONFIG_PATH).reset();

		BaSyxContextConfiguration config = new BaSyxContextConfiguration();
		config.loadFromResource(BaSyxContextConfiguration.DEFAULT_CONFIG_PATH);
		component = new MongoDBAASServerComponent(config.getHostname(), config.getPort(), config.getContextPath(),
				config.getDocBasePath());
		component.startComponent();
	}
}
