package org.eclipse.basyx.aas.backend.connected.aas.reference;

import org.eclipse.basyx.aas.api.metamodel.aas.reference.IKey;
import org.eclipse.basyx.aas.backend.connected.ConnectedElement;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.reference.Key;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;

/**
 * "Connected" implementation of IKey
 * 
 * @author rajashek
 *
 */
public class ConnectedKey extends ConnectedElement implements IKey {
	public ConnectedKey(VABElementProxy proxy) {
		super(proxy);
	}

	@Override
	public String getType() {
		return (String) getProxy().getModelPropertyValue(Key.TYPE);
	}

	@Override
	public boolean isLocal() {
		return (boolean) getProxy().getModelPropertyValue(Key.LOCAL);
	}

	@Override
	public String getValue() {
		return (String) getProxy().getModelPropertyValue(Key.VALUE);
	}

	@Override
	public String getidType() {
		return (String) getProxy().getModelPropertyValue(Key.IDTYPE);
	}
}
