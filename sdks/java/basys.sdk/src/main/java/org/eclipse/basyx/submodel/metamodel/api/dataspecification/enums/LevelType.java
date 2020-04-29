package org.eclipse.basyx.submodel.metamodel.api.dataspecification.enums;

import org.eclipse.basyx.submodel.metamodel.enumhelper.StandardizedLiteralEnum;
import org.eclipse.basyx.submodel.metamodel.enumhelper.StandardizedLiteralEnumHelper;

/**
 * Possible level types as defined in DAAS for IEC61360 data specification templates
 * 
 * @author espen
 *
 */
public enum LevelType implements StandardizedLiteralEnum {
	MIN("Min"), MAX("Max"), NOM("Nom"), TYP("Typ");
	
	private String standardizedLiteral;

	private LevelType(String standardizedLiteral) {
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

	public static LevelType fromString(String str) {
		return StandardizedLiteralEnumHelper.fromLiteral(LevelType.class, str);
	}
}
