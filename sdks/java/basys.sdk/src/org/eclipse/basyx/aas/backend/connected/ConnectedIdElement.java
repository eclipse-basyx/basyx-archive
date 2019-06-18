package org.eclipse.basyx.aas.backend.connected;

import org.eclipse.basyx.aas.api.resources.IElement;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;

public class ConnectedIdElement extends ConnectedElement implements IElement {
	public ConnectedIdElement(String path, VABElementProxy proxy) {
		super(path, proxy);
	}

	@Override
	public String getId() {
		return (String) getProxy().readElementValue(constructPath("idShort"));
	}

	@Override
	public void setId(String id) {
		try {
			getProxy().updateElementValue(constructPath("idShort"), id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
