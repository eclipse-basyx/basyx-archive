package org.eclipse.basyx.aas.backend.connected;

import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
/**
 * "Connected" implementation of VABModelMap<V extends Object>
 * @author rajashek
 *
 */
public class ConnectedVABModelMap<V extends Object> extends ConnectedElement {

	public ConnectedVABModelMap(VABElementProxy proxy) {
		super(proxy);
	}
}
