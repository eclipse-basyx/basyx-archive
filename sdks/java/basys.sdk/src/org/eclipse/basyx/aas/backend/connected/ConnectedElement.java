package org.eclipse.basyx.aas.backend.connected;

import org.eclipse.basyx.vab.core.proxy.VABElementProxy;

public class ConnectedElement {
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

	protected String getPath() {
		return path;
	}
}
