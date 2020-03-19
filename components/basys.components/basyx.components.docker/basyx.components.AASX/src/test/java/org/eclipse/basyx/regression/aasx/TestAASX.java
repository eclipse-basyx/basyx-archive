package org.eclipse.basyx.regression.aasx;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.eclipse.basyx.components.executable.AASXExecutable;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

/**
 * Test accessing to AAS using basys aas SDK
 * 
 * @author zhangzai
 *
 */
public class TestAASX extends AASXSuite {
	private static Logger logger = LoggerFactory.getLogger(TestAASX.class);

	@BeforeClass
	public static void setUpClass() throws ParserConfigurationException, SAXException, IOException {
		AASXExecutable.main(new String[] {});

		BaSyxContextConfiguration config = new BaSyxContextConfiguration();
		config.loadFromResource(BaSyxContextConfiguration.DEFAULT_CONFIG_PATH);

		aasEndpoint = "http://" + config.getHostname() + ":" + config.getPort() + "/" + config.getContextPath() + "/" + aasShortId + "/aas";
		smEndpoint = "http://" + config.getHostname() + ":" + config.getPort() + "/" + config.getContextPath() + "/" + aasShortId + "/aas/submodels/" + smShortId + "/submodel";
		logger.info("AAS URL for servlet test: " + aasEndpoint);
	}
}


