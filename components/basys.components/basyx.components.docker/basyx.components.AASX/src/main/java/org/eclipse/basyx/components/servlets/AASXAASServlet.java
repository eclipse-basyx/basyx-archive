package org.eclipse.basyx.components.servlets;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.basyx.components.aasx.AASXPackageManager;
import org.eclipse.basyx.components.servlet.aas.AASBundleServlet;
import org.xml.sax.SAXException;

/**
 * It generates the AAS-bundle using AASX package manager. It also maps the AAS
 * to servlet and adds the submodels, assets and concept descriptors to the AAS.
 * 
 * 
 * @author zhangzai
 */
public class AASXAASServlet extends AASBundleServlet {
	private static final long serialVersionUID = -3487515646027982620L;



	public AASXAASServlet(String filePath) throws ParserConfigurationException, SAXException, IOException {
		super(new AASXPackageManager(filePath).retrieveAASBundles());

	}
}
