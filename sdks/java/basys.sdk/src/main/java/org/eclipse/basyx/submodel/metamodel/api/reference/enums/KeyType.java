package org.eclipse.basyx.submodel.metamodel.api.reference.enums;

import org.eclipse.basyx.enumhelper.StandardizedLiteralEnum;
import org.eclipse.basyx.enumhelper.StandardizedLiteralEnumHelper;

/**
 * KeyType, LocalKeyType, IdentifierType as defined in DAAS document <br/>
 * <br />
 * Since there's no enum inheritance in Java, all enums are merged into a single
 * class
 * 
 * @author schnicke
 *
 */
public enum KeyType implements StandardizedLiteralEnum {
	/**
	 * Enum values of IdentifierType
	 */
	CUSTOM("Custom"), IRDI("IRDI"), IRI("IRI"),

	/**
	 * Enum values of LocalKeyType
	 */
	IDSHORT("IdShort"), FRAGMENTID("FragmentId");

	private String standardizedLiteral;

	private KeyType(String standardizedLiteral) {
		this.standardizedLiteral = standardizedLiteral;
	}

	@Override
	public String getStandardizedLiteral() {
		return this.standardizedLiteral;
	}

	@Override
	public String toString() {
		return standardizedLiteral;
	}

	public static KeyType fromString(String str) {
		return StandardizedLiteralEnumHelper.fromLiteral(KeyType.class, str);
	}
}
