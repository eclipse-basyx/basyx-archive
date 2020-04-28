package org.eclipse.basyx.submodel.metamodel.api.identifier;

import org.eclipse.basyx.enumhelper.StandardizedLiteralEnum;
import org.eclipse.basyx.enumhelper.StandardizedLiteralEnumHelper;

/**
 * Enumeration of different types of Identifiers for global identification <br/>
 * Since in Java there is no enum inheritance, it is implemented as class <br/>
 * <br/>
 * 
 * @author schnicke
 *
 */
public enum IdentifierType implements StandardizedLiteralEnum {
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

	private String standardizedLiteral;

	private IdentifierType(String standardizedLiteral) {
		this.standardizedLiteral = standardizedLiteral;
	}

	@Override
	public String getStandardizedLiteral() {
		return standardizedLiteral;
	}

	@Override
	public String toString() {
		return standardizedLiteral;
	}

	public static IdentifierType fromString(String str) {
		return StandardizedLiteralEnumHelper.fromLiteral(IdentifierType.class, str);
	}

}
