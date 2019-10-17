package org.eclipse.basyx.submodel.metamodel.connected;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.IElement;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;
import org.eclipse.basyx.vab.model.VABModelMap;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;

/**
 * Conntected Element superclass; Extends HashMap for local caching used for c# proxy
 * 
 * @author pschorn
 *
 */
public class ConnectedElement implements IElement {

	private VABElementProxy proxy;

	/*
	 * Stores element meta-information if retrieved from c# sdk
	 */
	private HashMap<String, Object> localInformation = new HashMap<String, Object>();

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

	@SuppressWarnings("unchecked")
	protected VABModelMap<Object> getElem() {
		return new VABModelMap<Object>((Map<String, Object>) getProxy().getModelPropertyValue(""));
	}

	protected void throwNotSupportedException() {
		throw new RuntimeException("Not supported on remote object");
	}

	@Override
	public String getIdShort() {
		return (String) getElem().getPath(Referable.IDSHORT);
	}

	@Override
	public void setIdShort(String id) {
		throwNotSupportedException();
	}
}
