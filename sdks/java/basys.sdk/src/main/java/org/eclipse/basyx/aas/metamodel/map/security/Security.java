package org.eclipse.basyx.aas.metamodel.map.security;

import java.util.HashMap;

import org.eclipse.basyx.aas.metamodel.api.security.ISecurity;
import org.eclipse.basyx.aas.metamodel.facade.security.SecurityFacade;

/**
 * KeyElements as defined in DAAS document
 * 
 * @author schnicke
 *
 */
public class Security extends HashMap<String, Object> implements ISecurity {
	private static final long serialVersionUID = 1L;

	public static final String ACCESSCONTROLPOLICYPOINTS = "accessControlPolicyPoints";
	public static final String TRUSTANCHOR = "trustAnchor";

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

	public void setAccessControlPolicyPoints(Object obj) {
		new SecurityFacade(this).setAccessControlPolicyPoints(obj);
	}

	@Override
	public Object getTrustAnchor() {
		return new SecurityFacade(this).getTrustAnchor();
	}

	public void setTrustAnchor(Object obj) {
		new SecurityFacade(this).setTrustAnchor(obj);
	}
}
