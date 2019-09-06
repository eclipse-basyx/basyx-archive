package org.eclipse.basyx.aas.impl.metamodel.facades;


import java.util.Map;

import org.eclipse.basyx.aas.api.metamodel.aas.security.ISecurity;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.security.Security;
/**
 * Facade providing access to a map containing the Security structure
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

	@Override
	public void setAccessControlPolicyPoints(Object obj) {
		map.put(Security.ACCESSCONTROLPOLICYPOINTS, obj);
		
	}

	@Override
	public Object getTrustAnchor() {
		return map.get(Security.TRUSTANCHOR);
	}

	@Override
	public void setTrustAnchor(Object obj) {
		map.put(Security.TRUSTANCHOR, obj);
		
	}

}
