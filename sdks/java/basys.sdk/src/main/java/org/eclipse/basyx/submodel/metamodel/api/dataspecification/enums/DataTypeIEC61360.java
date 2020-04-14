package org.eclipse.basyx.submodel.metamodel.api.dataspecification.enums;

/**
 * Possible value data types as defined in DAAS for IEC61360 data specification templates
 * 
 * @author espen
 *
 */
public enum DataTypeIEC61360 {
	BOOLEAN("BOOLEAN"), DATE("DATE"), RATIONAL("RATIONAL"), RATIONAL_MEASURE("RATIONAL_MEASURE"),
	REAL_COUNT("REAL_COUNT"), REAL_CURRENCY("REAL_CURRENCY"), REAL_MEASURE("REAL_MEASURE"), STRING("STRING"),
	STRING_TRANSLATABLE("STRING_TRANSLATABLE"),
	TIME("TIME"), TIME_STAMP("TIME_STAMP"), URL("URL");

	private String type;

	private DataTypeIEC61360(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return type;
	}

	public static DataTypeIEC61360 fromString(String str) {
		if (str == null || str.isEmpty()) {
			return null;
		}

		for (DataTypeIEC61360 elem : DataTypeIEC61360.values()) {
			if (elem.toString().equals(str)) {
				return elem;
			}
		}

		throw new RuntimeException("Unknown DataTypeIEC61360: " + str);
	}
}
