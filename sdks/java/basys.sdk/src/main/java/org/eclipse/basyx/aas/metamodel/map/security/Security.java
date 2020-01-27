package org.eclipse.basyx.aas.metamodel.map.security;

import java.util.Map;

import org.eclipse.basyx.aas.metamodel.api.security.ISecurity;
import org.eclipse.basyx.vab.model.VABModelMap;

/**
 * KeyElements as defined in DAAS document
 * 
 * @author schnicke
 *
 */
public class Security extends VABModelMap<Object> implements ISecurity {
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

	/**
	 * Creates a Security object from a map
	 * 
	 * @param obj
	 *            a Security object as raw map
	 * @return a Security object, that behaves like a facade for the given map
	 */
	public static Security createAsFacade(Map<String, Object> map) {
		if (map == null) {
			return null;
		}

		Security ret = new Security();
		ret.setMap(map);
		return ret;
	}

	@Override
	public Object getAccessControlPolicyPoints() {
		return get(Security.ACCESSCONTROLPOLICYPOINTS);
	}

	public void setAccessControlPolicyPoints(Object obj) {
		put(Security.ACCESSCONTROLPOLICYPOINTS, obj);
	}

	@Override
	public Object getTrustAnchor() {
		return get(Security.TRUSTANCHOR);
	}

	public void setTrustAnchor(Object obj) {
		put(Security.TRUSTANCHOR, obj);
	}
}
