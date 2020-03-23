package org.eclipse.basyx.components.servlets;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.basyx.components.servlet.aas.AASBundleServlet;
import org.eclipse.basyx.components.xml.XMLAASBundleFactory;
import org.xml.sax.SAXException;

/**
 * It imports AAS from given XML location provided in the context.properties and
 * maps the AAS to servlet. It also adds the submodels, assets and concept
 * descriptors to the AAS.
 * 
 * 
 * @author haque, schnicke
 */
public class XMLAASServlet extends AASBundleServlet {
	private static final long serialVersionUID = -3487515646027982620L;

	public XMLAASServlet(String xmlContent) throws ParserConfigurationException, SAXException, IOException {
		super(new XMLAASBundleFactory(xmlContent).create());
	}
}