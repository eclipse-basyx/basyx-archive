package org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetypedef;

import org.eclipse.basyx.submodel.metamodel.enumhelper.StandardizedLiteralEnum;
import org.eclipse.basyx.submodel.metamodel.enumhelper.StandardizedLiteralEnumHelper;

/**
 * Helper enum to handle anySimpleTypeDef as defined in DAAS document <br />
 * Represents the type of a data entry <br />
 * TODO: Extend this to support rest of types (cf. p. 58)
 * 
 * @author schnicke
 *
 */
public enum PropertyValueTypeDef implements StandardizedLiteralEnum {
	Double("double"), Float("float"), Integer("int"), Long("long"), String("string"), Boolean("boolean"), Void("void"), Null("null");

	private String standardizedLiteral;

	private PropertyValueTypeDef(String standardizedLiteral) {
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

	public static PropertyValueTypeDef fromString(String str) {
		return StandardizedLiteralEnumHelper.fromLiteral(PropertyValueTypeDef.class, str);
	}

}
