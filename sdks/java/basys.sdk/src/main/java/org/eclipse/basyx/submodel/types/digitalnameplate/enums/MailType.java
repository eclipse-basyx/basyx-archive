package org.eclipse.basyx.submodel.types.digitalnameplate.enums;

import org.eclipse.basyx.submodel.metamodel.enumhelper.StandardizedLiteralEnum;
import org.eclipse.basyx.submodel.metamodel.enumhelper.StandardizedLiteralEnumHelper;

/**
 * characterization of an e-mail address according to its location or usage
 * as described in the AAS Digital Nameplate template
 * @author haque
 *
 */
public enum MailType implements StandardizedLiteralEnum {
	// Enum values
	
	/**
	 * (office, 0173-1#07-AAS754#001)
	 */
	OFFICE("1"),
	
	/**
	 * (secretary, 0173-1#07-AAS756#001)
	 */
	SECRETARY("3"),
	
	/**
	 * (substitute, 0173-1#07-AAS757#001)
	 */
	SUBSTITUTE("4"),
	
	/**
	 * (home, 0173-1#07-AAS758#001)
	 */
	HOME("5");

	private String standardizedLiteral;

	private MailType(String standardizedLiteral) {
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

	public static MailType fromString(String str) {
		return StandardizedLiteralEnumHelper.fromLiteral(MailType.class, str);
	}

}
