package org.eclipse.basyx.aas.backend.connected;

import org.eclipse.basyx.aas.api.resources.IElement;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;

public class ConnectedElement implements IElement {

	private VABElementProxy proxy;
	private String path;

	public VABElementProxy getProxy() {
		return proxy;
	}

	public ConnectedElement(String path, VABElementProxy proxy) {
		super();
		this.path = path;
		this.proxy = proxy;
	}

	protected String constructPath(String relativePath) {
		if (!path.isEmpty()) {
			return path + "/" + relativePath;
		} else {
			return relativePath;
		}
	}

	@Override
	public String getId() {
		return (String) proxy.readElementValue(constructPath("idShort"));
	}

	@Override
	public void setId(String id) {
		try {
			proxy.updateElementValue(constructPath("idShort"), id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
