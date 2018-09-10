package org.eclipse.basyx.aas.backend;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.aas.api.reference.IElementReference;
import org.eclipse.basyx.aas.api.resources.basic.IContainerProperty;
import org.eclipse.basyx.aas.api.resources.basic.IProperty;
import org.eclipse.basyx.aas.api.services.IModelProvider;
import org.eclipse.basyx.aas.impl.tools.BaSysID;

public class ConnectedContainerProperty extends ConnectedProperty implements IContainerProperty {

	Map<String, IProperty> properties = new HashMap<>();
	
	@SuppressWarnings("unchecked")
	public ConnectedContainerProperty(String aasId, String submodelId, String path, IModelProvider provider, ConnectedAssetAdministrationShellManager aasMngr) {
		super(aasId, submodelId, path, provider, aasMngr);
		
		Map<String, IElementReference> elementRef = (Map<String, IElementReference>) provider.getModelPropertyValue(BaSysID.instance.buildPath(aasId, submodelId, path + ".properties", "properties"));
		for(String s : elementRef.keySet()) {
			properties.put(s, aasManager.retrievePropertyProxy(elementRef.get(s)));
		}
	}

	@Override
	public Map<String, IProperty> getProperties() {
		return properties;
	}

}
