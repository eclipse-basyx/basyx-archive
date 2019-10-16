package org.eclipse.basyx.aas.metamodel.facade.security;

import java.util.Map;

import org.eclipse.basyx.aas.metamodel.api.security.ISecurity;
import org.eclipse.basyx.aas.metamodel.map.security.Security;

/**
 * Facade providing access to a map containing the Security structure
 * 
 * @author rajashek
 *
 */
public class SecurityFacade implements ISecurity {

	private Map<String, Object> map;

	public SecurityFacade(Map<String, Object> map) {
		super();
		this.map = map;
	}

	@Override
	public Object getAccessControlPolicyPoints() {
		return map.get(Security.ACCESSCONTROLPOLICYPOINTS);
	}

	public void setAccessControlPolicyPoints(Object obj) {
		map.put(Security.ACCESSCONTROLPOLICYPOINTS, obj);
	}

	@Override
	public Object getTrustAnchor() {
		return map.get(Security.TRUSTANCHOR);
	}

	public void setTrustAnchor(Object obj) {
		map.put(Security.TRUSTANCHOR, obj);
	}

}
