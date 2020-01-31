package org.eclipse.basyx.submodel.metamodel.api.identifier;

/**
 * Enumeration of different types of Identifiers for global identification <br/>
 * Since in Java there is no enum inheritance, it is implemented as class <br/>
 * <br/>
 * 
 * @author schnicke
 *
 */
public enum IdentifierType {
	// Enum values
	/**
	 * IRDI according to ISO29002-5 as an Identifier scheme for properties and
	 * classifications.
	 */
	IRDI("IRDI"),

	/**
	 * IRI according to Rfc 3987. Every URI is an IRI.
	 */
	IRI("IRI"),

	/**
	 * Custom identifiers like GUIDs (globally unique identifiers)
	 */
	CUSTOM("Custom");

	private String name;

	private IdentifierType(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	public static IdentifierType fromString(String str) {
		if (str == null || str.isEmpty()) {
			return null;
		}

		for (IdentifierType elem : IdentifierType.values()) {
			if (elem.toString().equals(str)) {
				return elem;
			}
		}

		throw new RuntimeException("Unknown IdentifierType: " + str);
	}

}
