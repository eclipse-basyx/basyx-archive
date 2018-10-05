package org.eclipse.basyx.onem2m.manager;

import org.eclipse.basyx.onem2m.xml.protocols.Rsp;

public class DataResult<T extends Object> {
	Rsp response;
	T data;
	
	public DataResult(Rsp _response, T _data) {
		this.response = _response;
		this.data = _data;
	}

	public Rsp getResponse() {
		return response;
	}

	public T getData() {
		return data;
	}
	
	
}
