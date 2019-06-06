package org.eclipse.basyx.aas.backend.connected.aas.security;

import org.eclipse.basyx.aas.api.metamodel.aas.security.ISecurity;
import org.eclipse.basyx.aas.backend.connected.ConnectedElement;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.security.Security;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
/**
 * "Connected" implementation of ISecurity
 * @author rajashek
 *
 */
public class ConnectedSecurity extends ConnectedElement implements ISecurity {
	public ConnectedSecurity(String path, VABElementProxy proxy) {
		super(path, proxy);		
	}
	
	@Override
	public Object getAccessControlPolicyPoints() {
		return getProxy().readElementValue(constructPath(Security.ACCESSCONTROLPOLICYPOINTS));
	}

	@Override
	public void setAccessControlPolicyPoints(Object obj) {
		getProxy().updateElementValue(constructPath(Security.ACCESSCONTROLPOLICYPOINTS), obj);
		
	}

	@Override
	public Object getTrustAnchor() {
		return getProxy().readElementValue(constructPath(Security.TRUSTANCHOR));
	}

	@Override
	public void setTrustAnchor(Object obj) {
		getProxy().updateElementValue(constructPath(Security.TRUSTANCHOR), obj);
		
	}
}
