package org.eclipse.basyx.aas.metamodel.hashmap.aas.security;

import java.util.HashMap;

import org.eclipse.basyx.aas.api.metamodel.aas.security.ISecurity;
import org.eclipse.basyx.aas.metamodel.facades.SecurityFacade;

/**
 * KeyElements as defined in DAAS document
 * 
 * @author schnicke
 *
 */
public class Security extends HashMap<String, Object> implements ISecurity {
	private static final long serialVersionUID = 1L;
	
	public static final String ACCESSCONTROLPOLICYPOINTS ="accessControlPolicyPoints";
	public static final String TRUSTANCHOR ="trustAnchor";

	/**
	 * Constructor
	 */
	public Security() {
		// Default values
		put(ACCESSCONTROLPOLICYPOINTS, null);
		put(TRUSTANCHOR, null);
	}

	@Override
	public Object getAccessControlPolicyPoints() {
		return new SecurityFacade(this).getAccessControlPolicyPoints();
	}

	@Override
	public void setAccessControlPolicyPoints(Object obj) {
		new SecurityFacade(this).setAccessControlPolicyPoints(obj);
		
	}

	@Override
	public Object getTrustAnchor() {
		return new SecurityFacade(this).getTrustAnchor();
	}

	@Override
	public void setTrustAnchor(Object obj) {
		new SecurityFacade(this).setTrustAnchor(obj);
		
	}
}
