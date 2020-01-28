package org.eclipse.basyx.submodel.metamodel.api.reference.enums;

/**
 * KeyType, LocalKeyType, IdentifierType as defined in DAAS document <br/>
 * <br />
 * Since there's no enum inheritance in Java, all enums are merged into a single
 * class
 * 
 * @author schnicke
 *
 */
public enum KeyType {
	/**
	 * Enum values of IdentifierType
	 */
	CUSTOM("Custom"), IRDI("IRDI"), IRI("IRI"),

	/**
	 * Enum values of LocalKeyType
	 */
	IDSHORT("IdShort"), FRAGMENTID("FragmentId");

	private String name;

	private KeyType(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	public static KeyType fromString(String str) {
		if (str == null || str.isEmpty()) {
			return null;
		}

		for (KeyType elem : KeyType.values()) {
			if (elem.toString().equals(str)) {
				return elem;
			}
		}

		throw new RuntimeException("Unknown KeyType: " + str);
	}
}
