package org.eclipse.basyx.aas.backend.connected;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.vab.core.proxy.VABElementProxy;

/**
 * Conntected Element superclass; Extends HashMap for local caching used for c# proxy
 * @author pschorn
 *
 */
public class ConnectedElement {

	private VABElementProxy proxy;
	
	/*
	 * Stores element meta-information if retrieved from c# sdk
	 */
	private HashMap<String, Object> localInformation = new HashMap<String, Object> ();
	

	public VABElementProxy getProxy() {
		return proxy;
	}

	public ConnectedElement(VABElementProxy proxy) {
		super();
		this.proxy = proxy;
	}
	
	protected void putLocal(String key, Object value) {
		this.localInformation.put(key, value);
	}
	
	public void putAllLocal(Map<String, Object> opNode) {
		this.localInformation.putAll(opNode);
	}
	
	protected Object getLocal(String key) {
		return this.localInformation.get(key);
	}
}
