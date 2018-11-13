package org.eclipse.basyx.aas.backend.connected.property;

import java.util.Map;

import org.eclipse.basyx.aas.api.resources.IProperty;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;

/**
 * Creates IProperties based on the attached meta data as specified in VWiD
 * document
 * 
 * @author schnicke
 *
 */
public class ConnectedPropertyFactory {
	@SuppressWarnings("unchecked")
	public IProperty createProperty(String path, VABElementProxy proxy) {
		Map<String, Object> property = (Map<String, Object>) proxy.readElementValue(path);
		if (property.containsKey("properties")) {
			return new ConnectedContainerProperty(path, proxy);
		} else if (property.containsKey("valueType")) {
			Map<String, Object> valueType = (Map<String, Object>) property.get("valueType");
			if ((boolean) valueType.get("isMap")) {
				return new ConnectedMapProperty(path, proxy);
			} else if ((boolean) valueType.get("isCollection")) {
				return new ConnectedCollectionProperty(path, proxy);
			} else {
				return new ConnectedSingleProperty(path, proxy);
			}
		}
		System.err.println("Unknown property type");
		return null;
	}
}
