package org.eclipse.basyx.aas.metamodel.hashmap.aas.security;

import java.util.HashMap;

/**
 * KeyElements as defined in DAAS document
 * 
 * @author schnicke
 *
 */
public class Security extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public Security() {
		// Default values
		put("accessControlPolicyPoints", null);
		put("trustAnchor", null);
	}
}
