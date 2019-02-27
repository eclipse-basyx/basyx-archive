package org.eclipse.basyx.onem2m.manager;

import org.eclipse.basyx.onem2m.xml.protocols.Resource;
import org.eclipse.basyx.onem2m.xml.protocols.Rsp;

public class ResourceResult<T extends Resource> {
	Rsp response;
	T resource;

	public ResourceResult(Rsp _response, T _resource) {
		this.response = _response;
		this.resource = _resource;
	}

	public Rsp getResponse() {
		return response;
	}

	public T getResource() {
		return resource;
	}

}
