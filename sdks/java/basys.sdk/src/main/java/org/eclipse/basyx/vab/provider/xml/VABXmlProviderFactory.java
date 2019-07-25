package org.eclipse.basyx.vab.provider.xml;

import org.eclipse.basyx.vab.provider.hashmap.VABHashmapProvider;

/**
 * Provides the VAB elements from given xml string.
 * 
 * @author kannoth
 *
 */
public class VABXmlProviderFactory {

	public VABXmlProviderFactory() {
		// Empty Constructor
	}

	public VABHashmapProvider createVABElements(String aasXml) {
		try {
			return (new VABHashmapProvider(XmlParser.buildXmlMap(aasXml)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
