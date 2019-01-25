package org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier;

import org.eclipse.basyx.aas.metamodel.hashmap.aas.identifier.Identifier;

/**
 * Identifiable class
 * 
 * @author kuhn, schnicke
 *
 */
public class Identifiable extends Referable {

	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor
	 */
	public Identifiable() {
		// Add qualifier
		putAll(new Referable());

		// Administrative information of an element. (AdministrativeInformation)
		put("administration", null);
		// The globally unique identification of an element. (Identificator)
		put("identification", new Identifier());
	}

	/**
	 * Constructor that accepts values for most relevant properties
	 */
	public Identifiable(String version, String revision, String idShort, String category, String description, String idType, String id) {
		// Add qualifier
		putAll(new Referable());

		// Create administrative information of an element. (AdministrativeInformation)
		put("administration", new AdministrativeInformation(version, revision));
		// The globally unique identification of an element. (Identificator)
		put("identification", new Identifier(idType, id));
	}
}
