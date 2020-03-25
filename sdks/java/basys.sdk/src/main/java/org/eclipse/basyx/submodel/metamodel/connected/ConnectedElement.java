package org.eclipse.basyx.submodel.metamodel.connected;

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

	public VABElementProxy getProxy() {
		return proxy;
	}

	public ConnectedElement(VABElementProxy proxy) {
		super();
		this.proxy = proxy;
	}


	@SuppressWarnings("unchecked")
	protected VABModelMap<Object> getElem() {
		VABModelMap<Object> map = new VABModelMap<Object>((Map<String, Object>) getProxy().getModelPropertyValue(""));
		return map;
	}

	protected void throwNotSupportedException() {
		throw new RuntimeException("Not supported on remote object");
	}

	@Override
	public String getIdShort() {
		return (String) getElem().getPath(Referable.IDSHORT);
	}
}
