package org.eclipse.basyx.aas.backend.connected.aas.security;

import org.eclipse.basyx.aas.api.metamodel.aas.security.ISecurity;
import org.eclipse.basyx.aas.backend.connected.ConnectedElement;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.security.Security;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;

/**
 * "Connected" implementation of ISecurity
 * 
 * @author rajashek
 *
 */
public class ConnectedSecurity extends ConnectedElement implements ISecurity {
	public ConnectedSecurity(VABElementProxy proxy) {
		super(proxy);
	}

	@Override
	public Object getAccessControlPolicyPoints() {
		return getProxy().getModelPropertyValue(Security.ACCESSCONTROLPOLICYPOINTS);
	}

	@Override
	public Object getTrustAnchor() {
		return getProxy().getModelPropertyValue(Security.TRUSTANCHOR);
	}
}
