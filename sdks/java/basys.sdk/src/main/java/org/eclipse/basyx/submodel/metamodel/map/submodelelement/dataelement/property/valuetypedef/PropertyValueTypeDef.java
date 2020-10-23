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
	Int8("byte"), Int16("short"), Int32("int"), Int64("long"),
	UInt8("unsignedByte"), UInt16("unsignedShort"), UInt32("unsignedInt"), UInt64("unsignedLong"),
	String("string"), LangString("langString"),
	AnyURI("anyuri"), Base64Binary("base64Binary"), HexBinary("hexBinary"), NOTATION("notation"), ENTITY("entity"), ID("id"), IDREF("idref"),
	Integer("integer"), NonPositiveInteger("nonPositiveInteger"), NonNegativeInteger("nonNegativeInteger"), PositiveInteger("positiveInteger"), NegativeInteger("negativeInteger"),
	Double("double"), Float("float"), Boolean("boolean"),
	Duration("duration"), DayTimeDuration("dayTimeDuration"), YearMonthDuration("yearMonthDuration"),
	DateTime("dateTime"), DateTimeStamp("dateTimeStamp"), GDay("gDay"), GMonth("gMonth"), GMonthDay("gMonthDay"), GYear("gYear"), GYearMonth("gYearMonth"),
	QName("qName"),
	None("none"), AnyType("anyType"), AnySimpleType("anySimpleType");

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
