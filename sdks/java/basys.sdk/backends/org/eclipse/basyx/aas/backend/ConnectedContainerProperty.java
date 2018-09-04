package org.eclipse.basyx.aas.backend;

import org.eclipse.basyx.aas.api.resources.basic.IContainerProperty;
import org.eclipse.basyx.aas.api.resources.basic.IProperty;
import org.eclipse.basyx.aas.api.services.IModelProvider;
import org.eclipse.basyx.aas.impl.reference.ElementRef;

public class ConnectedContainerProperty extends ConnectedProperty implements IContainerProperty {

	public ConnectedContainerProperty(String aasId, String submodelId, String path, IModelProvider provider, ConnectedAssetAdministrationShellManager aasMngr) {
		super(aasId, submodelId, path, provider, aasMngr);
	}

	@Override
	public IProperty getProperty(String name) {
		return aasManager.retrievePropertyProxy(new ElementRef(aasID, aasSubmodelID, getId() + "." + name));
	}

}
