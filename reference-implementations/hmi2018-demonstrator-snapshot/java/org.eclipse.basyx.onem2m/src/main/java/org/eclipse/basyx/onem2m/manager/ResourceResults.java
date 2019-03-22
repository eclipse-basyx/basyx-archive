package org.eclipse.basyx.onem2m.manager;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.basyx.onem2m.xml.protocols.Resource;
import org.eclipse.basyx.onem2m.xml.protocols.Rsp;

public class ResourceResults<T extends Resource> {
	Collection<Rsp> responses = new ArrayList<>();
	T resource;

	public ResourceResults() {
	}

	public Collection<Rsp> getResponses() {
		return responses;
	}

	public T getResource() {
		return resource;
	}

	void setResource(T resource) {
		this.resource = resource;
	}

}
