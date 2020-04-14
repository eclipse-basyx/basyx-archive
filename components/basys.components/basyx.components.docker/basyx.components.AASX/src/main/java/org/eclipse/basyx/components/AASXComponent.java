package org.eclipse.basyx.components;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.basyx.components.aasx.AASXPackageManager;
import org.xml.sax.SAXException;

/**
 * A component that takes an AASX file and provides it via an HTTP server
 * 
 * @author schnicke, espen
 *
 */
public class AASXComponent extends XMLAASComponent {
	public AASXComponent(String hostName, int port, String path, String docBasePath, String aasxPath,
			String registryUrl) throws IOException, ParserConfigurationException, SAXException {
		super(hostName, port, path, docBasePath);
		setAASBundle(new AASXPackageManager(aasxPath).retrieveAASBundles());
		setRegistryUrl(registryUrl);
	}
}
