package org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.qualifiable;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.basyx.aas.metamodel.hashmap.aas.reference.Reference;

/**
 * Forumla class as defined by DAAS document
 * 
 * @author schnicke
 *
 */
public class Formula extends Constraint {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public Formula() {
		put("dependsOn", new HashSet<Reference>());
	}

	/**
	 * 
	 * @param dependsOn
	 *            set of References the formula depends on
	 */
	public Formula(Set<Reference> dependsOn) {
		put("dependsOn", dependsOn);
	}

}
